# Event Driven Architecture Reference Architecture

The modern digital business works in real time, it informs interested parties of things of interest when they happen, it makes sense of and derives insight from an ever-growing number of event sources, it learns,  it predicts, it's intelligent, it is by nature Event Driven.

For enterprise IT teams, this means embracing event driven as being foundational to the next gerenration of digital business applications. It will require IT teams to be able to design, develop, deploy and operate event driven solutions, in cloud native styles.

While event driven architectures and reactive programming models are not new concepts the move to Cloud Native architectures with Microservices, Container based workloads and "serverless" computing allow us to revist event driven in this Cloud Native context.  Indeed we could think of event driven as extending the Resillience, Agility and Scale characteristics of "Cloud Native" to also be Reactive and Reponsive:

* Microservices -  Provide the loosely coupled application architecture which enables deployment in highly distributed patters for Resilience, Agility and Scale.
* Cloud Native platforms with Containers and "Serverless deployments"  - Provide the application platform and tools which realise the Resilience Agility and Scale promise of the microservices architectures.
* Event services -  Realising an Event Driven Architetcure (EDA) provide the means to be reactive and responsive

Thinking in this way allows us to simplifiy the concept of the Event driven Architecture to be about providing three essential cpabilities to the Cloud Native Platform.

* Communication and persistance events.
* Taking action on events.
* Processing continuous event streams to derive real time insights and intelligence.
* Supporting event driven microservices

This repository represents the root of related content about the cloud native Event Driven Architecture, it provides guidance  for how to approach the design of event driven solutions,  introduces the Cliud Native Event Driven reference architecture  and provides reusable coding assets for implimentation in a cloud native environment.

