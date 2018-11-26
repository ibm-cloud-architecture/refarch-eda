## Event Sources

When we consider an Event Driven architecture we think about event producers and event consumers as the interaction points with events. As we develop event driven applications following a Microservices architecture the  Microservices we develop  will play the role of both event producers and event consumers, with the events being passed as the communication pay load between them.

However as we look at the wider opportunities which being event driven brings to us, we need to widen our view and consider event sources which come from beyond the application code we are writing, events wihch may be produced from outside our immediate system but have business relevance or enable us to gain valuable insights into things which are affecting our business.

Here are a set of commonly seen event sources
* IOT Devices/Sesnors showing device status changes
* Click Stream data from web and mobile applictions
* Mobile applications, HTTP to BFF and then to topic
* Geospacial data
* Scoial Media feeds
* Real time voice feeds

## Intgeration through  Events

While some Event driven applications will  stand alone, in many cases they will  require integration with existing (legacy ) enteprise applications and data sources.

There can be advanatges in enabling these integrations to be event driven at the earliest point.  Developers could modify code to emit events,  which then become available through the EDA to the event driven application developers.

Less introusive options may be presented where
* Messaging (MQ) solutions are deployed in the legacy environments which can be bridged into the cloud native EDA.
* Change Data Capture capabilities could publish changes as events to the EDA for legacy databases.

## Event Standards and Schemas
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
