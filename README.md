# Event Driven Reference Architecture

The modern digital business works in real time; it informs interested parties of things of interest when they happen, it makes sense of, and derives insight from an ever-growing number of sources. It learns, predicts and is intelligent -- it is by nature Event Driven.

Events are a way of capturing a statement of fact.  Events occur in a continuous stream as things happen in the real and digital worlds.  By taking advntage of this continous stream, applications can not only react in real time, but also reason about the future based upon what has happened in the past.

For enterprise IT teams, embracing event driven development is foundational to the next generation of digital business applications. IT  teams will need to be able to design, develop, deploy and operate event driven solutions, in cloud native styles.

While event driven architectures and reactive programming models are not new concepts, the move to Cloud Native architectures with Microservices, Container based workloads and "server-less" computing allow us to revisit event driven approaches in this Cloud Native context.  Indeed we could think of event driven as extending the Resilience, Agility and Scale characteristics of "Cloud Native" to also be Reactive and Responsive. Two aspects of a cloud-native architecture are essential to developing an event driven architecture:

* Microservices -  These provide the loosely coupled application architecture which enables deployment in highly distributed patterns for Resilience, Agility and Scale.
* Cloud Native platforms with Containers and "Serverless deployments" - These provide the application platform and tools which realize the Resilience, Agility and Scale promise of the microservices architectures.

An Event Driven Architecture should provide the following essential event capabilities to the Cloud Native Platform.

* Being able to communicate and persist events.
* Being able to take direct action on events.
* Processing event streams to derive real time insight/intelligence.
* Providing communication for event driven microservices.

This repository represents the root of related content about the Cloud Native Event Driven Architecture.  It provides guidance for how to approach the design of event driven solutions, introduces the Cloud Native Event Driven reference architecture and provides reusable coding assets for implementation in a cloud native environment.

## Table of Contents

