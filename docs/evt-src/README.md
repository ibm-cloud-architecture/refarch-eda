# Event Sources

When we consider an Event Driven architecture we think about event producers and event consumers as the interaction points with events. As we develop event driven applications following a Microservices architecture the  Microservices we develop  will play the role of both event producers and event consumers, with the events being passed as the communication pay load between them.

However as we look at the wider opportunities which being event driven brings to us, we need to widen our view and consider event sources which come from beyond the application code we are writing, events which may be produced from outside our immediate system but have business relevance or enable us to gain valuable insights into things which are affecting our business.

Here are a set of commonly seen event sources
* IOT Devices/Sesnors showing device status changes
* Click Stream data from web and mobile applictions
* Mobile applications, HTTP to BFF and then to topic
* Geospacial data
* Scoial Media feeds
* Real time voice feeds



## IOT Devices/Sensors

With IOT devices and sensors we typically have a gateway providing the connectivity for the device,  and a level of event enrichment and filtering. In terms of Domain Driven Design we would see the device and gateway as being the techincal domain and the event driven reference architcture providing the infratsructure for the applicatoins in a business domain.

In practice the IOT gateaway or platform  would provide the connectivity and would be the point of fitering and consoilidation of events so that only business relevant events are passed up to the busiess domain. The gateway may also be the point where the technical event is enhanced to relate to something recogizable at the business level, an example of this could be relating a device number/idetifier in the event to something that the business would recognize.

<img src="../hl-arch-iot.png" width="1024px">

## Click Stream Data

Clickstream data is often used to understand the behavior of  usesrs as they navigate their way through web or mobile apps.  It provides a recording of the actions they take, such as the the clicks, the mousemovements, the gestures.

Analysis of the clickstream data can lead to deep understanding of how users actually interact with the application. It enables detection of where users struggle and allows developers to look for ways to imporve the experience.

Processing the click stream in real time  in an event driven architecture can aslo give rise to the opportunities to take direction action in response to what a user is currently doing, or more acuratley has just done.

There are various "collectors" which enable collection of standard clickstream events and allow custom actions to be collected as events typically through tags in javascript.

Withing the Apache Open Source communities Divolte colletor is an example of one of these collectores which will directly publish the events to Kafka topics https://divolte.io/

<img src="../hl-arch-clickstream.png" width="1024px">

## Microservices as event producers and consumers
The event driven rerefence architure provides support for eventy driven microservices, this is microservices are conencted and cimmunicate via the pub/sub communication protocol within the Event Backbone.

With kafka as the event backbone and pub/sub messaging provider,  microservices can use the  Kafka API's to publish and listen for events.

[Read more ...](docs/servicemesh/README.md) for more details


# Event Standards and Schemas
Where we have control as the producer of an event we should consider having an event schema and following a standard to privde the best opportunity for portability of the solutions across cloud environments.
With a lack of formal standards, a working group under the Cloud Native Computing Foundation (CNCF) has recently been formed to define and propose [Cloud Events](https://cloudevents.io/) as the standard.

Our recommendation is follow CloudEvents where we have the ability to define the event structure and so pass "CloudEvents" through the event backbone.

The examples included in this repository will use CloudEvents with Json payloads where we define and pass events into the backbone.

## Supporting Products
* [Kafka Producer API for Java](https://kafka.apache.org/10/javadoc/?org/apache/kafka/clients/producer/KafkaProducer.html)
* [Nodejs kafka client]()
* [Springboot streams]()

## Code Reference
The following code repositories can be used for event sourcing inspiration:
* [PumpSimulator](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/tree/master/asset-event-producer#pump-simulator) to send New Pump/ Asset event or Metric events to emulate intelligent IoT Electrical Pump.
* [Simple text message producer](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/tree/master/asset-event-producer#basic-text-message-pubsubscribe)
