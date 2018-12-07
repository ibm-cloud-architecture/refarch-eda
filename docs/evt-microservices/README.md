# Event Drive Microservices
In this chapter we address multiple consumer types:
* Java microservices deployed using OpenLiberty
* Nodejs as a Function as a service.
* Web Application.
* MQ


IBM Cloud functions provides a *server-less* compute model and simplified programming model for event drive microservices with the complexities of managing active consumer service instances abstracted into the server-less compute stack.

## Supporting Products

* [Kafka Consumer API for Java](https://kafka.apache.org/10/javadoc/org/apache/kafka/clients/consumer/package-summary.html)


We are starting to address service mesh in [this note](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/service-mesh/readme.md), and adopting messaging as a microservice communication backbone involves using at least the following patterns:
* microservice publish events when something happens in the scope of their control, for example an update in the business entities they are responsible of.
* microservice interested by other business entities, subscribe to those events and it can update its own states and business entities when receiving such events. Business entity keys needs to be unique, immutable.
* Business transactions are not ACID and span multiple services, they are more a series of steps, each step is supported by a microservice responsible to update its own entities. We talk about eventual consistency of the data.
* the message broker needs to guarantee that events are delivered at least once and the microservices are responsible to manage their offset from the stream source and deal with inconsistency, by detecting duplicate events.
* at the microservice level, updating data and emitting event needs to be an atomic operation, to avoid inconsistency if the service crashes after the update to the datasource and before emitting the event. This can be done with an eventTable added to the microservice datasource and an event publisher that read this table on a regular basis and change the state of the event once published. Another solution is to have a database transaction log reader or miner responsible to publish event on new row added to the log.
* One other approach to avoid the two phase commit and inconsistency is to use an Event Store or event sourcing to keep trace of what is done on the business entity with enough data to rebuild the data. Events are becoming facts describing state changes done on the business entity.




## Code Reference
The following code repositories can be used for event sourcing inspiration:
* [PumpSimulator](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/tree/master/asset-event-producer#pump-simulator) to send New Pump/ Asset event or Metric events to emulate intelligent IoT Electrical Pump.
* [Simple text message producer](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/tree/master/asset-event-producer#basic-text-message-pubsubscribe)
