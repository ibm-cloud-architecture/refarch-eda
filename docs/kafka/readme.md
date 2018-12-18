# Apache Kafka

In this article we are presenting real time event processing and analytics using **Kafka** deployed on Kubernetes cluster. We are documenting how to deploy this capability on IBM Cloud Private using a [**Kafka**](https://Kafka.apache.org/) open source distribution or the new [IBM Event Streams product](https://developer.ibm.com/messaging/event-streams/) and how to remote connect from hosts outside of the cluster, how to support high availability...

Update 12/2018 - *Author: [Jerome Boyer](https://www.linkedin.com/in/jeromeboyer/)*  

## Table of contents

* [Introduction - concepts - use cases](#introduction)
* [Details](#kafka-stream-details)
* [Architecture and design discussion](#architecture)
* [Deployment](#deployment)
  * [Kafka deployment](../../deployments/kafka/README.md)
  * [Zookeeper deployment](../../deployments/zookeeper/README.md)
  * [IBM Event Streams on IBM Cloud Private](../../deployments/eventstreams/README.md)
* [Producer implementation considerations](./producers.md)
* [Consumer implementation considerations](./consumers.md)
* [Monitoring with Prometheus and Grafana](./monitoring.md)
* [Streaming applications](./kafka-stream.md)
* [Compendium](#compendium)

## Introduction

[Kafka](https://Kafka.apache.org/) is a distributed streaming platform with the following key capabilities:

* Publish and subscribe streams of records. Data are stored so consuming applications can pull the information they need, and keep track of what they have seen so far.
* Atomic broadcast, send a record once, every subscriber gets it once.
* Store streams of data records on disk and replicate within the cluster for fault-tolerance. Keep data for a time period before delete.
* Built on top of the ZooKeeper synchronization service to keep topic, partitions and offsets states high available.

### Use cases

The typical use cases where **Kafka** helps are:

* Aggregation of event coming from multiple producers.
* Monitor distributed applications to produce centralized feed of operational data.
* Logs collector from multiple services
* Manage loosely coupled communication between microservices. (See [this note](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/service-mesh.md#asynchronous-loosely-coupled-solution-using-events) I present a way to support a service mesh solution using asynchronous event)

## Key concepts

The diagram below presents the key components we are detailing in this section:  

 ![](images/kafka-hl-view.png)

### Brokers

* **Kafka** runs as a cluster of one or more **broker** servers that can, in theory, span multiple data centers.
* The **Kafka** cluster stores streams of records in **topics**. Topic is referenced by producer to send data too, and subscribed by consumers. 

In the figure above, the **Kafka** brokers are allocated on three servers, with three data replicas.

### Topics 

Topics represent end points to put or get records to.

* Each record consists of a key, a value, and a timestamp.
* Producers publish data records to topic and consumers subscribe to topics. When a record is produced without specifying a partition, a partition will be chosen using a hash of the key. If the record did not provide a timestamp, the producer will stamp the record with its current time (creation time or log append time). Producers hold a pool of buffer to keep records not yet transmitted to the server.
* Kafka store log data in its `log.dir` and topic maps to subdirectories in this log directory.
* **Kafka** uses topics with a pub/sub combined with queue model: it uses the concept of consumer group to divide the processing over a collection of consumer processes, running in parallel, and message can be broadcasted to multiple groups.
* Consumer performs asynchronous pull to the connected broker via the subscription to a topic.

The figure below illustrates one topic having multiple partitions, replicated within the broker cluster:  
![](./images/kafka-topic-partition.png)  

### Partitions

Partitions are used by producers and consumers and data replication. Partitions are basically used to parallelize the event processing when a single server would not be able to process all events, using the broker clustering. So to manage increase in the load of messages Kafka uses partitions.

![](images/topic-part-offset.png)   

* Each broker may have zero or more partitions per topic. When creating topic we specify the number of partition to use. Each partition will run on a separate server. So if you have 5 brokers you can define topic with 5 partitions. 
* Each partition is a time ordered immutable sequence of records, that are persisted for a long time period. It is a log. Topic is a labelled log.
* Each partition is replicated across a configurable number of servers for fault tolerance. The number of partition will depend on characteristics like the number of consumers, the traffic pattern, etc...
* Each partitioned message has a unique sequence id called **offset** ("abcde, ab, a ..." in the figure above are offsets). Ids are defined when event arrives at the broker level, and are local to the partition. They are unmutable. 
* When a consumer reads a topic, it actually reads data from all the partitions. As a consumer reads data from a partition, it advances its offset. To read an event the consumer needs to use the topic name, the partition number and the last offset to read from. 
* Partitions guarantee that data with the same keys will be sent to the same consumer and in order. They improve throughtput.


### Zookeeper

Zookeeper is used to persist the component and platform states and it runs in cluster to ensure high availability. One zookeeper server is the leader and other are used in backup.

* Kafka does not keep state regarding consumers and producers.
* Offsets are maintained in Zookeeper or in **Kafka**, so consumers can read next message (or from a specific offset) correctly even during broker server outrages. We are detailing this in the asset consumer implementation in [this repository](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/tree/master/asset-consumer).
* Stream processing is helpful for handling out-of-order data, *reprocessing* input as code changes, and performing stateful computations. It uses producer / consumer, stateful storage and consumer groups. It treats both past and future data the same way.

### Consumer group

This is the way to group of consumers so the processing of event downstream is parallelized. The number of consumers in a group is the same as the number of partition defined in a topic.  

## Kafka Stream Details

I recommend reading this excellent introduction from Jay Kreps @confluent: [Kafka stream made simple](https://www.confluent.io/blog/introducing-kafka-streams-stream-processing-made-simple/) to get familiar of why Kafka stream.

**Kafka** Stream has the following capabilities:

* Embedded library for your application to use.
* Integrate tables for state persistence with streams of events.
* Consumes continuous real time flows of records and publish new flows.
* Supports exactly-once processing semantics to guarantee that each record will be processed once and only once even when there is a failure.
* Stream APIs transform, aggregate and enrich data, per record with milli second latency, from one topic to another one.
* Supports stateful and windowing operations by processing one record at a time.
* Can be integrated in java application and microservice. No need for separate processing cluster. It is a Java API. Stream app is done outside of the broker code!.
* Elastic, highly scalable, fault tolerance, it can recover from failure.
* Deployed as container to Kubernetes or other orchestration platform, with some clear understanding of the impact.

**Kafka** stream should be your future platform for asynchronous communication between your microservices to simplify interdependencies between them. (We are covering this pattern in [this service mesh article.](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/service-mesh.md))

## Architecture

![](images/kafka-stream-arch.png)

* **Kafka** Streams partitions data for processing it. Partition enables data locality, elasticity, scalability, high performance, parallelism, and fault tolerance.
* The keys of data record determine the partitioning of data in both **Kafka** and **Kafka Streams**.
* An application's processor topology is scaled by breaking it into multiple tasks.
* Tasks can then instantiate their own processor topology based on the assigned partitions.

See [this article from Confluent](https://docs.confluent.io/current/streams/architecture.html) for deeper architecture presentation.

When you want to deploy solution that spreads over multiple regions to support global streaming, you need to address challenges like:

* How do you make data available to applications across multiple data centers?
* How to serve data closer to the geography?
* How to be compliant on regulations, like GDPR?
* How to address no duplication of records?

### Solution considerations

There are a set of design considerations to assess for each **Kafka** solution:

#### Topics

Performance is more a function of number of partitions than topics. Expect that each topic has at least one partition. When considering latency you should aim for limiting to hundreds of topic-partition per broker node.

The approach to use one topic per data type is natural and will work, but when addressing microservice to microservice communication it is less relevant to use this pattern.
The important requirement to consider is the sequencing or event order. When event order is very important then use a unique partition, and use the entity unique identifier as key. Ordering is not preserved across partitions.

When dealing with entity, independent entities may be in separate topics, when strongly related one may stay together.

With **Kafka** stream, state store or KTable, you should separate the changelog topic from the others.

#### Producers

When developing a record producer you need to assess the following:

* What is the expected throughput to send events? Event size * average throughput combined with the expected latency help to compute buffer size.
* Can the producer batch events together to send them in batch over one send operation?
* Is there a risk for loosing communication? Tune the RETRIES_CONFIG and buffer size
* Assess *once to exactly once* delivery requirement. Look at idempotent producer.

See [implementation considerations discussion](./producers.md)

#### Consumers

From the consumer point of view a set of items need to be addressed during design phase:

* Do you need to group consumers for parallel consumption of events?
* What is the processing done once the record is processed out of the topic? And how a record is supposed to be consumed?.
* How to persist consumer committed position? (the last offset that has been stored securely)
* Assess if offsets need to be persisted outside of Kafka?. From version 0.9 offset management is more efficient, and synchronous or asynchronous operations can be done from the consumer code. 
* Does record time sensitive, and it is possible that consumers fall behind, so when a consumer restarts he can bypass missed records?
* Do the consumer needs to perform joins, aggregations between multiple partitions?

See [implementation considerations discussion](./consumers.md)

### High Availability in the context of Kubernetes deployment

The combination of kafka with kubernetes seems to be a sound approach, but it is not that easy to achieve. Kubernetes workloads prefer to be stateless, Kafka is stateful platform and manages it own brokers, and replications across known servers. It knows the underlying infrastructure. In kubernetes nodes and pods may change dynamically.

Per design as soon as you have at least three brokers Kafka is highly available using its replication mechanism and leading partition process. Each partitition is replicated at least 3 times and allocated in different brokers. One is the lead. In the case of broker failure, existing partition in running broker will take the lead:

![](images/kafka-ha.png)

For any Kubernetes deployment real high availability is constrained by the application / workload deployed on it. The Kubernetes platform supports high availability by having at least the following configuration:

* At least three master nodes (always a odd number). One is active at master, the others are in standby.
* Three proxy nodes.
* At least three worker nodes, but with zookeeper and Kafka clusters need to move to 6 nodes as we do not want to have zookeeper nodes with Kafka cluster broker on the same host.
* Externalize the management stack to three manager nodes
* Shared storage outside of the cluster to support private image registry, audit logs, and statefulset data persistence.
* Use `etcd` cluster: See recommendations [from this article](https://github.com/coreos/etcd/blob/master/Documentation/op-guide/clustering.md). The virtual IP manager assigns virtual IP address to master and proxy nodes and monitors the health of the cluster. It leverages `etcd` for storing information, so it is important that `etcd` is high available too.  

For IBM Cloud private HA installation see the [product documentation](https://www.ibm.com/support/knowledgecenter/en/SSBS6K_2.1.0.3/installing/custom_install.html#HA)

Traditionally disaster recovery and high availability were always consider separated subjects. Now active/active deployment where workloads are deployed in different data center, is more and more a common request. IBM Cloud Private is supporting [federation cross data centers](https://github.com/ibm-cloud-architecture/refarch-privatecloud/blob/master/Resiliency/Federating_ICP_clusters.md), but you need to ensure to have low latency network connections. Also not all deployment components of a solution are well suited for cross data center clustering.

For **Kafka** context, the *Confluent* web site presents an interesting article for [**Kafka** production deployment](https://docs.confluent.io/current/kafka/deployment.html). One of their recommendation is to avoid cluster that spans multiple data centers and specially long distance ones.
But the semantic of the event processing may authorize some adaptations. For sure you need multiple Kafka Brokers, which will connect to the same ZooKeeper ensemble running at least five nodes (you can tolerate the loss of one server during the planned maintenance of another). One Zookeeper server acts as a lead and the two others as stand-by.

The schema above illustrates the recommendations to separate Zookeeper from **Kafka** nodes for failover purpose as zookeeper keeps state of the **Kafka** cluster. We use Kubernetes [anti-affinity](https://kubernetes.io/docs/concepts/configuration/assign-pod-node/#affinity-and-anti-affinity) to ensure they are scheduled onto separate worker nodes that the ones used by zookeeper. It uses the labels on pods with a rule like:
> **Kafka** pod should not run on same node as zookeeper pods.  

Here is an example of such spec:

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: with-pod-affinity
spec:
  affinity:
    podAntiAffinity:
          requiredDuringSchedulingIgnoredDuringExecution:
            labelSelector:
            matchExpressions:
            - key: name
              operator: In
              values:
              - gc-zookeeper
          topologyKey: kubernetes.io/hostname
```

We recommend doing the [running zookeeper in k8s tutorial](https://kubernetes.io/docs/tutorials/stateful-application/zookeeper) for understanding such configuration.
Provision a **fast storage class** for persistence volume.

**Kafka** uses the log.dirs property to configure the driver to persist logs. So you need to define multiple volumes/ drives to support log.dirs.

Zookeeper should not be used by other applications deployed in k8s cluster, it has to be dedicated for one **Kafka** cluster only.

In a multi-cluster configuration being used for disaster recovery purposes, messages sent between clusters will have different offsets in the two clusters. It is usual to use timestamps for position information when restarting applications for recovery after a disaster. We are addressing offset management in one of our consumer projects [here](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/).

For configuring ICP for HA on VmWare read [this note](https://github.com/ibm-cloud-architecture/refarch-privatecloud/blob/master/Configuring_ICP_for_HA_on_VMware.md).

For **Kafka** streaming with stateful processing like joins, event aggregation and correlation coming from multiple partitions, it is not easy to achieve high availability cross clusters: in the strictest case every event must be processed by the streaming service exactly once. Which means:

* producer emit data to different sites and be able to re-emit in case of failure. Brokers are known by producer via a list of hostname and port number.
* communications between zookeepers and cluster nodes are redundant and safe for data losses
* consumers ensure idempotence... They have to tolerate data duplication and manage data integrity in their persistence layer.

Within Kafka's boundary, data will not be lost, when doing proper configuration, also to support high availability the complexity moves to the producer and the consumer implementation.

**Kafka** configuration is an art and you need to tune the parameters by use case:

* Partition replication for at least 3 replicas. Recall that in case of node failure,  coordination of partition re-assignments is provided with ZooKeeper.
* End to end latency needs to be measured from producer (when a message is sent) to consumer when it is read. A consumer is able to get a message when the broker finishes replicating to all in-synch replicas.
* Use the producer buffering capability to pace the message to the broker. Can use memory or time based threshold.
* Define the number of partitions to drive consumer parallelism. More consumers running in parallel the higher is the throughput.
* Assess the retention hours to control when old messages in topic can be deleted
* Control the maximum message size the server can receive.    

Zookeeper is not CPU intensive and each server should have a least 2 GB of heap space and 4GB reserved. Two cpu per server should be sufficient. Servers keep their entire state machine in memory, and write every mutation to a durable WAL (Write Ahead Log) on persistent storage. To prevent the WAL from growing without bound, ZooKeeper servers periodically snapshot their in memory state to storage. Use fast and dynamically provisioned persistence storage for both WAL and snapshot.


## Compendium

* [Start by reading Kafka introduction](https://Kafka.apache.org/intro/)
* [Another introduction from the main contributors: Confluent](http://www.confluent.io/blog/introducing-Kafka-streams-stream-processing-made-simple)
* [Develop Stream Application using Kafka](https://Kafka.apache.org/11/documentation/streams/)
* [Validating the Stream deployment](https://developer.ibm.com/messaging/event-streams/docs/validating-the-deployment/)
* [Kafka on Kubernetes using stateful sets](https://github.com/kubernetes/contrib/tree/master/statefulsets/Kafka)
* [IBM Event Streams product based on Kafka delivered in ICP catalog](https://developer.ibm.com/messaging/event-streams/)
* [IBM Developer works article](https://developer.ibm.com/messaging/event-streams/docs/learn-about-Kafka/)
* [Install Event Streams on ICP](https://developer.ibm.com/messaging/event-streams/docs/install-guide/)
* [Spark and Kafka with direct stream, and persistence considerations and best practices](http://aseigneurin.github.io/2016/05/07/spark-Kafka-achieving-zero-data-loss.html)
* [Example in scala for processing Tweets with Kafka Streams](https://www.madewithtea.com/processing-tweets-with-Kafka-streams.html)
