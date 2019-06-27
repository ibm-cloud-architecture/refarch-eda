# Frequently asked questions

## Kafka concepts?

See [this introduction](readme.md)

## How to support exactly once delivery?

See the section in the producer implementation considerations [note](producers.md).

Also it is important to note that the Kafka Stream API supports exactly once semantics with the config: `processing.guarantee=exactly_once`. Each task within a read-process-write flow may fail so this setting is important to be sure the right answer is delivered, even in case of task failure, and the process is executed exactly once. 

## Why does kafka use zookeeper?

Kafka as a distributed system using cluster, it needs to keep cluster states, sharing configuration like topic, assess which node is still alive within the cluster, support registrering new node added to the cluster, being able to support dynamic restart. Zookeeper is an orchestrator for distributed system, it maintains kafka cluster integrity, select broker leader... 

## Differences between Akka and Kafka?

[Akka](https://akka.io/) is a open source toolkit for Scala or Java to simplify multithreading programming and makes application more reactive by adopting an asynchronous mechanism to access to io: database or HTTP request. To support asynchronous communication between 'actors', it uses messaging, internal to the JVM. 
Kafka is part of the architecture, while Akka is an implementation choice for one of the component of the business application deployed inside the architecture. 

[vert.x](https://vertx.io/) is another open source implementation of such internal messaging mechanism but supporting more language:  Java, Groovy, Ruby, JavaScript, Ceylon, Scala, and Kotlin.



## Other FAQs

[FAQ from confluent](https://cwiki.apache.org/confluence/display/KAFKA/FAQ#FAQ-HowareKafkabrokersdependonZookeeper?)