# Apache Kafka

In this article we are summarizing what Apache [**Kafka**](https://Kafka.apache.org/) is and group some references and notes we gathered during our different implementations and  **Kafka** deployment within Kubernetes cluster. We are documenting how to deploy Kafka on IBM Cloud Private or deploying [IBM Event Streams product](https://developer.ibm.com/messaging/event-streams/). This content does not replace [the excellent introduction](https://Kafka.apache.org/intro/) every developer using Kafka should read.

Update 04/2019 - *Author: [Jerome Boyer](https://www.linkedin.com/in/jeromeboyer/)*  

## Introduction

[Kafka](https://Kafka.apache.org/) is a distributed event streaming platform with the following key capabilities:

* Publish and subscribe streams of records. Data are stored so consuming applications can pull the information they need, and keep track of what they have seen so far.
* Atomic broadcast, send a record once, every subscriber gets it once.
* Store streams of data records on disk and replicate within the cluster for fault-tolerance. Keep data for a time period before delete.
* Built on top of the ZooKeeper synchronization service to keep topic, partitions and metadat highly available.

### Use cases

The typical use cases where **Kafka** helps are:

* Aggregation of event coming from multiple producers.
* Monitor distributed applications to produce centralized feed of operational data.
* Logs collector from multiple services
* Implement [event soucing pattern](../evt-microservices/ED-patterns.md) out of the box
* Manage loosely coupled communication between microservices. (See [this note](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/service-mesh.md#asynchronous-loosely-coupled-solution-using-events) where I present a way to support a service mesh solution using asynchronous event)

## Key concepts

The diagram below presents Kafka's key components:  

 ![](images/kafka-hl-view.png)

### Brokers

* **Kafka** runs as a cluster of one or more **broker** servers that can, in theory, span multiple data centers. It is really possible if the latency is very low at the 10ms or better as there are a lot of communication between kafka brokers and kafka and zookeepers.  
* The **Kafka** cluster stores streams of records in **topics**. Topic is referenced by producer to send data too, and subscribed by consumers to get data. 

In the figure above, the **Kafka** brokers are allocated on three servers, with data within the topic are replicated three times. In production it is recommended to use 5 nodes to authorise planned failure and un-planned failure. 

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
* Each partitioned message has a unique sequence id called **offset** ("abcde, ab, a ..." in the figure above are offsets). Those offset ids are defined when events arrived at the broker level, and are local to the partition. They are unmutable. 
* When a consumer reads a topic, it actually reads data from all the partitions. As a consumer reads data from a partition, it advances its offset. To read an event the consumer needs to use the topic name, the partition number and the last offset to read from. 
* Partitions guarantee that data with the same keys will be sent to the same consumer and in order.
* Adding more partition, in the limit of number of borkers, improve throughtput.


### Zookeeper

Zookeeper is used to persist the component and platform states and it runs in cluster to ensure high availability. One zookeeper server is the leader and other are used in backup. 

* Kafka does not keep state regarding consumers and producers.
* Depends on kafka version, offsets are maintained in Zookeeper or in **Kafka**: newer versions use an internal Kafka topic called __consumer_offsets. In any case consumers can read next message (or from a specific offset) correctly even during broker server outrages. 
* Access Controls are saved in Zookeeper

### Consumer group

This is the way to group consumers so the processing of event is parallelized. The number of consumers in a group is the same as the number of partition defined in a topic. We are detailinh consumer group implementation in [this note](./consumers.md)


## Architecture

As a distributed cluster, kafka brokers ensure high availability to process new events. Topic has replication factor to support not loosing data in case of broker failure. You need at least 3 brokers to ensure availability and a replication of 3 for each topic. Partition enables data locality, elasticity, scalability, high performance, parallelism, and fault tolerance. Each partitition is replicated at least 3 times and allocated in different brokers. One replicas is the lead. In the case of broker failure, one of the existing partition in remaining running brokers will take the lead:

![](images/kafka-ha.png)

The keys in the data record determine the partitioning of data in **Kafka**. The records with the same key will be in the same partition.

As kafka is keeping its cluster states in zookeeper, you also need to have at least a three node cluster for zookeeper. Writes to Zookeeper are only performed on changes to the membership of consumer groups or on changes to the Kafka cluster itself. Assuming you are using the most recent kafka version (after 0.9), it is possible to have a unique zookeeper cluster for multiple kafka clusters. But the latency between Kafka and zookeeper needs to be under few milliseconds anyway. Zookeepers and Brokers should have high availability communication, and each borker and node allocated on different racks and blades.

![](images/ha-comp.png)

Consumers and producers are using a list of bootstrap server names (also named advertiser.listeners ) to contact the cluster. The list is used for cluster discovery, it does not need to keep the full set of server names or ip addresses. A Kafka cluster has exactly one broker that acts as the controller.

Per design Kafka aims to run within a single data center. But it is still recommended to use multiple racks connected with low laterncy dual network. With multiple racks you will have better fault tolerance, as one rack failure will impact only one broker. There is a configuration property to assign kafka broker using rack awareness. (See [this configuration](https://kafka.apache.org/documentation/#brokerconfigs) from the product documentation).

Always assess the latency requirements and consumers needs. Throughtput is linked to the number of partitions within a topic and having more consumers running in parallel. Consumers and producers should better run on separate servers than the brokers nodes. 
For high availability assess any potential single point of failure, such as server, rack, network, power supply...

The figure below illustrates a kubernetes deployment, where zookeeper and kafka brokers are allocated to 3 worker nodes (could be better 5 nodes) and evnet driven microservices are deployed in separate nodes. Those microservices are consumers and producers of events from one to many topics. Kafka may be used as event sourcing.

![](images/k8s-deploy.md)

To add new broker, we can deploy the runtime to a new server / rack / blade, and give a unique ID. It will process new topic, but it is possible to use tool to migrate some existing topic/ partitions to the new server. The tool is used to reassign partitions across brokers. An ideal partition distribution would ensure even data load and partition sizes across all brokers. 

### Multi regions

With the current implementation it is recommended to have one cluster per data center. Consumers and producers are co-located to the broker cluster. When there are needs to keep some part of the data replicated in both data center, you need to assess what kind of data can be aggregated, and if Kafka mirroring tool can be used. The tool consumes from a source cluster, from a given topic, and produces to a destination cluster with the same named topic. It keeps the message key for partitioning so order is preserved. 

![](images/ha-dc1.png)

The above diagram is using Kafka MirroeMaker with a master to slave deployment. Within the data center 2, the brokers are here to manage the topics and event. As there is no consumers running nothing happen. Consumers and producers can be started when DC1 fails. In fact we could have consumer within the DC2 processing topics to manage a readonly model, keeping in memory their projection view, as presented in the CQRS pattern. 

The second solution is to use one mirror maker in each site, for each topic. This is an active - actice topology: consumers and producers are on both sites. But to avoid infinite loop, 
When you want to deploy solution that spreads over multiple regions to support global streaming, you need to address challenges like:

* How do you make data available to applications across multiple data centers?
* How to serve data closer to the geography?
* How to be compliant on regulations, like GDPR?
* How to address no duplication of records?


### Solution considerations

There are a set of design considerations to assess for each **Kafka** solution:

#### Topics

Performance is more a function of number of partitions than topics. Expect that each topic has at least one partition. When considering latency you should aim for limiting to hundreds of topic-partition per broker node.

What of the most important question is what topics to use?. What is an event type? Should we use one topic to support multiple event types? 
Let define that an event type is linked to a main business entity like an Order, a ship, a FridgeredContainer. OrderCreated, OrderCancelled, OrderUpdated, OrderClosed are events linked to the states of the Order. The order of those events matter. So the natural approach is to use one topic per data type or schema, specially when using the topic as Event Sourcing where event order is important to build the audit log. You will use a unique partition to support that. The orderID is the partition key and all events related to the order are in the same topic.

The important requirement to consider is the sequencing or event order. When event order is very important then use a unique partition, and use the entity unique identifier as key. Ordering is not preserved across partitions.

When dealing with entity, independent entities may be in separate topics, when strongly related one may stay together.

Other best practices:
* When event order about the same entity is important use the same topic and define partition key.
* When two entities are related together by containment relationship then they can be in the same topic. 
* Different entities are separated to different topics.
* It is possible to group topics in coarse grained one when we discover that several consumers are listening to the same topics. 
* Clearly define the partition key as it could be an compound key based on multiple entities.

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

