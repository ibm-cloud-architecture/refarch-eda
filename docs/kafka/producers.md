# Producers considerations

A producer is a thread safe kafka client API that publishes records to the cluster. It uses buffers, thread pool, and serializer to send data. They are stateless: the consumers is responsible to manage the offsets of the message they read. When the producer connects via the initial bootstrap connection, it gets the metadata about the topic - partition and the leader broker to connect to. The assignment of message to partition is done following different algorithms: round-robin if there is no key specified, using the hash code of the key, or custom defined.

We recommend reading [IBM Event streams producer guidelines](https://ibm.github.io/event-streams/about/producing-messages/) to understand how producers work and some configuration parameters.

## Design considerations

When developing a record producer you need to assess the following:

* What is the event payload to send? Is is an aggregate, as domain driven design concept, with value objects that needs to be kept in sequence to be used as event sourcing? or order does not matter? Remember that when order is important, messages need to go to the same topic. When multiple partitions are used, the messages with the same key will go to the same partition to guaranty the order. See related discussions [from Martin Kleppmann on confluent web site](https://www.confluent.io/blog/put-several-event-types-kafka-topic/). Also to be exhaustive, it is possible to get a producer doing retries that could generate duplicate records as acknowleges may take time to come: within a batch of n records, if the producer did not get all the n acknowledge on time, it may resend the batch. This is where 'idempotence' becomes important.
* Is there a strong requirement to manage the schema definition? If using one topic to manage all events about a business entity, then be sure to support a flexible [avro model](https://avro.apache.org/docs/1.8.1/spec.html).
* What is the expected throughput to send events? Event size * average throughput combined with the expected latency help to compute buffer size. By default, the buffer size is set at 32Mb, but can be configured with `buffer.memory` property. (See [producer configuration API](https://kafka.apache.org/10/javadoc/org/apache/kafka/clients/producer/ProducerConfig.html)
* Can the producer batch events together to send them in batch over one send operation? By design kafka producers batch events.
* Is there a risk for loosing communication? Tune the RETRIES_CONFIG and buffer size, and ensure to have at least 3 or even better 5 brokers within the cluster to maintain quorum in case of one failure. The client API is implemented to support reconnection.
	* When deploying kafka on Kubernetes, it is important to proxy the broker URLs with a proxy server outside of kubernetes.

* Assess *exactly once* delivery requirement. Look at idempotent producer: retries will not introduce duplicate records (see [section](#how-to-support-exactly-once-delivery) below).
* Partitions help to scale the consumer processing of message, but it also helps the producer to be more efficient as it can send message in parallel to different partition.
* Where the event timestamp comes from? Should the producer send operation set it or is it loaded from external data? Remember that `LogAppendTime` is considered to be processing time, and `CreateTime` is considered to be event time.

## Typical producer code structure

The producer code, using java or python API, does the following steps:

* define producer properties
* create a producer instance
* send event records and get resulting metadata.

Producers are thread safe. The send() operation is asynchronous and returns immediately once record has been stored in the buffer of records, and it is possible to add a callback to process the broker acknowledgement.

## Kafka useful Producer APIs

Here is a list of common API to use in your producer and consumer code.

* [KafkaProducer](https://kafka.apache.org/11/javadoc/org/apache/kafka/clients/producer/KafkaProducer.html) A Kafka client that publishes records to the Kafka cluster.  The send method is asynchronous. A producer is thread safe so we can have per topic to interface.
* [ProducerRecord](https://kafka.apache.org/11/javadoc/org/apache/kafka/clients/producer/ProducerRecord.html) to be published to a topic
* [RecordMetadata](https://kafka.apache.org/11/javadoc/org/apache/kafka/clients/producer/RecordMetadata.html) metadata for a record that has been acknowledged by the server.

## Properties to consider

The following properties are helpful to tune at each topic and producer and will vary depending on the deployment:  

 | Properties | Description |
 | --- | --- |
 | BOOTSTRAP_SERVERS_CONFIG |  A comma-separated list of host:port values for all the brokers deployed. So producer may use any brokers |
 | KEY_SERIALIZER_CLASS_CONFIG and VALUE_SERIALIZER_CLASS_CONFIG |convert the keys and values into byte arrays. Using default String serializer should be a good solution for Json payload. For streaming app, use customer serializer.|
 | ACKS_CONFIG | specifies the minimum number of acknowledgments from a broker that the producer will wait for before considering a record send completed. Values = all, 0, and 1. 0 is for fire and forget. |
 | RETRIES_CONFIG | specifies the number of times to attempt to resend a batch of events. |
 | ENABLE_IDEMPOTENCE_CONFIG | Set to true, the number of retries will be maximized, and the acks will be set to `All`.|  

## How to support exactly once delivery

Knowing that exactly once delivery is one of the hardest problems to solve in distributed systems, how kafka does it?. Broker can fail or a network may respond slowly while a producer is trying to send events.

Producer can set acknowledge level to control the delivery semantic:

* **At least once**: means the producer set ACKS_CONFIG=1 and get an acknowledgement message when the message sent, has been written at least one time in the cluster (assume replicas = 3).  If the ack is not received, the producer may retry, which may generate duplicate records in case the broker stops after saving to the topic and before sending back the acknowledgement message.
* **At most semantic**: means the producer will not do retry in case of no acknowldege received. It may create log and compensation, but the message is lost.
* **Exactly once** means even if the producer sends the message twice the system will send only one message to the consumer. Once the consumer commits the read offset, it will not receive the message again, even if it restarts. Consumer offset needs to be in sync with produced event.

With the idempotence property (ENABLE_IDEMPOTENCE_CONFIG = true), the record sent, has a sequence number and a producer id, so that the broker keeps the last sequence number per producer and per partition. If a message is received with a lower sequence number, it means a producer is doing some retries on record already processed, so the broker will drop it, to avoid having duplicate records per partition. The sequence number is persisted in a log so even in case of broker leader failure, the new leader will have a good view of the states of the system.

![Exactly once](images/exactly-one-1.png)

If the producer resends its batch, the broker will drop the messages with a sequence less or equals to the last committed sequence number, and accept the other ones.

![Excatly once - retries](images/exactly-one-2.png)

!!! note
        The replication mechanism guarantees that when a message is written to the leader replica, it will be replicated to all available replicas.
        As soon as you want to get acknowledge of all replicates, it is obvious to set idempotence to true. It does not impact performance.

To add to these, as topic may have multiple partitions, kafka supports atomic writes to all partitions, so that all records are saved or none of them are visible to consumers. This transaction control is done by using the producer transactional API, and a unique transaction identifier to keep integrated state. Here is an example of such configuration that can be done in a producer contructor method:

```java
producerProps.put("enable.idempotence", "true");
producerProps.put("transactional.id", "prod-1");
kafkaProducer.initTransactions()
```

`initTransactions()` registers the producer with the broker as one that can use transactions, identifying it by its transactional.id and a sequence number, or epoch.

In case of multiple partitions, the broker will store a list of all updated partitions for a given transaction.

See the [code in order command](https://github.com/ibm-cloud-architecture/refarch-kc-order-ms/blob/53bbb8cdeac413883ca2ccf521eb0797a43f45a3/order-command-ms/src/main/java/ibm/gse/orderms/infrastructure/kafka/OrderCommandProducer.java#L46) microservice.

The consumer is also interested to configure the reading of the transactional messages by defining the isolation level. Consumer waits to read transactional messages until the associated transaction has been committed. Here is an example of consumer code and configuration

```java
consumerProps.put("enable.auto.commit", "false");
consumerProps.put("isolation.level", "read_committed");
```

With `read_committed`, no message that was written to the input topic in the same transaction will be read by this consumer until message replicas are all written.

The consumer commits its offset with code, and specifies the last offset to read.

```java
offsetsToCommit.put(partition, new OffsetAndMetadata(offset + 1))
producer.sendOffsetsToTransaction(offsetsToCommit, "order-group-id");
```

The producer then commits the transaction.

```java
try {
    kafkaProducer.beginTransaction();
    ProducerRecord<String, String> record = new ProducerRecord<>(ApplicationConfig.ORDER_COMMAND_TOPIC, key, value);
    Future<RecordMetadata> send = kafkaProducer.send(record);
    send.get(ApplicationConfig.PRODUCER_TIMEOUT_SECS, TimeUnit.SECONDS);
    kafkaProducer.commitTransaction();
} catch (KafkaException e){
    kafkaProducer.abortTransaction();
}
```

There is an interesting [article](https://www.baeldung.com/kafka-exactly-once) from the Baeldung team about exactly once processing in kafka with code example.

## Code Examples

* [Simple text message](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/blob/master/asset-event-producer/src/main/java/ibm/cte/kafka/play/SimpleProducer.java)
* [Order management with CQRS in Java]((https://github.com/ibm-cloud-architecture/refarch-kc-order-ms))
* [Ship movement and container metrics event producers](https://github.com/ibm-cloud-architecture/refarch-kc-ms)
* [Springboot with kafka template]((https://github.com/ibm-cloud-architecture/refarch-kc-container-ms))

## More readings

* [Creating advanced kafka producer in java - Cloudurable](http://cloudurable.com/blog/kafka-tutorial-kafka-producer-advanced-java-examples/index.html)
* [Confluent blog: Exactly-once Semantics are Possible: Here’s How Kafka Does it](https://www.confluent.io/blog/exactly-once-semantics-are-possible-heres-how-apache-kafka-does-it/)
