# Consumers considerations 

Implementing a Topic consumer is using the kafka [KafkaConsumer class](https://kafka.apache.org/10/javadoc/?org/apache/kafka/clients/consumer/KafkaConsumer.html) which the API documentation is a must read.
The implementation is simple for a single thread consumer:
* prepare the properties
* create an instance of KafkaConsumer
* loop on polling events 
  * process the ConsumerRecords and commit or autocommit the offset 

But the complexity comes from the offset management and multithreading needs. So the following important considerations need to be addressed while implementing a consumer:

## Assess number of thread to consume event 
The KafkaConsumer is not thread safe so it is recommended to run in a unique thread. But if needed you can implement a multi threads solution, but as each thread will open a TCP connection to the Kafka broker, be sure to close the connection to avoid memory leak. 

If you need multiple consumers running in parallel to scale horizontally, you may define multiple partitions while configuring the topic and use fine-grained control over offset persistence.

## Offset management
Recall that offset is just a numeric identifier of a consumer position of the last record read within a partition. Consumers periodically need to commit the offsets of messages they have received to show they have processed the message and in case of failure from where they should reconnect. It is possible to commit by calling API or by setting some properties at the consumer creation.

When dealing with heavy load storing offset in zookeeper is non advisable. It is even now recognize as a bad practice. To manage offset use the new consumer API, and for example commits offset synchronously when a specified number of events are read from the topic and the persistence to the back end succeed.

Assess if it is possible to lose messages from topic.  If so, when a consumer restarts it will start consuming the topic from the end of the queue.

Do the solution is fine with at-least-once delivery or exactly-once is a must have? As the operation to store a message and the storage of offsets are two separate operations, and in case of failure between them, it is possible to have stale offsets, which will introduce duplicate messages when consumers restart to process from last known committed offset. "exactly-once" means grouping record and offset persistence in an atomic operation.


## Repositories with consumer code

* Within the Container shipment solution we have a ship movement event consumer and a container metrics event consumer.

* [Asset analytics asset consumers](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/tree/master/asset-consumer)

## References

* [IBM Event Streams - Consuming messages](https://ibm.github.io/event-streams/about/consuming-messages/)
* [KafkaConsumer class](https://kafka.apache.org/10/javadoc/?org/apache/kafka/clients/consumer/KafkaConsumer.html)