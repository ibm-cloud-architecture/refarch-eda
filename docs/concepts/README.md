# Concepts
As  we look at Event driven solutions and Event Driven Architectures there are a number of concepts that we should understand as the underpin the very reasoning of being event driven.

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
* Ones where we connect to a real time event streams, eg from an IOT device, a Voice Stream from a telephone system, a Video stream, Ship/Plane locations from global positioning systems.

## Commands

In most cases, events are notifications of change of state, so a record of something which has happened.  Notifications are issued (or published) and interested parties may subscribe and take action on the events.  Typically, there is no connection back to the issuer of the notificaton that it has been processed.

In event driven solutions we may also consider sending a command, so an *instruction to do something*.  In this case it is a request for something to be done, and we are more likely to be interested in knowing if the commanded was carried out and what the result was.

## Events or Messages ?

Thinking in this way about events, and commands, brings the question of the relationship of event driven and messages systems.

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

Cohesion is the degree to which related things are encapsulated together in the same software module. At this point, for the purposes of our EDA discussion, we define module as an independently deployable software unit that has high cohesion.  Cohesion is strongly related to coupling in the sense that a highly cohesive module communicates less with other modules, thus reducing the number of events, but most importantly, the number of event types in the system. The fewer modules interact with each other, the less coupled they are.
Achieving cohesion in software while at the same time optimizing module size for flexibility and adaptability is hard but it is something that should be aimed for. Designing for cohesion starts with a holistic understanding of the problem domain and good analysis work. Sometimes it must also take into account the constraints of the supporting software environment. Monolithic implementations should be avoided, as should implementations that are excessively fine-grained.

## Event Sourcing

Event Sourcing is a method of recording state changes through events. Event sourcing can have appeal in distributed solutions where they may be a high number of instances with:

* Services publish events whenever the data they control change.
* The event publish needs to be unique (atomic) and the source reliable (no event duplication).
* Event sourcing persists the state of a business entity as a sequence of state changing events.
* The event store is used for persistence. The service is not persisting data in a relational database anymore.
* To avoid keeping a huge amount of change log, snapshot can be performed to keep a view of the data at a given point of time. Changes will then apply from a snapshot.
* Queries have to reconstruct the state of the business entity from a snapshot.

## Command Query Responsibility Segregation

When using a microservices architecture pattern, each service is responsible to manage its persistence for the business entities it manages. Therefore, it is challenging to perform join query on multiple business entities across microservice boundaries.
Basically Command Query Responsibility Segregation, CQRS, is a pattern where the CUD operations (the commands) are done in one service while query / read operations are supported by a separate service. The command-side emits events when data changes. The Query side maintains a set of views that are kept up to date by subscribing to events.

One of the main advantages is to support multiple data denormalization and being able to scale easily. It is complex to implement, aim for code duplication and should not be considered as the silver bullet.


## Function as a service

As event consumer functions deliver stateless discrete step or task for the global event processing. The serverless approach will bring cost efficiency for the just on-demand invocation. It fits well in post processing with the event processing.
Cloud functions provides a simple way for developers to write code which takes action on an event.
Serverless computing model, is complete abstraction of infrastructure away from the developer, there is no need to worry about infrastructure/scaling. It supports event notifications and event commands, and help control cost as you pay for event processing compute time only.

