# Consumers design and implementation considerations

Implementing a Topic consumer is using the kafka [KafkaConsumer class](https://kafka.apache.org/10/javadoc/?org/apache/kafka/clients/consumer/KafkaConsumer.html) which the API documentation is a must read.
The implementation is simple for a single thread consumer:
* prepare the properties
* create an instance of KafkaConsumer to connect to a topic and a partition
* loop on polling events 
  * process the ConsumerRecords and commit the offset by code or use the autocommit attibute of the consumer,   

Examples of Java consumers can be found in [this project](https://github.com/ibm-cloud-architecture/refarch-kc-ms).
Example of Javascript implementation is in [this repository](https://github.com/jbcodeforce/nodejs-kafka)

But the complexity comes from the offset management and multithreading needs. So the following important considerations need to be addressed while implementing a consumer:

## Assess number of consumer needed 

The KafkaConsumer is not thread safe so it is recommended to run in a unique thread. But if needed you can implement a multi-threads solution, but as each thread will open a TCP connection to the Kafka broker, be sure to close the connection to avoid memory leak. The alternate is to start n processus. 

If you need multiple consumers running in parallel to scale horizontally, you have to define multiple partitions while configuring the topic and use fine-grained control over offset persistence. You’ll use one consumer per partition of a topic. 
This consumer-per-partition pattern maximizes throughput. When consumers run in parallel and you use multiple threads per soncumser you need to be sure the total number of threads across all instances do not exceed the total number of partitions in the topic.

Also, a consumer can subscribe to multiple topics. The brokers are doing rebalancing of the assignment of topic-partition to a consumer that belong to a group. When creating a new consumer you can specify the group id in the options. 

Consumer groups are grouping consumers to cooperate to consume messages from one or more topics. Organized in cluster the coordinator servers are responsible for assigning partitions to the consumers in the group. The rebalancing of partition to consumer is done when a new consumer join or leave the group or when a new partition is added to an existing topic.

## Offset management

Recall that offset is just a numeric identifier of a consumer position of the last record read within a partition. Consumers periodically need to commit the offsets of messages they have received to show they have processed the message and in case of failure from where they should reconnect. It is possible to commit by calling API or by setting some properties at the consumer creation level to enable autocommit offset. When doing manual offet, there are two types of manually committed:
* offsets—synchronous
* asynchronous. 

When dealing with heavy load storing offset in zookeeper is non advisable. It is even now recognize as a bad practice. To manage offset use the new consumer API, and for example commits offset synchronously when a specified number of events are read from the topic and the persistence to the back end succeed.

Assess if it is possible to lose messages from topic.  If so, when a consumer restarts it will start consuming the topic from the end of the queue.

Do the solution is fine with at-least-once delivery or exactly-once is a must have? As the operation to store a message and the storage of offsets are two separate operations, and in case of failure between them, it is possible to have stale offsets, which will introduce duplicate messages when consumers restart to process from last known committed offset. "exactly-once" means grouping record and offset persistence in an atomic operation.


## Repositories with consumer code

* Within the Container shipment solution we have a ship movement event consumer and a container metrics event consumer.

* [Asset analytics asset consumers](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/tree/master/asset-consumer)

* [Nodejs kafka consumers and producers](https://github.com/jbcodeforce/nodejs-kafka)

## Kafka useful Consumer APIs
* [KafkaConsumer](https://kafka.apache.org/11/javadoc/org/apache/kafka/clients/consumer/KafkaConsumer.html) a topic consumer which support:
  * transparently handles brokers failure
  * transparently adapt to partition migration within the cluster
  * support grouping for load balancing among consumers
  * maintains TCP connections to the necessary brokers to fetch data
  * subscribe to multiple topics and being part of consumer groups
  * each partition is assigned to exactly one consumer in the group
  * if a process fails, the partitions assigned to it will be reassigned to other consumers in the same group
* [ConsumerRecords](https://kafka.apache.org/11/javadoc/org/apache/kafka/clients/consumer/ConsumerRecords.html) holds the list ConsumerRecord per partition for a particular topic.
* [ConsumerRecord](https://kafka.apache.org/11/javadoc/org/apache/kafka/clients/consumer/ConsumerRecord.html) A key/value pair to be received from Kafka. This also consists of a topic name and a partition number from which the record is being received, an offset that points to the record in a Kafka partition, and a timestamp
## References

* [IBM Event Streams - Consuming messages](https://ibm.github.io/event-streams/about/consuming-messages/)
* [KafkaConsumer class](https://kafka.apache.org/10/javadoc/?org/apache/kafka/clients/consumer/KafkaConsumer.html)