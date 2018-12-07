# Event Driven Cloud Native Apps

With cloud native platforms we has seen microservices become the application architecture of choice. As we look to become more event driven as a business and with our applications the event driven pattern needs to extend into our microservices application space.  This means that our microservices will need to be able to respond to and send out events, or in event driven terms they need to be both event producers and consumers.

## Event Backbone - Pub/Sub communication for microservices
With the adoption of microservices  there has been a lot of focus on synchronous communication between services. This has lead to Service Mesh packages such as [Istio](https://istio.io/) which help with the management of communication, service discovery, load balancing and visibility in this synchronous communication environment

With event driven microservices our communication point becomes the Pub/Sub layer of the event backbone.

![](evt-micro.png)

##  Event Driven Apps  with Cloud Functions

IBM Cloud functions provides a *server-less* compute model and a simplified *event driven programming model* which enabled developers to easily develop event driven microservices.

With the cloud functions programming model

* Developers write  the logic for the event driven microservices
* The micro-service is defined as an action which is executed in response to an event arriving on a Kafka topic ( the event backbone)
* Functions brings up the required compute to run the micro-service
* Functions shuts down the compute when the micro-service  completes
* Functions automatically scales for event volume/velocity with the complexities of managing active consumer service instances abstracted into the server-less compute stack.

## Event Driven apps with containers

While the server-less approach with Cloud functions provides a simplified event based programming model, the majority of microservices applications today are developed for and deployed to a container based cloud native stack.

Within the container environments for event driven microservices we again look to the event backbone ( Kafka ) to be the Pub/Sub communication provider to connect microservices.

In this context Microservices are developed as direct consumers and producers  of events on the backbone ( Kafka topics)

The challenge with event driven microservices in this environment is in managing consumer instances to the demand of the event stream. How many consumer micro-service instances need to be running to keep pace with or always be immediately available to execute the micro-service in response to an event arriving.

## Event Driven Microservices patterns
Adopting messaging ( Pub/Sub ) as  a micro-service communication backbone involves using at least the following patterns:
* microservices publish events when something happens in the scope of their control. For example, an update in the business entities they are responsible for.
* A micro-service interested in other business entities, subscribe to those events and it can update its own states and business entities when receiving such events. Business entity keys needs to be unique, immutable.
* Business transactions are not ACID and span multiple services, they are more a series of steps, each step is supported by a micro-service responsible to update its own entities. We talk about eventual consistency of the data.
* the message broker needs to guarantee that events are delivered at least once and the microservices are responsible to manage their offset from the stream source and deal with inconsistency, by detecting duplicate events.
* at the microservice level, updating data and emitting event needs to be an atomic operation, to avoid inconsistency if the service crashes after the update to the datasource and before emitting the event. This can be done with an eventTable added to the microservice datasource and an event publisher that read this table on a regular basis and change the state of the event once published. Another solution is to have a database transaction log reader or miner responsible to publish event on new row added to the log.
* One other approach to avoid the two phase commit and inconsistency is to use an Event Store or event sourcing to keep trace of what is done on the business entity with enough data to rebuild the data. Events are becoming facts describing state changes done on the business entity.

We are starting to address this with the service mesh in [this note](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/service-mesh/readme.md),

## Code Reference
T
