# Understanding event driven microservice patterns

!!! abstract
    In this article, we are detailing some of the most import event-driven patterns to be used during your microservice implementation and when adopting kafka as an event backbone.

Adopting messaging (Pub/Sub) as a microservice communication backbone involves using at least the following patterns:

* [Decompose by subdomain](https://microservices.io/patterns/decomposition/decompose-by-subdomain.html), event driven microservices are still microservices, so we need to find them, and the domain-driven subdomains is a good approach to identify and classify business function and therefore microservices. With the event storming method, aggregates help to find those subdomain of responsibility. 
* [Database per service](https://microservices.io/patterns/data/database-per-service.html) to enforce each service persists data privately and is accessible only via its API. Services are loosely coupled limiting impact to other service when database schema changes. The database technology is selected from business requirements. The implementation of transactions that span multiple services is complex and enforce using the Saga pattern. Queries that goes over multiple entities is a challenge and CQRS represents an interesting solution. 
* [Strangler pattern](#strangler-pattern) is used to incrementally migrate an existing, monolytic application by replacing a set of features to a microservice but keep both running in parallel. Applying a domain driven design approach, you may strangle the application using bounded context. But then aS soon as this pattern is applied, you need to assess the co-existence between existing bounded contexts and the new microservices. One of the challenges will be to define where the write and read operations occurs, and how data should be replicated between the contexts. This is where event driven architecture helps. 
* [Event sourcing](event-sourcing.md) persists the state of a business entity such an Order as a sequence of state-changing events. 
* [Command Query Responsibility Segregation](cqrs.md) helps to separate queries from commands and help to address queries with cross-microservice boundary.
* [Saga pattern:](saga.md) Microservices publish events when something happens in the scope of their control like an update in the business entities they are responsible for. A microservice interested in other business entities, subscribe to those events and it can update its own states and business entities when receiving such events. Business entity keys needs to be unique, immutable.
* [Event reprocessing with dead letter](#event-reprocessing-with-dead-letter-pattern): event driven microservice may have to call external service via synchronous call, we need to process failure to get response from those service, using event backbone.


## Strangler pattern

### Problem

How to migrate a monolytics application to microservice without doing a big bang, redeveloping the application from white page. Replacing and rewritting an existing application can be a huge investment. Rewritting a subset of business functions while running current application in parallel may be relevant and reduce risk and velocity of changes. 

### Solution

The approach is to use a "strangler" interface to dispatch request to new or old features. Existing features to migrate are selected by trying to isolate sub components. 

One of main challenge is to isolate data store and how the new microservices and the legacy application are accessing the shared data. Continuous data replication can be a solution to propagate write model to read model. Write model will most likely stays on the monolitic application, change data capture can be used, with event backbone to propagate change to read model.

The facade needs to be scalable and not a single point of failure. It needs to support new APIs (RESTful) and old API (most likely SOAP).

## Event reprocessing with dead letter pattern

With event driven microservice, it is not just about pub/sub. There are use cases where the microservice needs to call existing service via an HTTP or RPC call. The call may fail. So what should be the processing to be done to retry and gracefully fail by leveraging the power of topics and the concept of dead letter.

This pattern is influenced by the adoption of Kafka as event backbone and the offset management offered by Kafka. Once a message is read from a Kafka topic by a consumer the offset can be automatically committed so the consumer can poll the next batch of events, or in most the case manually committed, to support business logic to process those events. 

The figure below demonstrates the problem to address: the `reefer management microservice` get order event to process by allocating a reefer container to the order. The call to update the container inventory fails. 

![Dead letter context](images/dl-1.png)

At first glance, the approach is to commit the offset only when the three internal operations are succesful: write to reefer database (2), update the container inventory using legacy application service (3), and produce new event to `orders` (4) and `containers` (5) topics. Step (4) and (5) will not be done as no response from (3) happened. 

In fact a better approach is to commit the read offset on the orders topic, and then starts the processing: the goal is to do not impact the input throughput. In case of step (2) or (3) fails the order data is published to an `order-retries` topic (4) with some added metadata like number of retries and timestamp.

![Dead letter context](images/dl-2.png)

A new order retry service or function consumes the `order retry` events (5) and do a new call to the remote service using a delay according to the number of retry already done: this is to pace the calls to a service that has issue for longer time. If the call (6) fails this function creates a new event in the `order-retries` topic with a retry counter increased by one. If the number of retry reaches a certain threshold then the order is sent to `order-dead-letter` topic (7) for human to work on. A CLI could read from this dead letter topic to deal with the data, or retry automatically once we know the backend service works. Using this approach we delay the call to the remote service without putting too much preassure on it.

For more detail we recommend this article from Uber engineering: [Building Reliable Reprocessing and Dead Letter Queues with Apache Kafka](https://eng.uber.com/reliable-reprocessing/).