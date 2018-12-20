# Event-driven cloud native apps

On cloud-native platforms, microservices are the application architecture of choice. As businesses become event-driven and with our applications the event driven pattern needs to extend into our microservices application space. This means that your microservices must respond to and send out events, or in event-driven terms they need to be both event producers and consumers.

## Event backbone - Pub/Sub communication and data sharing for microservices

![](evt-micro.png)

With the adoption of microservices, the focus on synchronous communication between services has increased. Service mesh packages such as [Istio](https://istio.io/) which help with the management of communication, service discovery, load balancing, and visibility in this synchronous communication environment.

With event-driven microservices, the communication point becomes the Pub/Sub layer of the event backbone. By adopting an event-based approach for intercommunication between microservices, the microservices applications are naturally responsive (event-driven). This approach enhances the loose coupling nature of microservices because it decouples producers and consumers.  Further, it enables the sharing of data across microservices through the event log.
These *event* style characteristics are increasingly important considerations when you develop microservices style applications. In practical terms microservices applications are a combination of synchronous API-driven, and asynchronous event-driven communication styles.

### Supporting Products and suggested reading

* IBM Cloud Functions/Openwhisk programming model  https://openwhisk.apache.org/documentation.html#programming-model
* Using Cloud functions with event trigger in Kafka  https://github.com/IBM/ibm-cloud-functions-message-hub-trigger
* IBM Cloud Functions product offering https://www.ibm.com/cloud/functions
* Getting Started with Cloud Functions  https://console.bluemix.net/openwhisk/

## Event Driven apps with containers

While the serverless approach with Cloud Functions provides a simplified event-based programming model, the majority of microservices applications today are developed for and deployed to a container-based cloud-native stack.  Within the cloud-native landscape, Kubernetes is the standard platform for container orchestration, and therefore becomes the base for the container platform in the event-driven architecture.

As before, the event backbone is be the Pub/Sub communication provider and event log for shared data for the microservices. In this context microservices are developed as direct consumers and producers of events on the backbone via topics.  The extra work in this environment is in managing consumer instances to respond to the demand of the event stream. You must determine how many consumer microservice instances need to be running to keep pace with, or always be immediately available to execute, the microservice in response to an arriving event.

### Supporting products and suggested reading

* IBM Cloud Private - Kuberenets base container platform  https://www.ibm.com/cloud/private
* IBM Cloud Kubernetes Service https://console.bluemix.net/catalog/infrastructure/containers-kubernetes
* Deploy a microservices application on Kubernetes https://www.ibm.com/cloud/garage/tutorials/microservices-app-on-kubernetes?task=0
* IBM Cloud Kubernetes Service: Manage apps in containers and clusters on cloud https://www.ibm.com/cloud/garage/content/run/tool_ibm_container/

## Understanding Event Driven Microservices patterns

Adopting messaging (Pub/Sub) as a microservice communication backbone involves using at least the following patterns:
* microservices publish events when something happens in the scope of their control. For example, an update in the business entities they are responsible for.
* A microservice interested in other business entities, subscribe to those events and it can update its own states and business entities when receiving such events. Business entity keys needs to be unique, immutable.
* Business transactions are not ACID and span multiple services, they are more a series of steps, each step is supported by a micro-service responsible to update its own entities. We talk about eventual consistency of the data.
* The message broker needs to guarantee that events are delivered at least once and the microservices are responsible to manage their offset from the stream source and deal with inconsistency, by detecting duplicate events.
* At the microservice level, updating data and emitting event needs to be an atomic operation, to avoid inconsistency if the service crashes after the update to the datasource and before emitting the event. This can be done with an eventTable added to the microservice datasource and an event publisher that read this table on a regular basis and change the state of the event once published. Another solution is to have a database transaction log reader or miner responsible to publish event on new row added to the log.
* One other approach to avoid the two phase commit and inconsistency is to use an Event Store or event sourcing to keep trace of what is done on the business entity with enough data to rebuild the data. Events are becoming facts describing state changes done on the business entity.

[Read more](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/service-mesh/readme.md),

## Code References

* The K Containers shipment use cases provides a supporting EDA example  https://github.com/ibm-cloud-architecture/refarch-kc
* Within K Containers the following are example microservices  https://github.com/ibm-cloud-architecture/refarch-kc-ms
