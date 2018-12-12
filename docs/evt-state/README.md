# Event Managed state

## Event Sourcing

The idea of event sourcing is that whenever we make a change to the state of a system:

* An application issues a notification *event* of the state change.
* Any interested parties can become consumers of the event and take required actions.
* The state change event is stored in an event log or event store in time order.
* The event log/store becomes the principal source of truth.
* The system state can be recreated to a point in time by reprocessing the events at any time in the future.
* The history of state changes becomes an audit record for the business and is often a useful source of data for data scientists to gain insights into the business.

![](evt-src.png)

In some cases the event sourcing pattern would be implemented completely within the event backbone, with the use of the event log and Kafka streams. However, we may also consider implementing the pattern with an external event store, which provides optimizations for how the data may be accessed/used.  For example [IBM Db2 Event store]( https://www.ibm.com/products/db2-event-store) can provide the handler and event store connected to the backbone and provide optimization for down stream analytical processing of the data.

In operation, the event stores persists all state changing events for an object together with a timestamp, in time order so creating a time series of changes for objects. The current state of an object can always be derived by replaying the events in the time series. At its most basic an event store only needs to store three pieces of information:

* The type of event or aggregate.
* The sequence number of the event.
* The data as a serialized blob.

More data can be added to help with diagnosis and audit, but the core functionality only requires a narrow set of fields. This should give rise to a very simple data design that can be heavily optimized for appending and retrieving sequences of records.

## Command Query Responsibility Segregation (CQRS)

The CQRS application pattern is frequently connected to event sourcing. In high level terms, CQRS says that we should separated the "command" operations from the "query/read" operations.

With the event sourcing pattern and CQRS we would typically end up with a pattern where updates are done as State notification events (change of state), which are persisted in the event log/store. On the read side we now have the option of persisting the state in different stores optimized for how other applications may query/read the data.

![](evt-cqrs.png)

## Event Sourcing, CQRS and Microservices

With the adoption of microservices we have explicitly separated state, so that a micro-service is bounded with its own state. Further with the use of event sourcing, we create a history log which is not easy to query.

The challenge now comes when we need to implement a query which requires a joining of data from multiple services.

One answer to this is to implement a CQRS pattern where we have state changes being published as events by multiple related business objects. Each change is persisted in the event log/event store, and a higher level operation or join microservice subscribes to each event and persists the data in a queryable data store

Read more on this pattern at https://microservices.io/patterns/data/cqrs.html


## Supporting Products
* [IBM Event Streams Public Cloud](https://console.bluemix.net/catalog/services/event-streams)
* [IBM Event Streams Private Cloud](https://www.ibm.com/cloud/event-streams)
* [IBM Db2 Event store](https://www.ibm.com/products/db2-event-store)
