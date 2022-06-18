---
title: Flow architecture
description: James Urquhaert's "Flow architecture" book summary
---

** Added 1/21/2022

From the [James Urquhart's book: Flow architecture](https://www.amazon.com/Flow-Architectures-Streaming-Event-Driven-Integration/dp/1492075892/ref=sr_1_1?keywords=flow+architectures&qid=1637675009&sr=8-1) and
personal studies.

As more and more of our businesses “go digital”, the groundwork is in place to fundamentally
change how real-time data is exchanged across organization boundaries. Data from different sources
can be combined to create a holistic view of a business situation.

**Flow** is networked software integration that is event-driven, loosely coupled, and highly adaptable and extensible.

Value is created by interacting with the flow, and not just the data movement.

Since the beginning of IT as an industry, we are digitizing and automating the exchanges of value, and we spend
a lot of time and money to execute key transactions with less human intervention.
However, most of the integrations we execute across organizational boundaries today are not
in real time. Today, most, perhaps all—digital financial transactions in the world economy
still rely on batch processing at some point in the course of settlement.

There is no consistent and agreed-upon mechanism for exchanging signals for immediate action across companies or industries.

It is still extremely rare for a company to make real-time data available for 
unknown consumers to process at will.

This is why modern event-driven architecture (EDA) will enable profound changes in 
the way companies integrate. EDAs are highly decoupled architectures, meaning there 
are very few mutual dependencies between the parties at both ends of the exchange.


## 1- Flow characteristics

* Consumer applications requests data streams through self-service interfaces, and get the data continuously.
* Producers maintain control of relevant information to transmit and when to transmit.
* **Event** packages information of data state changes, with timestamp and unique ID. 
The context included with the transmitted data allows the consumer to better understand the nature of that data. [CloudEvent](https://cloudevents.io/) helps defining such context.
* The transmission of a series of events between two parties is called an **event stream**.

The more streams there are from more sources, the more flow consumers will be drawn to 
those streams and the more experimentation may be done. Over time, organizations will find 
new ways to tie activities together to generate new value.

**Composable architectures** allow the developer to assemble fine grained parts using 
consistent mechanisms for both inputting data and consuming the output.
In **contextual architectures**, the environment provides specific contexts in 
which integration can happen. Developer must know a lot about the data that is available,
 the mechanism by which the data will be passed, the rules for coding and deploying 
 the software.

EDA provides a much more composable and evolutionary approach for building event and data streams.

## 2- Business motivations

* Do digital transformation to improve customer experiences. Customers expect their data to 
be used in a way that is valuable to them, not just to the vendors. Sharing data between organizations
 can lead to new business opportunities. This is one of the pilard of Society 5.0. 

> The Japan government defined **Society 5.0** as "A human-centered society that balances economic 
advancement with the resolution of social problems by a system that highly integrates
cyberspace and physical space".

* Improve process automation, to drive efficiencies and profitability. The most limiting constraint in the 
process hides any improvements made to other steps. Finding constraints is where [value stream mapping](https://tkmg.com/books/value-stream-mapping/) shines:
it uses lead time (queue time) and actual time to do the work. EDA will help to get time stamp and data 
for steps in the process that are not completely in scope of a business process: may be cross business boundaries.
* Extract innovative value from data streams. Innovation as better solution for existing problem, or as new
solution to emerging problems.

To improve process time, software needs accurate data at the time to process the work. As business evolve,
having a rigid protocol to get the data, impacts process time. A business will need to experiment with new data sources
 when they are available and potentially relevant to their business.

**Stream processing improves interoperability (exchange data)**

Innovation is not adaptation. Companies must adapt constantly just to survive, like adding features on a product
to pace with competition. Digital transformation aimed at avoiding competitive disruption is not innovation.

As the number of stream options grows, more and more business capabilities will be 
defined in terms of stream processing. This will drive developers to find easier ways 
to discover, connect to, and process streams.

### Enabler for flow adoption

* **Lowering the cost of stream processing**: Integration costs dominate modern IT budgets.
For many integrations, the cost of creating interaction between systems is simply too high for what little value is gained.
With common interfaces and protocols that enable flows, the integration cost will be lower
and people will find new uses for streaming that will boost the overall demand for streaming technologies. The [Jevons paradox](https://en.wikipedia.org/wiki/Jevons_paradox) at work
* **Increasing the flexibility in composing data flows**: "pipe" data streams from one processing 
system to another through common interfaces and protocols.
* **Creating and utilizing a rich market ecosystem around key streams**. The equities markets have all moved entirely to electronic forms of executing their marketplaces.
Health-care data streams for building services around patient data. Refrigerators streaming data to grocery
delivery services. 

Flow must be secure (producers maintain control over who can access their events), 
agile (change schema definitions), 
timely (Data must arrive in a time frame that is appropriate for the context to which it is being applied), 
manageable and retain a memory of its past. 

Serverless, stream processing, machine learning, will create alternative to batch processing.

## 3- Market

SOA has brought challenges for adoption and scaling. Many applications have their own interfaces
and even protocols to expose their functionality, so most integrations need protocol and 
data model translations. 

The adoption of queues and adaptors to do data and protocol translation was a scalable solution. 
Extending this central layer of adaptation was the Enterprise Service Bus, with intelligent
pipes / flows. 

Message queues and ESBs are important to the development of streaming architectures but
to support scalability and address complexity more decoupling is needed between 
producers and consumers.

For IoT [MQTT](https://mqtt.org/) is the standard for messaging protocols in a lightweight pub/sub 
transport protocol. MQTT supports 3 service levels: 0 - at most once, 1- at least once, 2 - exactly once.
It allows for messaging between device to cloud and cloud to device. It supports for persistent sessions
 reduces the time to reconnect the client with the broker.
The MQTT broker manages a list of topics, which enable it to identify groups of subscribers interested in a collection of messages.

For event processing three type of engines:

* **Functions** (including low-code or no-code processors): WAS lambda, Knative eventing, Flink, Storm. Mendix and Vantiq have event-driven low code platform.
* **log-based event streaming platforms**: Apache Kafka, Apache Pulsar, AWS Kinesis, and Microsoft Azure Event Hubs. Topic becomes a system of record, as event sourcing pattern implementation.
* **real-time stateful systems**: *Digital twins* are software agents supporting the problem domain
in a stateful manner. Behavior is supported by code or rules, and relationship between agents. 
Agents can monitor the overall system state. [Swim.ai](https://www.swim.ai/) builds its model dynamically from the event stream and provides built-in machine learning capabilities that enable both continuous learning and high performance model execution

Mainstream adoption of flow itself will be five to ten years from now (2020). Flow will have to prove that 
it meets security criteria for everything from electronic payments, to health-care data, to classified
 information. The CNCF’s [CloudEvents](https://cloudevents.io/) specification, for instance, strongly suggests payloads be encrypted.
There is no single approach to defining an event with encryption explicitly supported 
that can be read by any event-consuming application (MQTT, AMQP, have different encryption and TLS add more for 
TCP connection).

Consumers need assurances that the data they receive in an event is valid and accurate, a practice 
known as **data provenance**.  

> Data provenance is defined as “a record of the inputs, entities, 
systems, and processes that influence data of interest, providing a historical record of the 
data and its origins"

Provenance has to maintained by the producer as a checksum number created by parsing the event data, and encrypted
by the producer's key. CloudEvent has metadata about the message. When sent to Kafka they are 
immutable record. Now the traceability of the consumers in kafka world is a major challenge.
Blockchain may also be used to track immutable record with network parties attest its accuracy.


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

Finally, producers will want to trust that only authorized consumers are using the events they produce.
Also it may be possible to imagine a way to control the intellectual property of the data so producer can keep 
its ownership. Data consumption should be done via payment like we do with music subscription.

## Flow patterns:

### Collector pattern

The Collector pattern is a pattern in which a single consumer subscribes to topics from multiple producers. 

### Distributor pattern

Each event in a stream is distributed to multiple consumers. It could be a hard problem to solve
when doing it across geographically distributed systems. Edge computing can be used to distribute
 streaming endpoints closer to the consumers that need those streams. Alternate
 is to moving the event processing close to the source. For many Distributor use cases, 
 partitioning the data by region is probably smart, and flow interfaces will need to take 
 this into account.

### Signal pattern

The Signal pattern is a general pattern that represents functions that exchange data between
 actors based on a distinct function or process, in can be seen as a traffic cop. 
 It supports multiple producers and multiple consumers. The signal pattern is supported
 by multiple event processing each handling one aspect of the event processing.

Stream processing may route event streams between several distributed edge computing services as 
well as core shared services, but then we need management layer to get global view of the systems.
They need to be integrated into observability tool. But the "single pane of glass" is often a lure
as distributed systems require distributed decision-making. More local solutions are more agile, flexible
and better address local problems for improved resilience.  

One of the challenge of complex adaptive systems is that any agent participating in 
the system has difficulty seeing how the system as a whole operates,
because of its limited connections to other neighbor agents.

### Facilitator pattern

A specialized form of Signal pattern, facilitator is a "broker" to match producers' events to
consumers' demands. It is like matching sellers with buyers.

## 4- Identifying flow in your business

The classical usee case categories:

* **Addressing and discovery**: In modern complex systems environments, multiple systems need to be informed of the new entity, be able to utilize to assign
work to it. Addressing and discovery happens across organizational boundaries (for example in real-time inventory SKU is used to identify item for both supplier and retailers).
To seek such use cases, look at tracking problems like who or what is involved in a problem domain that is difficult to scale.
With event stream centric approach, A&D is done via a registry service used by new agents to indicate their existence, and the service publishes an event to a topic
to broadcast the information about the new agent. A second option is to use a discovery service to watch  
specific event stream for certain transmissions that indicate the presence of an agent. [Swim.ai](https://www.swim.ai/) continuously process and analyze 
streaming data in concert with contextual data to inform business-critical, operational decisions. See also [SwimOS](https://github.com/swimos) or Apache Flink.

* **Command and control**: sources are connected to key decision-making and action-taking services to complete a business task. So they are everywhere in any business.
A typical example of such use case, is the Supervisory Control And Data Acquisition, used in manufacturing, or energy production.
Try to ask: *where does the organization depend on timely responses to changes in state?*
C&C can be supported by centralized control with events come from multiple sources to stateless or stateful services, to apply
real-time analysis and decision-making algorithms to those streams. Output events are published to sinks for future processing.
Scaling with a centralized control approach is not straightforward, as getting the right events to the right processing instances can be a challenge.
Also when we need the compute global aggregates by looking at the state of various agents in the systems is more complex,
as it needs to integrate with stateful stream processing. Actions can be triggered by state changes, triggers that fire at specific times, or even API requests from other applications or services.

An alternate is to use distributed control, like applying the decision-making logic at the edge. 

* **Query and observability:** querying or monitoring individual agents or specific groups of agents. The problem is
to locate the right agent target of the query, and get current state or history from that agent. 
* **Telemetry and analytics:** focuses on understanding systems behavior, and get real-time big data insights (e.g. Click streams). 
Need to assess which insights require understanding the emerging behavior of a system of agents emitting vast amounts of data.

Interesting presentations:

* [Voxxed Athens 2018 - Eventing, Serverless, and the Extensible Enterprise by Clemens Vasters](https://www.youtube.com/watch?v=qCNXUUlhJJE)

## 5- Model Flow

Use Event storming to build a timeline of events that are required to complete a complex task, and to get
and understanding of the people, systems, commands and policies that affect the event flow. 
The Event Storming process is a highly interactive endeavor that :brings subject matter experts 
together to identify the flow of events in a process or system

1. Identify the business activities that you would like to model in terms of event flow
1. Begin by asking the group to identify the events that are interesting and/or required for that business activity
1. Place events along a timeline from earliest action to latest action
1. Capture:

    * The real-world influences on the flow, such as the human users or external systems that produce or consume events
    * What commands may initiate an event
    * What policies are activated when an event takes place. A policy usually initiates a new command. Events always result in a policy action unless the event is generating output.
    * What are the outputs from the event flow.

When designing the solution assess:

* When the event is simply to broadcast facts for consumption by interested parties. The producer contract is
 simply to promise to send events as soon as they are available.
* If consumers can come and go, and experiment with the consumption of a stream with little risk of consequences if they choose not to continue
* When event is part of an interaction around an intent, requiring a conversation with the consumer
* Is the event a stand-alone communication, discrete, or is it only useful in a context that includes a series of events. 
Series applications are where log-based queueing shines
* Is the processing involve one simple action per event, or is there a group of related actions, a workflow, required to complete processing

When building a flow-ready application for messaging, the “trunk-limb-branch-leaf” pattern is a 
critical tool to consider: use edge computing to distribute decision-making close to the related 
groups of agents, computing local aggregates, and propagate to larger more central flows. Using messaging
middleware to manage interaction between agents, to isolate message distribution to just the needed
servers and agents, and propagate aggregates to the trunk, greatly reducing traffic between the original agents and the core.

Another consideration is to assess if the consumers need to filter events from a unique topic before
doing its own processing, in this case the event payload may include metadata and URL to get the payload.
If the metadata indicates an action is required, the consumer can then call the data retrieval URL.

Whether or not you include payload data depends a bit on the volume of events being published and the security and latency requirements of your application.

Log-based queues can play the role of “system of record” for both event values and sequence, especially for systems that need both the most recent event and an understanding of the recent history of events received

For single action processing, serverless, knative eventing are technologies to consider. Solution
needs to route events to the appropriate processor. But if your event processing needs require 
maintaining accurate state for the elements sending events then stateful streaming platform are better fit.

For workflow, modern solutions, simplify creating and managing process definitions independent of the actions taken in that process.
It supports for stepping an event through multiple interdependent actions. Workflow may require to
wait for another related event occurs or a human completes his action.

## 6- Today landscape


* **Standards** are important for flow:  TLS, WebSockets, and HTTP from IETF, MQTT and AMQP from OASIS, 
*CloudEvents* and the Serverless Working Group from CNCF
* **Open sources projects**: 

    * Apache Kafka and Apache Pulse for log-based queueing
    * Apache Beam, Flink, Heron, Nifi, Samza, and Storm for stream processing
    * Apache Druid as a “stream-native” database
    * gRPC may play a key role in any future flow interface
    * NATS.io, a cloud-native messaging platform
    * Argo, a Kubernetes-based workflow manager that theoretically could act as the core of an event-driven process automation bus


* Opportunities:

    * Data provenance and security for payloads passed between disparate parties
    * Tracking event data distribution across the world wide flow. Where does the data generated by an event end up being consumed or processed?
    * Platforms for coordinating event processing, aggregation, and synchronization between core data center event processors, edge computing environments, and end-user or IoT devices
    * Monetization mechanisms for all types of event and messaging streams

The adoption of a technology is not the delivery that makes it valuable, but the ecosystem that consumes it.

Look at existing streams and determine how to add value for the consumers of that stream. 
Can you automate valuable insights and analytics in real time for customers with shared needs? 
Would it be possible to recast the stream in another format for an industry that is currently 
using a different standard to consume that form of data? 