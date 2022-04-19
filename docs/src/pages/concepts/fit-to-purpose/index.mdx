---
title: Fit for purpose
description: Different fit for purpose evaluation and criteria for technologies involved in EDA
---

Updated 04/19/2022


In this note we want to list some of the main criteria to consider and assess during an event-driven architecture establishment work 
or during the continuous application governance. This is not fully exhaustive, but give good foundations for analysis and study.
Fit for purpose practices should be done under a bigger program about application development governance and data governance.
We can look at least to the following major subjects:

<AnchorLinks>
  <AnchorLink>Cloud native applications</AnchorLink>
  <AnchorLink>Modern data pipeline</AnchorLink>
  <AnchorLink>MQ Versus Kafka</AnchorLink>
  <AnchorLink>Kafka Streams vs Apache Flink</AnchorLink>
</AnchorLinks>


## Cloud native applications

With the adoption of cloud native and microservice applications (the [12 factors](https://12factor.net/) app), the followings need to be addressed:

* Responsiveness with elastic scaling and resilience to failure. Which leads to adopt the '[reactive](/advantages/reactive/) manifesto' and consider messaging as a way to communicate between apps. Elastic also may lead to multi-cloud deployments.
* Address data sharing using a push model to improve decoupling, and performance. Instead of having each service using REST endpoints to pull the data from other services, each service pushes the change to their main business entity state to a event backbone. 
Each future service in need for those data, pulls from the messaging system.
* Adopting common patterns like [command query responsibility seggregation](/patterns/cqrs/) to help implementing complex queries, joining different business entities owned by different microservices, [event sourcing](/patterns/event-sourcing/), [transactional outbox](/patterns/intro/#transactional-outbox) and [SAGA](/patterns/saga/) for long running transaction.
* Addressing data eventual consistency to propagate change to other components versus ACID transaction.
* Support "always-on" approach with the deployment to multiple data centers (at least three) being active/active and being able to propagate data in all data centers.

Supporting all or part of those requirements will lead to the adoption of event-driven microservices and architecture.

## Motivation for data streaming

The central value propositions of data stream are to: 

* lower the cost of integrating via event streams, 
* use event streams to signal state changes in near-real time
* replay the past to build data projection

Applying the concept of data loose value over time, it is important to act on data as early
as possible, close to creation time. After a period of time data becomes less valuable.

Two time factors are important in this data processing: **latency** (time to deliver data to consumers)
and **retention** (time to keep data). For latency try to reduce the number of network segment between
producer and consumers. Considering edge computing as a way to bring event processing close to the source.
The event processing add time to the end to end latency. Considering constraining the processing time frame.

*Retention* is a problem linked to the business requirements, and we need to assess for each topic how long
an event is still valuable for the consumers. Not keeping enough events will impact correctness of consumer state, 
projection views... keeping for too long, increase the cost of storage, but also the time to rebuild data 
projection. 

## Modern data pipeline

As new business applications need to react to events in real time, the adoption of [event backbone](/concepts/terms-and-definitions/#event-backbone) is really part of the IT toolbox. 
Modern IT architecture encompasses the adoption of new data hub, where all the data about a 'customer', for example, is accessible in one event backbone. 
Therefore, it is natural to assess the data movement strategy and assess how to offload some of those ETL jobs running at night, 
by adopting real time data ingestion. 

We detailed the new architecture in [this modern data lake](introduction/reference-architecture/#modern-data-lake) article, so from a *fit for purpose* point of view, 
we need to assess the scope of existing ETL jobs, and refector to streaming logic that can be incorporated into different logs/ topics.
With Event Backbone like Kafka, any consumer can join the data log consumption at any point of time, within the retention period. 
By moving the ETL logic to a streaming application, we do not need to wait for the next morning to get important metrics.

## MQ Versus Kafka

We already addressed the differences between Queueing and Streaming in [this chapter](/concepts/events-versus-messages/).

Now in term of technologies we can quickly highlight the followings:

### Kafka characteristics

* Keep message for long period of time, messages are not deleted once consumed
* Suited for high volume and low latency processing
* Support pub/sub model only
* Messages are ordered in a topic/partition but not cross partitions
* Stores and replicates events published to a topic, remove on expired period or on disk space constraint
* Messages are removed from file system independent of applications 
* Topic can have multiple partitions to make consumer processing parallel.
* Not supporting two phase commits / XA transaction, but message can be produced with local transaction
* Multi-region architecture requires data replication across regions with Mirror Maker 2
* Applications (producers, consumers, or streaming) are going to a central cluster.
* Cluster can support multi availability zones
* But also support extended cluster to go over different regions if those regions have low latency network
* Scales horizontally, by adding more nodes
* non-standard API but rich library to support the main programming language.
* But support also HTTP bridge or proxy to get message sent via HTTP
* when there is a problem on the broker it takes a lot of time to recover and it impacts all consumers
* Kafka is easy to setup with kubernetes deployment with real operator, is more difficult to manage with bare metal deployment.
* Cluster and topics definition can be managed with Gitops and automatically instantiated in new k8s cluster
* Consumer and producers are build and deployed with simple CI/CD pipeline

### MQ characteristics

* Best suited for point-to-point communication 
* Supports horizontal scaling and high volume processing
* Supports **event mesh**, where Brokers can be deployed in different environments and serve the async applications locally, 
improving communication, performance, placement and scalability.
* The local transaction supports to write or read a message from a queue is a strength for scalability as if a consumer is not able to
process the messagem another one will do. In Kafka, one  partition is assigned to a consumer, and may be reassigned to 
a new consumer after failure, which leads to a very costly work of partition rebalancing.
* Participates to two-phase commit transaction
* Exactly one delivery with strong consistency
* Integrate with Mainframes: transactional applications on mainframe 
* Support JMS for JEE applications
* Support AMQP for lighter protocol
* Messages are removed after consumption, they stayed persisted until consumed by all subscribers
* Strong coupling with subscribers, producer knows its consumers
* Supports MQ brokers in cluster with leader and followers.
* Support replication between brokers
* Support message priority
* Support dynamic queue creation. Typical case it replay to queue.
* Support much more queue per broker so it is easier to scale.
* Easily containerized and managed with Kubernetes operators.

### Direct product feature comparison

| Kafka | IBM MQ | 
| --- | --- |
| Kafka is a pub/sub engine with streams and connectors | MQ is a queue,or pub/sub engine |
| All topics are persistent | Queues and topics can be persistent or non persistent |
| All subscribers are durable | Subscribers can be durable or non durable |
| Adding brokers to requires little work (changing a configuration file) | Adding QMGRs requires some work (Add the QMGRs to the cluster, add cluster channels.  Queues and Topics need to be added to the cluster.) |
| Topics can be spread across brokers (partitions) with a command | Queues and topics can be spread across a cluster by adding them to clustered QMGRs |
| Producers and Consumers are aware of changes made to the cluster | All MQ clients require a CCDT file to know of changes if not using a gateway QMGR |
| Can have `n number` of replication partitions | Can have 2 replicas (RDQM) of a QMGR, Multi Instance QMGRs |
| Simple load balancing | Load balancing can be simple or more complex using weights and affinity |
| Can reread messages | Cannot reread messages that have been already processed | 
| All clients connect using a single connection method | MQ has Channels which allow different clients to connect, each having the ability to have different security requirements | 
| Data Streams processing built in, using Kafka topic for efficiency| Stream processing is not built in, but using third party libraries, like MicroProfile Reactive Messaging, ReactiveX, etc. |
| Has connection security, authentication security, and ACLs (read/write to Topic) | Has connection security, channel security, authentication security, message security/encryption, ACLs for each Object, third party plugins (Channel Exits) |
| Built on Java, so can run on any platform that support Java 8+ | Latest native on AIX, IBM i, Linux systems, Solaris, Windows, z/OS, run as Container | 
| Monitoring by using statistics provided by Kafka CLI, open source tools, Prometheus | Monitoring using PCF API, MQ Explorer, MQ CLI (runmqsc), Third Party Tools (Tivoli, CA APM, Help Systems, Open Source, etc) |

### Migrating from MQ to Kafka

When the real conditions as listed above are met, architects may assess if it makes sense to migrate MQ application to Kafka. 
Most of the time the investment is not justified. Modern MQ supports the same DevOps and deployment pattern as other cloud native applications.

JEE or mainframe applications use MQ in transaction to avoid duplicate messages or loss of messages. Supporting exactly once delivery in Kafka
needs some configuration and participation of producer and consumers: far more complex to implement.

We recommend adopting the two messaging capabilities for any business applications.

## Kafka Streams vs Apache Flink

Once we have setup data streams, we need technology to support near real-time analytics and complex event processing. Historically, analytics performed on static data was done using batch reporting techniques. However, if
insights have to be derived in near real-time, event-driven architectures help to analyse and look for patterns within events.

[Apache Flink](https://flink.apache.org) (2016) is a framework and **distributed processing** engine for stateful computations over unbounded and bounded data streams. It is considered to be superior to Apache Spark and Hadoop. It supports batch and graph processing and complex event processing. 
The major stream processing features offered by Flink are:

* Support for event time and out of order streams: use event time for consistent results
* Consistency, fault tolerance, and high availability: guarantees consistent state updates in the presence of failures and consistent data movement between selected sources and sinks
* Low latency and high throughput: tune the latency-throughput trade off, making the system suitable for both high-throughput data ingestion and transformations, as well as ultra low latency (millisecond range) applications.
* Expressive and easy-to-use APIs in Scala and Java: map, reduce, join, with window, split,... Easy to implement the business logic using Function.
* Support for sessions and unaligned windows: Flink completely decouples windowing from fault tolerance, allowing for richer forms of windows, such as sessions.
* Connectors and integration points: Kafka, Kinesis, Queue, Database, Devices...
* Developer productivity and operational simplicity: Start in IDE to develop and deploy and deploy to Kubernetes, [Yarn](https://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/YARN.html), [Mesos](http://mesos.apache.org/) or containerized
* Support batch processing
* Includes Complex Event Processing capabilities

Here is simple diagram of Flink architecture from the Flink web site:

 ![Flink components](https://ci.apache.org/projects/flink/flink-docs-release-1.12/fig/distributed-runtime.svg)


See this [technology summary](/technology/flink/).

See also [this article from Confluent](https://www.confluent.io/blog/apache-flink-apache-kafka-streams-comparison-guideline-users) about comparing Flink with Kafka Streams.