* [Target audiences](#target-audiences)
* [Concepts](#concepts)
* [Reference architecture](#reference-architecture)
* [Extended architecture for machine learning and legacy integration](#extended-architecture)
* [Event Storming methodology](#event-storming)
* [Deployments](#deployments)
* [Sample EDA Applications](#sample-eda-applications)
* [Contribute to the solution](#contribute)
* [Project Status](#project-status)

## Target audiences

While the content of this repository is mostly technical in nature and is intended for a technical audience, it also introduces methods such as [Event Storming](#event-storming) which would be used with business leaders to identify key business domain events and actions. You may find it useful to share this information with your business leaders before engaging them in such activities.

At a high level this is what you should expect to learn by working through this repository and the related examples.

* As an architect, you will understand how the event driven architecture provides capabilities which support development of event driven solutions.
* As a developer, you will understand how to develop event driven applications and develop analytics based on event streams.
* As a project manager, you may understand all the artifacts which may be required for an event driven solution.

The related repositories provide sample code and best practices  which you may want to reuse during your future implementations. The reference architecture has been designed to be portable and applicable to Public Cloud, Hybrid cloud and across multiple clouds. Examples given are directly deployable in IBM Public Cloud and with IBM Cloud Private.

## Concepts

Before we start looking at the details of the Event Driven Architecture we will quickly examine the core concepts of being event driven:

* Events
* Event streams
* Commands
* Loose Coupling
* Cohesion

[Read more ...](docs/concepts/README.md)

## Reference Architecture

We defined the starting point for a Cloud Native Event Driven Architecture to be that it supports at least the following important capabilities:

* Being able to communicate and persist events.
* Being able to take direct action on events.
* Processing streams of events to derive real time insight/intelligence.
* Providing communication between event driven microservices and functions.

With an event backbone providing the connectivity between the capabilities, we can visualize a reference Event Driven Architecture as below:

<img src="docs/hl-arch-refa.png" width="1024px">

Where:

* IBM Event Streams: Provides a Event Backbone supporting Pub/Sub communication, an event log, and simple event stream processing based on [Apache Kafka](https://kafka.apache.org/).
* IBM Cloud Functions: Provides a simplified programming model to take action on an event through a "serverless" function-based compute model.
* Streaming Analytics: Provides continuous ingest and analytical processing across multiple event streams.
* Decision Server Insights: Provides the means to take action on events and event streams through business rules.
* Event Driven Microservices: Applications that run as server-less functions or containerized workloads which are connected via pub/sub event communication through the event backbone.
* Event Stores: Provide optimized persistence (data stores), for event sourcing, Command Query Response Separation (CQRS) and analytical use cases.

Now we will take a detailed look at each of these component areas which make up the reference architecture:

1- **Event sources:** The modern digital business is driven by events. Events come into the business and events likewise need to be pushed outside of the business.  For our Cloud Native Event Driven Architecture we consider event sources to be all of those things which may generate events which are of interest to the business. This could include, events coming from IoT devices, mobile apps, web apps, database triggers or microservices.

In general terms, an *Event Source*, or event producer is any component capable of creating an event notification and publishing it to the event backbone, but let look at some specific types of producer to better understand the opportunity with event driven.

[Read more ...](docs/evt-src/README.md)

2- **The Event Backbone** is the center of the Event Driven Architecture providing the event communication and persistence layer with the following capabilities:

 * Pub/Sub style event communication between event producers and consumers
 * An Event Log to persist events for a given period of time
 * Replay of events
 * Subscriptions from multiple consumers

 [Read more ...](docs/evt-backbone/README.md)

3- **Taking an Action** after an event has occurred is one of the fundamental operations for any event driven solution.  **IBM Cloud Functions** provides a simplified event driven programming model, enabling developers to simply write the *action* code in the language of their choice and have Cloud Functions manage the computation workload.

With this simplified model:

 * A business event of interest would be published to the event backbone.
 * The *action* for the event would be written as a Cloud Functions action.
 * Cloud Functions would be configured to subscribe to the event and use it as a trigger to start the *action*.
 * Cloud functions manages the start-up of all required compute resources.
 * Cloud functions managed execution of the action code.
 * Cloud functions manages the shut-down of the computation resources when the action is complete.

 [Read more ...](docs/evt-action/README.md)

4- **Processing continuous streaming events to derive real time insights/intelligence** is an essential element of modern event driven solutions. Specialized *streaming analytics engines* provide the means to run stateful analytical and complex event processing workloads across multiple real time event streams while maintaining low latency processing times.

Including these engines as part of the Event Driven Architecture enables:

* Analysis and understanding of real time event streams
* Extracting real time event data from the stream so that Data Scientists can understand and derive Machine Learning models
* Running analytical processes, Machine Learning models in line in real time against the event stream.
* Matching of complex event patterns across multiple streams and time windows to make decisions and take actions

[Read more ...](docs/rt-analytics/README.md)

5- **Event Managed State:** While the prime focus for an event driven architecture is for processing events, there are cases where we need to persist events for post processing and queries by other applications.  With the event backbone we have a builtin *Event Log* which provides the means to store and reply events published to the backbone, however when we consider the full scope of Event Driven solutions there are other use cases and types of store that we should support. This includes:

 * Event Stores optimized for analytics
 * Event Sourcing as a pattern for recording state changes and updates across distributed systems
 * Command Query Response Separation (CQRS) as an optimization which separates updates and reads across different stores

[Read more ...](docs/evt-state/README.md)

6- **Event Driven Cloud Native Apps (Microservices):** The event driven architecture must also reach across into our application platform. Developers will build applications which interact with events and are themselves event driven, that is they will both produce and consume events via the event backbone.

In this context we can view the Event Backbone as being part of the microservices mesh, providing the Pub/Sub communication between microservices, and therefore enabling the support of loosely coupled event driven microservices.

[Read more ...](docs/evt-microservices/README.md)


## Extended Architecture

The event-driven reference architecture provides the framework to support event-driven applications and solutions. The extended architecture provides the connections for:

  * Integration with legacy apps and data resources
  * Integration with analytics or machine learning to derive real-time insights

The diagram below shows how these capabilities fit together to form an extended event-driven architecture.

<img src="docs/hl-arch-extended.png" width="1024px">

[Read more ...](docs/extended-arch/readme.md)

## Event Storming

When it comes to the design of event driven solutions there are some additional methods which can be utilized to help understand the business events and actions that make up a business.

**Event Storming**, is a workshop format for quickly exploring complex business domains by focusing on *domain events* generated in the context of a business process or a business application. It focuses on communication between product owner, domain experts and developers.

**Insights Storming**, is an extension to the event storming workshop and encourages a forward-looking approach to consider the insights, (predictive models) which would make a difference to the business when we look at actions for key business events. What if instead of seeing a *system has failed event* (events are something that has happened) we could see a predictive or derived event, the *system will fail in 3 days*, we could take preventative actions.

[Read more about the Event Storming Methodology](docs/methodology/readme.md)

## Applicability of an EDA

EDAs are typically not used for distributed transactional processing because this can lead to increased coupling and performance degradation. But as seen in previous section, using a message backbone to support communication between microservices to ensure data consistency is a viable pattern. The use of EDAs for batch processing is also restricted to cases where the potential for parallelizing batch workloads exist.  Most often EDAs are used for event driven applications that require near-realtime situation awareness and decision making.

[Read more about EDA applicability and use cases ](docs/eda-usecases/README.md)


## Deployments

In term of event backbone deployment we propose different approaches:
* **[IBM Cloud](https://cloud.ibm.com/)** with the [Event Streams service](https://cloud.ibm.com/catalog/services/event-streams).
  * Deployment discussions for the KC solution are in [this note](https://github.com/ibm-cloud-architecture/refarch-kc/blob/master/docs/prepare-ibm-cloud.md)
* **IBM Cloud Private**
  * [Event Streams deployment](./deployments/eventstreams/README.md).
  * [Zookeeper deployment](./deployments/zookeeper/README.md) and [Kafka deployment](./deployments/kafka/README.md) for ICP.
* Running on your developer workstation within kubernetes (Tested on Docker-Edge on Mac)
  * [Zookeeper deployment](./deployments/zookeeper/README.md) and [Kafka deployment](./deployments/kafka/README.md).
* Running locally with docker compose. See [this note](https://github.com/ibm-cloud-architecture/refarch-kc#run-locally) for details.

## Sample EDA Applications

* [Container shipment solution](https://github.com/ibm-cloud-architecture/refarch-kc): this solution presents real time analytics, pub-sub architecture pattern and micro-service communication on Kafka.
* [Predictive maintenance - analytics and EDA](https://github.com/ibm-cloud-architecture/refarch-asset-analytics) how to mix Apache Kafka, stateful stream, Apache Cassandra and ICP for data to develop machine learning model deployed as a service.

## Compendium

* [Getting started with IBM Streaming Analytics on IBM Cloud](https://console.bluemix.net/docs/services/StreamingAnalytics/t_starter_app_deploy.html#starterapps_deploy)
* [IBM Streams Samples](https://ibmstreams.github.io/samples/)
* [Kafka summary and deployment on IBM Cloud Private](./docs/kafka/readme.md)
* [Service mesh](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/service-mesh/readme.md)
* [Serverless](https://github.com/ibm-cloud-architecture/refarch-integration/tree/master/docs/serverless)
* [API for declaring messaging handlers using Reactive Streams](https://github.com/eclipse/microprofile-reactive-messaging/blob/master/spec/src/main/asciidoc/architecture.asciidoc)
* [Microservice patterns - Chris Richardson](https://www.manning.com/books/microservices-patterns)

## Contribute

We welcome your contributions. There are multiple ways to contribute: report bugs and improvement suggestion, improve documentation and contribute code.
We really value contributions and to maximize the impact of code contributions we request that any contributions follow these guidelines:

The [contributing guidelines are in this note.](./CONTRIBUTING.md)

## Project Status
[10/2018] Just started

## Contributors

* Lead development [Jerome Boyer](https://www.linkedin.com/in/jeromeboyer/)
* Lead offerings [Andy Gibbs](https://www.linkedin.com/in/andy-g-3b7a06113/)
* [IBM Streams Analytics team]
  * [Martin Siegenthaler](https://www.linkedin.com/in/martin-siegenthaler-7654184/)
  * [David Engebretsen](https://www.linkedin.com/in/david-engebretsen/)
  * [Francis Parr](https://www.linkedin.com/in/francis-parr-26041924)
* [IBM Event Stream team]
* [IBM Decision Insight team]
  * [Jose De Freitas](https://www.linkedin.com/in/jose-de-freitas-755a501b/)
* [Hemankita Perabathini](https://www.linkedin.com/in/hemankita-perabathini/)

Please [contact me](mailto:boyerje@us.ibm.com) for any questions.
