 Concepts

## Events.

Events are notifications of change of state.  Notifications are issued ( or published ) and interested parties may subscribe and take action on the events.  Typically there is no connection  the issuer of the notification for what the action taken is and
no corresponding feedback that it has been processed

* Events are notifications of change of state.
* Typically it’s the change of state of something of interest to the business.
* They are a record of something which has happened.
* They can not be changed, that is they are immutable ( we can't change something which has happened)

## Event Streams.
An Event stream is a continuous un-bounded series of events.

* The start of the stream may have occurred before we started to process the stream
* The end of the stream is at some unknown point in the future
* Events are ordered by the point in time at which each event occurred.

When developing event driven solutions we will typically see two types of Event Stream,
* Ones where we have defined the events and published them into the stream as part of our solution
* Ones where we connect to a real time event stream, eg from an IOT device, a Voice Stream from a telephone system, a Video stream, Ship/Plane locations from global positioning systems.

## Commands
A *command*, is an *instruction to do something*. Typically commands are directed to a particular consumer which will run the required command/process

## Events and Messages
There is a long history of *messaging* in IT systems, and we could easily see an *event driven solution* and *events* in the context of  messaging systems  and messages, but there are different characteristics which are worth considering.

**Messaging** Messages transport a payload, messages are persisted until consumed, message consumers are typically directly targeted and related to the producer who cares that the message has been delivered and processed.

**Events:** Events are persisted as a repayable Stream History, event consumers  are not tied to the producer,
 An event is a record of something which has happened and so cant be changed ( you cant change history ),

<img src="../hl-arch-concepts1.png" width="1024px">

## Loose coupling
Loose coupling is one of the main benefits of event-driven processing. It allows event producers to emit events without any knowledge about who is going to consume those events. Likewise, event consumers do not have to be aware of the event emitters. Because of this, event consuming modules and event producer modules can be implemented in different languages or use technologies that are different and appropriate for specific jobs. Loosely coupled modules are better suited to evolve independently and, when implemented right, result in a significant decrease in system complexity.

Loose coupling, however, does not mean “no coupling”. An event consumer consumes events that are useful in achieving its goals and in doing so establishes what data it needs and the type and format of that data. The event producer emits events that it hopes will be understood and useful to consumers thus establishing an implicit contract with potential consumers. For example, an event notification in XML format must conform to a certain schema that must be known by both the consumer and the producer.  One of the most important things that you can do to reduce coupling in an event-driven system is to reduce the number of distinct event types that flow between modules. To do this you have pay attention to the cohesiveness of those modules.

## Cohesion
Cohesion is the degree to which related things are encapsulated together in the same software module. At this point, for the purposes of our EDA discussion, we define module as an independently deployable software unit that has high cohesion.  Cohesion is strongly related to coupling in the sense that a highly cohesive module communicates less with other modules, thus reducing the number of events, but most importantly, the number of event types in the system. The less modules interact with each other, the less coupled they are.
Achieving cohesion in software while at the same time optimizing module size for flexibility and adaptability is hard but it is something that should be aimed for. Designing for cohesion starts with a holistic understanding of the problem domain and good analysis work. Sometimes it must also take into account the constraints of the supporting software environment. Monolithic implementations should be avoided, as should implementations that are excessively fine-grained.
