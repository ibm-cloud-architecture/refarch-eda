# Event Backbone

The event backbone is the communication layer in the event driven architecture.  It provides the connection between event driven capabilities and for cloud native it becomes part of the fabric of the microservies infrastructure. It needs to provide the Pub/Sub communication at scale with minimal latency to enable developers to take benenfit of the loose coupling of event driven microservices.

At this high level we would consider two types of relevant technologies for the event backbone, *Messaging Systems* and *Event Logs*.  Both technology types could be used to achieve the event communication style, with the "Publish and  subscribe" model.

However, it is also important to consider other capabilities which event driven solutions typically make use of connected to the *event communication*

* Keeping an **Event Log** as a time sequenced as it happend recording of events  (Source of the truth)
* Support for event based state management **Event Sourcing**
* Enabling direct *Replay of events*
* Enabling programmatic access to the *continuous event stream*

When viewed across this set of event driven capabilities, the choice between a Messaging System and an Event Log technology becomes clear.

![](evt-backbone-choices.png)


## Event Backbone supported capabilities

* Capability to store events for a period of time, allowing for potential downtime of the event consumers (ideally implemented with an event log)
* Immutable data : Consistent replay for evolving application instances
* Facilitate many consumers: Shared central “source of truth”
* Event log history is becoming a useful source for data scientists and machine learning model derivation

![](evt-backbone.png)

We are addressing some of those features in our Kafka [summary here](../kafka/readme.md)

## Supporting products

The IBM Event Streams offering provides a kafka service which can form the Event Backbone for your event driven architecture.  The service is available as a fully managed service within Public cloud and as a supported build withing IBM cloud Private.

* [IBM Event Streams Public Cloud](https://console.bluemix.net/catalog/services/event-streams)
* [IBM Event Streams Private Cloud](https://www.ibm.com/cloud/event-streams)
* [kafka](http://apache.kafka.org)
* [See also our own kafka study](../kafka/readme.md) on how to support HA and how to deploy to your local environment or to a kubernetes cluster like IBM Cloud Private.