## Table of Contents
* [Target Audiences](#target-audiences)
* [Reference Architecture](#architecture)
* [Concepts](#concepts)
* [Event Storming Methodology](docs/methodology/readme.md)
* [Related repositories](#related-repositories)
* [Contribute to the solution](#contribute)
* [Project Status](#project-status)

## Target audiences

* As an architect, you will understand how the event driven architecture provides capapbabilites which support development of event driven solutions.
* As a developer, you will understand how to develop event driven applications.
* As a project manager, you may understand all the artifacts which may be required for an event driven solution.

From the repository you will get starting code, and best practices  which you may want to reuse during your future implementations. The reference architecture has been designed to be portable, and applicable to Public Cloud, Hybrid cloud and across multiple clouds. Examples given are directly deployable in IBM Publc Cloud and with IBM Cloud Private.

While the content of this repository is mostly technical in nature , it does introduce methods such as Event Storming which would be used with business leaders to identify key bsuiness domain events and actions, you may find it useful to share this information with your business leaders before  engaging them in such activities.

## Architecture

We defined the starting point for a modern Cloud Native Event Driven Architecture to be that it supports at least the following important capabilities:

* Being able to communicate and persist events
* Being able to take direct action on events.
* Processing event streams to derive real time insight/intelligence
* Providing communication for event driven microservices

We could visualize this architecture at the capability level as shown below:

<img src="docs/hl-arch-refa.png" width="1024px">

IBM Event Streams : provides a Kafka Event Backbone with
Pub/Sub communication,  event log, event stream processing

IBM Cloud Functions : Provides a simplified programming model to take action on an event  with serverless  compute

Streaming Analytics : Provides continuous ingest and analytical processing across multiple event streams

Decision Server Insights: Provides the means to take action on events and event streams through business rules

Event Driven Microservices applications running as serverless functions or containerized workloads are connected via pub/sub event communication through the event backbone.

Connectec Event Stores provide additional persistance options for , optimised event sourcing and analytical use cases. 

In the following sections we will expand this component view and look at each area in detail. So lets start from  this expanded view of the architecture and explore each of the compenent parts in detail.

<img src="docs/hl-arch-num.png" width="1024px">

1- **Event sources**

The modern digital business is driven by events,  events come into the business and events need to be pushed outside of the buisness.  For our Cloud Native EDA we consider event sources to be all of those things which may generate events which are of interest to the business. This could include, events coming from, IoT devices, mobile apps, web apps, database triggers or microservices.

In general EDA terms, an Event Source or  event producer is any component capable of creating an event notification and publishing it to the event backbone.

[Read more ...](docs/evt-src/README.md)

2- **The Event Backbone** is the center of the Event driven architecture providing the event communication and persistence layer with the following capabilities.
 * Pub/Sub style event communication between event producers and consumers
 * Persist events for a period of time
 * Enables replay of events
 * Deliverds events once only
 * Handle subscriptions from multiple consumers

 [Read more ...](docs/evt-backbone/README.md)


2- **Event consumers** are any components capable of receiving and reacting to event notifications. Event consumers carry out activities as diverse as detecting  business threats and opportunities, performing actions, or monitoring event flows. Like event producers, software modules that are event consumers should aim to be cohesive and loosely coupled.
In modern architecture consumers are functions as a service, traditional applications (in the enterprise network) and microservices. Microservices are also producers. As microservice persists its own data in its own store, and architects may leverage EDA to manage data consistency between services. We are addressing this pattern in [the service-mesh section below](#service-mesh).

[Read more ...](docs/evt-consumer/README.md)

3- **CEP consumers / Real time analytics**: Some types of event consumers are specials in the sense that they are designed specifically for event processing. Sometimes referred to as event processing engines, such components may be capable of simple event processing, complex event processing (CEP) or event stream processing (ESP). Specializations in the way commercial and open source products implement these capabilities constitute the basis for our discussion concerning the non-functional aspects of event-driven architecture.  As part of this event streaming processing there are a set of application that can support real time analytics and even applying machine learning model on real time data.

[Read more ...](docs/rt-analytics/README.md)





5- **Dashboard**: Event based solution needs to present different type of user interface:  operational dashboards to assess the state of the runtime components and business oriented dashboard, also known as Business Activity Monitoring.
There is a need to keep visibility of event paths inside the architecture. Dashboards will be connected to the event backbone and to event store.

[Read more ...](docs/evt-dashboard/README.md)


6- **Data scientist workbench**:
There are opportunities to have data scientists connecting directly event subscriber from their notebook to do real time data analysis, data cleansing and even train and test model on the data from the event payload. The data can be kept in data store but the new model can be deployed back to the streaming analytics component...

[Read more ...](docs/ml-workbench/README.md)

---

## Concepts

### Loosely coupling
Loose coupling is one of the main benefits of event-driven processing. It allows event producers to emit events without any knowledge about who is going to consume those events. Likewise, event consumers do not have to be aware of the event emitters. Because of this, event consuming modules and event producer modules can be implemented in different languages or use technologies that are different and appropriate for specific jobs. Loosely coupled modules are better suited to evolve independently and, when implemented right, result in a significant decrease in system complexity.

Loose coupling, however, does not mean “no coupling”. An event consumer consumes events that are useful in achieving its goals and in doing so establishes what data it needs and the type and format of that data. The event producer emits events that it hopes will be understood and useful to consumers thus establishing an implicit contract with potential consumers. For example, an event notification in XML format must conform to a certain schema that must be known by both the consumer and the producer.  One of the most important things that you can do to reduce coupling in an event-driven system is to reduce the number of distinct event types that flow between modules. To do this you have pay attention to the cohesiveness of those modules.

### Cohesion
Cohesion is the degree to which related things are encapsulated together in the same software module. At this point, for the purposes of our EDA discussion, we define module as an independently deployable software unit that has high cohesion.  Cohesion is strongly related to coupling in the sense that a highly cohesive module communicates less with other modules, thus reducing the number of events, but most importantly, the number of event types in the system. The less modules interact with each other, the less coupled they are.
Achieving cohesion in software while at the same time optimizing module size for flexibility and adaptability is hard but it is something that should be aimed for. Designing for cohesion starts with a holistic understanding of the problem domain and good analysis work. Sometimes it must also take into account the constraints of the supporting software environment. Monolithic implementations should be avoided, as should implementations that are excessively fine-grained.


### Function as a service
As a event consumer functions deliver stateless discrete step or task for the global event processing. The serverless approach will bring cost efficiency for the just on-demand invocation. It fits well in post processing with the event processing.
Cloud functions provides a simple way for developers to write code which takes action on an event.
Serverless computing model, complete abstraction of infrastructure away from the developer
No need to worry about infrastructure/scaling
Supports event notifications and event commands
Cost model reflects simple event processing, pay for event processing compute time only

* [Serverless - FaaS](docs/serverless/README.md)


### Service mesh
We are starting to address service mesh in [this note](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/service-mesh/readme.md), and adopting messaging as a microservice communication backbone involves using at least the following patterns:
* microservice publish events when something happens in the scope of their control, for example an update in the business entities they are responsible of.
* microservice interested by other business entities, subscribe to those events and it can update its own states and business entities when receiving such events. Business entity keys needs to be unique, immutable.
* Business transactions are not ACID and span multiple services, they are more a series of steps, each step is supported by a microservice responsible to update its own entities. We talk about eventual consistency of the data.
* the message broker needs to guarantee that events are delivered at least once and the microservices are responsible to manage their offset from the stream source and deal with inconsistency, by detecting duplicate events.
* at the microservice level, updating data and emitting event needs to be an atomic operation, to avoid inconsistency if the service crashes after the update to the datasource and before emitting the event. This can be done with an eventTable added to the microservice datasource and an event publisher that read this table on a regular basis and change the state of the event once published. Another solution is to have a database transaction log reader or miner responsible to publish event on new row added to the log.
* One other approach to avoid the two phase commit and inconsistency is to use an Event Store or event sourcing to keep trace of what is done on the business entity with enough data to rebuild the data. Events are becoming facts describing state changes done on the business entity.

### Command Query Responsibility Segregation

When using a microservices architecture pattern, each service is responsible to manage its persistence for the business entities it manages. Therefore it is challenging to perform join query on multiple business entities across microservice boundaries.
Basically Command Query Responsibility Segregation, CQRS, is a pattern where the CUD operations (the commands) are done in one service while query / read operations are supported by a separate service. The command-side emits events when data changes. The Query side maintains a set of views that are kept up to date by subscribing to events.

One of the main advantages is to support multiple data denormalization and being able to scale easily. It is complex to implement, aim for code duplication and should not be considered as the silver bullet.

### Event Sourcing

Services publish events whenever the data they control change. The event publish needs to be unique (atomic) and the source reliable (no event duplication). Event sourcing persists the state of a business entity as a sequence of state changing events. The event store is used for persistence. The service is not persisting data in a relational database anymore.
To avoid keeping a huge amount of change log, snapshot can be perform to keep a view of the data at a given point of time. Changes will then apply from a snapshot.
Queries have to reconstruct the state of the business entity from a snapshot.

## Applicability of an EDA

EDAs are typically not used for distributed transactional processing because this can lead to increased coupling and performance degradation. But as seen in previous section, using message backbone to support communication between microservices to ensure data consistency is a viable pattern. The use of EDAs for batch processing is also restricted to cases where the potential for parallelizing batch workloads exist.  Most often EDAs are used for event driven applications that require near-realtime situation awareness and decision making.

## Related repositories

* [Container shipment solution](https://github.com/ibm-cloud-architecture/refarch-kc): this solution presents real time analytics, pub-sub architecture pattern and microservice communication on Kafka.
* [Predictive maintenance - analytics and EDA](https://github.com/ibm-cloud-architecture/refarch-asset-analytics) how to mix Apache Kafka, stateful stream, Apache Cassandra and ICP for data to develop machine learning model deployed as a service.

## Compendium

* [Getting started with IBM Streams Analytics on IBM Cloud](https://console.bluemix.net/docs/services/StreamingAnalytics/t_starter_app_deploy.html#starterapps_deploy)
* [IBM Streams Analytics Samples](https://ibmstreams.github.io/samples/)
* [Kafka summary and deployment on IBM Cloud Private](./docs/kafka/readme.md)
* [Service mesh](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/service-mesh/readme.md)
* [Serverless](https://github.com/ibm-cloud-architecture/refarch-integration/tree/master/docs/serverless)
* [API for declaring messaging handlers using Reactive Streams](https://github.com/eclipse/microprofile-reactive-messaging/blob/master/spec/src/main/asciidoc/architecture.asciidoc)
* [Microservice patterns - Chris Richardson](https://www.manning.com/books/microservices-patterns)

## Contribute

We welcome your contribution. There are multiple ways to contribute: report bugs and improvement suggestion, improve documentation and contribute code.
We really value contributions and to maximize the impact of code contributions we request that any contributions follow these guidelines
* Please ensure you follow the coding standard and code formatting used throughout the existing code base
* All new features must be accompanied by associated tests
* Make sure all tests pass locally before submitting a pull request
* New pull requests should be created against the integration branch of the repository. This ensures new code is included in full stack integration tests before being merged into the master branch.
* One feature / bug fix / documentation update per pull request
* Include tests with every feature enhancement, improve tests with every bug fix
* One commit per pull request (squash your commits)
* Always pull the latest changes from upstream and rebase before creating pull request.

If you want to contribute, start by using git fork on this repository and then clone your own repository to your local workstation for development purpose. Add the up-stream repository to keep synchronized with the master.

## Project Status
[10/2018] Just started

## Contributors
* Lead development [Jerome Boyer](https://www.linkedin.com/in/jeromeboyer/)
* [Andy Gibb](https://www.linkedin.com/in/andy-g-3b7a06113/)
* [IBM Streams Analytics team]
* [IBM Event Stream team]
* [IBM Decision Insight team]

Please [contact me](mailto:boyerje@us.ibm.com) for any questions.
