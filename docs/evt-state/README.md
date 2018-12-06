# Event Managed state

## Event Sourcing

Event Sourcing is a method of recording state changes through events. Event sourcing can have appeal in distributed solutions where the may be a high number of service instances which are updating state.

* Services publish events whenever the data they control changes.
* The event publish needs to be unique (atomic) and the source reliable (no event duplication).
* Event sourcing persists  each change of state as a new record in the event log or an optimised event store
* The event store is used for persistence.


The service is not persisting data in a relational database anymore.
* To avoid keeping a huge amount of change log, snapshot can be perform to keep a view of the data at a given point of time. Changes will then apply from a snapshot.
* Queries have to reconstruct the state of the business entity from a snapshot.

## Command Query Responsibility Segregation

When using a microservices architecture pattern, each service is responsible to manage its persistence for the business entities it manages. Therefore it is challenging to perform join query on multiple business entities across micro-service boundaries.
Basically Command Query Responsibility Segregation, CQRS, is a pattern where the CUD operations (the commands) are done in one service while query / read operations are supported by a separate service. The command-side emits events when data changes. The Query side maintains a set of views that are kept up to date by subscribing to events.

One of the main advantages is to support multiple data denormalization and being able to scale easily. It is complex to implement, aim for code duplication and should not be considered as the silver bullet.



## Supporting Products


## Code Reference
The following code repositories can be used for event sourcing inspiration:
* TDB
