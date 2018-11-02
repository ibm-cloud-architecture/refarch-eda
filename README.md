# Event Driven Architecture Reference Architecture
Today IT architectures are hybrid cloud and event driven: most businesses are using cloud deployment as part of their IT strategy and millions of events are created in this context. Adopting an event driven architecture brings loosely coupled components and simplify microservices integration.

This repository represents the root on related content about Event Driven Architecture and groups guidances and reusable assets for EDA.

## Table of Contents
* [Target Audiences](#target-audiences)
* [Summary](#summary)
* [Related repositories](#related-repositories)
* [Contribute to the solution](#contribute)
* [Project Status](#project-status)

## Target audiences
You will be greatly interested by the subjects addressed in this solution if you are...
* an architect, you will get a deeper understanding on how all the components work together, and how to address.
* a developer, you will get a broader view of the solution end to end and get existing starting code, and practices you may want to reuse during your future implementation. We focus on event driven solution in hybrid cloud addressing patterns and non functional requirements as CI/CD, Test Driven Development, resiliency, ...
* a project manager, you may understand all the artifacts to develop in an EDA solution, and we may help in the future to do project estimation.

## Summary
The high level component view can be summarized in the diagram below:
<img src="docs/hl-arch.png" width="624px" usemap="#edamap">
<map name="edamap">
  <area shape="rect" coords="0,0,82,126" alt="Source" href="docs/evt-source.md">
  </map>


* Data sources reference events coming from IoT device, mobile app, webapp, database triggers or microservices. Click stream from webapp or mobile app are common events used for real time analytics.
* The Event Backbone is the center of the Event driven architecture, proving the event communication layer with event log. It propagates events, which may be a hierarchy of messaging hub. The pub/sub style is used
![]()
* Consumer will be functions, traditional application and microservices. Microservices are also producers. As microservice persists its own data in its own store we can leverage EDA to manage data consistency, see [section below](#service-mesh).

* The bottom part of the diagram addresses real time streaming analytics supported by complex event processing runtime and analytics operations.
* Decision insight is a stateful operator to manage business decision on enriched event linked to business context and business entities. This is the cornerstone to apply business logic and best action using time related business rules.
* There is also the opportunity to have data scientists connecting directly from a notebook to the topic and do real time data analysis, data cleansing and then train model on remote servers then deploy the model back to streaming analytics component...
* Business dashboards are also necessary and may be connected to event store or object store... pulling data on regular basis.

### Event Backbone
The Event Backbone propagates events. The pub/sub style is used. The event log represents the original source of truth and support the concept of event sourcing (see below) and event replay.

![](docs/evt-backbone.png)  

the Key features of an event Backbone:
*	Capability to store events for a period of time, allowing for potential downtime of the event consumers (ideally implemented with an event log)
* Immutable data : Consistent replay for evolving application instances
* Facilitate many consumers: Shared central “source of truth”
* Event log history is becoming a useful source for data scientists and machine learning model derivation

•	capability for once only event delivery
•	capability to handle subscriptions
•	capability to mediate and transform

Supporting products:
* [kafka](http://apache.kafka.org) and see also our [kafka article](https://github.com/ibm-cloud-architecture/refarch-analytics/tree/master/docs/kafka) on how to support HA and deployment to kubernetes.
* [IBM Event Streams](http://)

### Real-time analytics
The real-time analytics component supports
* Continuous event ingestion and analytics
* Correlation across events and streams
* Windowing and stateful compute
* Consistent/high-volume streams of data

The application pattern can be represented by the following diagram:
![](docs/rt-analytic-app-pattern.png)

* many sources of event are in the ingestion layer via connectors and event producer code.
* the data preparation involves data transformation, filtering, correlate, aggregate some metrics and data enrichment by leveraging other data sources.
* detect and predict events pattern using scoring, classification
* decide by applying business rules and business logic
* act by triggering process, business services, modifying business entities...


### Function as a service
As a event consumer functions deliver stateless discrete step or task for the global event processing. The serverless approach will bring cost efficiency for the just on-demand invocation. It fits well in post processing with the event processing.
Cloud functions provides a simple way for developers to write code which takes action on an event.
Serverless computing model, complete abstraction of infrastructure away from the developer
No need to worry about infrastructure/scaling  
Supports event notifications and event commands
Cost model reflects simple event processing, pay for event processing compute time only

* [Serverless - FaaS](docs/serverless/README.md)

### Decision Insights
[See this note too](docs/dsi/README.md)

IBM [Operational Decision Manager Product documentation](https://www.ibm.com/support/knowledgecenter/en/SSQP76_8.9.1/com.ibm.odm.itoa.overview/topics/con_what_is_i2a.html)

### Service mesh
We are starting to address service mesh in [this note](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/service-mesh/readme.md), and adopting messaging as a microservice communication backbone involves using at least the following patterns:
* microservice publish events when something happens in the scope of their control, for example an update in the business entities they are responsible of.
* microservice interested by other business entities, subscribe to those events and it can update its own states and business entities when receiving such events. Business entity keys needs to be unique, immutable.
* Business transactions are not ACID and span multiple services, they are more a series of steps, each step is supported by a microservice responsible to update its own entities. We talk about eventual consistency of the data.
* the message broker needs to guarantee that events are delivered at least once and the microservices are responsible to manage their offset from the stream source and deal with inconsistency, by detecting duplicate events.   
* at the microservice level, updating data and emitting event needs to be an atomic operation, to avoid inconsistency if the service crashes after the update to the datasource and before emitting the event. This can be done with an eventTable added to the microservice datasource and an event publisher that read this table on a regular basis and change the state of the event once published. Another solution is to have a database transaction log reader or miner responsible to publish event on new row added to the log.
* One other approach to avoid the two phase commit and inconsistency is to use an Event Store or event sourcing to keep trace of what is done on the business entity with enough data to rebuild the data. Events are becoming facts describing state changes done on the business entity.

### Command Query Responsibility Segregation

## Related repositories
* [Predictive maintenance - analytics and EDA](https://github.com/ibm-cloud-architecture/refarch-asset-analytics) how to mix Apache Kafka, stateful stream, Apache Cassandra and ICP for data to develop machine learning model deployed as a service.


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
Please [contact me](mailto:boyerje@us.ibm.com) for any questions.
