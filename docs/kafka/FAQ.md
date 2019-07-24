# Frequently asked questions

## Kafka concepts?

See [this introduction](readme.md)

## How to support exactly once delivery?

See the section in the producer implementation considerations [note](producers.md).

Also it is important to note that the Kafka Stream API supports exactly once semantics with the config: `processing.guarantee=exactly_once`. Each task within a read-process-write flow may fail so this setting is important to be sure the right answer is delivered, even in case of task failure, and the process is executed exactly once. 

## Why does kafka use zookeeper?

Kafka as a distributed system using cluster, it needs to keep cluster states, sharing configuration like topic, assess which node is still alive within the cluster, support registrering new node added to the cluster, being able to support dynamic restart. Zookeeper is an orchestrator for distributed system, it maintains kafka cluster integrity, select broker leader... 

## Retention time for topic what does it mean?

The message sent to a cluster is kept for a max period of time or until a max size is reached. Those topic properties are: `retention.ms` and `retention.bytes`. Messages stay in the log even if they are consumed. The oldest messages are marked for deletion or compaction depending of the cleanup policy (delete or compact) set to `cleanup.policy` parameter.

See the kafka documentation on [topic configuration parameters](https://kafka.apache.org/documentation/#topicconfigs). 

Here is a command to create a topic with specific retention properties:

```
bin/kafka-configs --zookeeper XX.XX.XX.XX:2181 --entity-type topics --entity-name orders --alter --add-config  retention.ms=55000 --add-config  retention.ms=55000
```

But there is also the `offsets.retention.minutes` property, set at the cluster level to control when the offset information will be deleted. It is defaulted to 1 day, but the max possible value is 7 days. This is to avoid keeping too much information in the broker memory and avoid to miss data when consumers run not continuously. So consumers need to commit their offset. If the consumer properties `auto.offset.reset=earliest` it will reprocess all the events, or skips to the latest if set to latest. In this last case if the consumers are offline for more than this time window, they will lose events.


## Differences between Akka and Kafka?

[Akka](https://akka.io/) is a open source toolkit for Scala or Java to simplify multithreading programming and makes application more reactive by adopting an asynchronous mechanism to access to io: database or HTTP request. To support asynchronous communication between 'actors', it uses messaging, internal to the JVM. 
Kafka is part of the architecture, while Akka is an implementation choice for one of the component of the business application deployed inside the architecture. 

[vert.x](https://vertx.io/) is another open source implementation of such internal messaging mechanism but supporting more language:  Java, Groovy, Ruby, JavaScript, Ceylon, Scala, and Kotlin.


## Other FAQs

[IBM Event streams on Cloud FAQ](https://cloud.ibm.com/docs/services/EventStreams?topic=eventstreams-faqs) 

[FAQ from Confluence](https://cwiki.apache.org/confluence/display/KAFKA/FAQ#FAQ-HowareKafkabrokersdependonZookeeper?)