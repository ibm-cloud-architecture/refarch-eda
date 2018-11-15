# Event Driven Architecture Reference Architecture
Today IT architectures are hybrid cloud and event driven: most businesses are using cloud deployment as part of their IT strategy and millions of events are created in this context. This is given that microservices is the development approach and application design used in 3nd generation of business application. They use loosely coupling pattern, highly distributed for horizontal scaling, containerized for simple deployment and address the need to be very agile in term of development to production speed and even pivoting to new business capability. Development teams are cross discipline and autonomous focusing on short term business outcomes to support strategic initiatives like digitalization.

In this context applications have to support the [reactive manifesto](https://www.reactivemanifesto.org/). One of the characteristics being message and event driven: "Reactive Systems rely on asynchronous message-passing to establish a boundary between components that ensures loose coupling, isolation and location transparency."

Event-Driven Architecture (EDA) is the software architecture to enable the sending of events from event producers to event consumers in a decoupled manner to support modern reactive intelligent applications. The term intelligent is for the fact that we can add machine learning model to do real time analytics on the event stream. There is also opportunity to connect a Data Scientist workbench to those event backbone.

An **event**, more precisely an event notification, is a timestamped, immutable message that is delivered from event producers to event consumers via an asynchronous mechanism, sometimes called an event channel. EDA is only decoupled in the sense that event producers are not aware of event consumers and vice versa but this is sufficient to make event-driven systems more flexible, scalable and adaptable.

Adopting an event driven architecture brings [loosely coupled](#loosely-coupled) components and simplify microservices integration. EDAs are conceptually simple, but the large scale implementation of an EDA is anything but simple.

This repository represents the root of related content about Event Driven Architecture and groups guidances and reusable coding assets for EDA.

## Table of Contents
* [Target Audiences](#target-audiences)
* [Reference Architecture](#architecture)
* [Concepts](#concepts)
* [Event Storming Methodology](docs/methodology/readme.md)
* [Related repositories](#related-repositories)
* [Contribute to the solution](#contribute)
* [Project Status](#project-status)

## Target audiences
You will be greatly interested by the subjects addressed in this solution if you are...
* an architect, you will get a deeper understanding on how all the components work together, and how to address.
* a developer, you will get a broader view of the solution end to end and get existing starting code, and practices you may want to reuse during your future implementation. We focus on event driven solution in hybrid cloud addressing patterns and non functional requirements as CI/CD, Test Driven Development, resiliency, ...
* a project manager, you may understand all the artifacts to develop in an EDA solution, and we may help in the future to do project estimation.

## Architecture
Modern Event Driven Architecture supports at least the following important capabilities:
* Being able to communicate and persist events
* Being able to take direct action on events.
* Processing event streams to derive real time insight/intelligence

The diagram below summarize a product agnostic platform with the components to support the above capabilities:

<img src="docs/hl-arch.png" width="1024px">

To document the components involved in this architecture we are adding numbers with detailed descriptions:

<img src="docs/hl-arch-num.png" width="1024px">

1- **Data and event sources** reference events coming from IoT devices, mobile apps, web apps, database triggers or microservices. Click stream from web apps or mobile apps are common events used for real time analytics. An event producer is any component capable of creating an event notification and publishing it to an event channel or backbone.   

[Read more ...](docs/evt-src/README.md)

2- **Event consumers** are any components capable of receiving and reacting to event notifications. Event consumers carry out activities as diverse as detecting  business threats and opportunities, performing actions, or monitoring event flows. Like event producers, software modules that are event consumers should aim to be cohesive and loosely coupled.
In modern architecture consumers are functions as a service, traditional applications (in the enterprise network) and microservices. Microservices are also producers. As microservice persists its own data in its own store, and architects may leverage EDA to manage data consistency between services. We are addressing this pattern in [the service-mesh section below](#service-mesh).  

[Read more ...](docs/evt-consumer/README.md)

3- **CEP consumers / Real time analytics**: Some types of event consumers are specials in the sense that they are designed specifically for event processing. Sometimes referred to as event processing engines, such components may be capable of simple event processing, complex event processing (CEP) or event stream processing (ESP). Specializations in the way commercial and open source products implement these capabilities constitute the basis for our discussion concerning the non-functional aspects of event-driven architecture.  As part of this event streaming processing there are a set of application that can support real time analytics and even applying machine learning model on real time data.

[Read more ...](docs/rt-analytics/README.md)

4- **The Event Backbone** is the center of the Event driven architecture, proving the event communication layer with event log. It enables the delivery of events from event producers to consumers using a pub/sub style. It supports at least the following capabilities:
 * store events for a period of time, allowing for potential downtime of the event consumers (ideally implemented with an event log)
 * deliver event once only
 * handle subscriptions from multiple consumers
 * mediate, filter, aggregate and transform Events

[Read more ...](docs/evt-backbone/README.md)

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
When using a microservices architecture pattern, each service is responsible to manage its persistence for the business entities it manages. Therefore it is challenging to perform join query on multiple business entities.
Basically Command Query Responsibility Segregation, CQRS, is a pattern where the CUD operations (the commands) are done in one service while query / read operations are supported by a separate service. The command-side emits events when data changes. The Query side maintains a set of views that are kept up to date by subscribing to events.

One of the main advantages is to support multiple data denormalization and being able to scale easily. It is complex to implement, aim for code duplication and should not be considered as the silver bullet. 

## Applicability of an EDA
EDAs are typically not used for distributed transactional processing because this can lead to increased coupling and performance degradation. But as seen in previous section, using message backbone to support communication between microservices to ensure data consistency is a viable pattern. The use of EDAs for batch processing is also restricted to cases where the potential for parallelizing batch workloads exist.  Most often EDAs are used for event driven applications that require near-realtime situation awareness and decision making.  


## Related repositories
* [Predictive maintenance - analytics and EDA](https://github.com/ibm-cloud-architecture/refarch-asset-analytics) how to mix Apache Kafka, stateful stream, Apache Cassandra and ICP for data to develop machine learning model deployed as a service.
* [Container shipment solution](https://github.com/ibm-cloud-architecture/refarch-kc): this solution presents real time analytics, pub-sub architecture pattern and microservice communication on Kafka.


## Compendium
* [Getting started with IBM Streams Analytics on IBM Cloud](https://console.bluemix.net/docs/services/StreamingAnalytics/t_starter_app_deploy.html#starterapps_deploy)
* [IBM Streams Analytics Samples](https://ibmstreams.github.io/samples/)
* [Kafka summary and deployment on IBM Cloud Private](https://github.com/ibm-cloud-architecture/refarch-analytics/tree/master/docs/kafka)
* [Service mesh](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/service-mesh/readme.md)
* [Serverless](https://github.com/ibm-cloud-architecture/refarch-integration/tree/master/docs/serverless)
* [API for declaring messaging handlers using Reactive Streams](https://github.com/eclipse/microprofile-reactive-messaging/blob/master/spec/src/main/asciidoc/architecture.asciidoc)

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
