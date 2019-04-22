# Producers considerations 

A producer is a thread safe kafka client API that publishes records to the cluster. It uses buffers, thread pool, and serializer to send data. They are stateless. This is the consumers that are managing the offsets. Producers are more simple to implement but still you need to assess some design considerations.

## Design considerations

When developing a record producer you need to assess the following:

* What is the expected throughput to send events? Event size * average throughput combined with the expected latency help to compute buffer size. By default, the buffer size is set at 32Mb, but can be configured with `buffer.memory`. (See [producer configuration API](https://kafka.apache.org/10/javadoc/org/apache/kafka/clients/producer/ProducerConfig.html)
* Can the producer batch events together to send them in batch over one send operation? 
* Is there a risk for loosing communication? Tune the RETRIES_CONFIG and buffer size, and ensure to have at least 3 brokers and even 5 to maintain quorum in case of one failure. The client API is implemented to support reconnection.
* Assess *once to exactly once* delivery requirement. Look at idempotent producer: retries will not introduce duplicate records.
* Where the event timestamp comes from? Should the producer send operation set it or is it loaded from external data? Remember that `LogAppendTime` is considered to be processing time, and `CreateTime` is considered to be event time.

See related discussions [on confluent web site.](https://www.confluent.io/blog/put-several-event-types-kafka-topic/)

## Typical code structure

The producer code does the following steps:

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
 | ENABLE_IDEMPOTENCE_CONFIG | Set to tru, the number of retries will maximized, and the acks will be set to `All`.|  


## Code Examples

* [Simple text message](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/blob/master/asset-event-producer/src/main/java/ibm/cte/kafka/play/SimpleProducer.java)
* [A Pump simulator](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/tree/master/asset-event-producer#pump-simulator)
* [Ship movement and container metrics event producers](https://github.com/ibm-cloud-architecture/refarch-kc-ms)

## More readings

*[Creating advanced kafka producer in java - Cloudurable](http://cloudurable.com/blog/kafka-tutorial-kafka-producer-advanced-java-examples/index.html)