# Event Sourcing

Any component / application can be an event producer. The common event producers are:
* web applications, user's click stream. HTTP or direct link to topic.
* Mobile applications, HTTP to BFF and then to topic
* Sensors / Internet of Thing, most likely connected via MQTT or HTTP   
* Business Processes, can be instrumented to send events when reaching step or transition in the process
* Microservices
* Social media, sending unstructured data
* Database triggers or transaction log digestion
* Other messaging system like MQ, used to support integration with mainframe and distributed systems.

Using 'legacy' applications, to integrate with event backbone, developers need to modify code to emit events so consumers can work on. Which leads to consider different things like:  
* what is the payload useful for others to consume?
* what the size that can be consume?
* Should we standardize to an event structure to be consistent cross applications and portable? There are work on specifications at [Cloud Events](https://cloudevents.io/)
* What is the potential throughput on the backbone side? Do we need buffering capacity on consumer side?
* Do we need to consider idempotent producer or not? This is to support exactly once delivery.
* Do we need to send the same event to multiple topics in a 'transactional' way.
* What is the failover mechanism to put in place if there is communication issue to the event backbone?
* What is the level of durability expected? When should we consider the event producer request to be completed? Do we authorize duplicate event?
* What are the memory constraints on the client side, to control the buffering?


## Supporting Products
* [Kafka Producer API for Java](https://kafka.apache.org/10/javadoc/?org/apache/kafka/clients/producer/KafkaProducer.html)
* [Nodejs kafka client]()
* [Springboot streams]()

## Code Reference
The following code repositories can be used for event sourcing inspiration:
* [PumpSimulator](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/tree/master/asset-event-producer#pump-simulator) to send New Pump/ Asset event or Metric events to emulate intelligent IoT Electrical Pump.
* [Simple text message producer](https://github.com/ibm-cloud-architecture/refarch-asset-analytics/tree/master/asset-event-producer#basic-text-message-pubsubscribe)
