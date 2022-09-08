---
title: Event driven solution governance
description: Event driven solution governance
---

Digital businesses are real-time, responsive, scalable and flexible while focussing on delivering
outstanding customer experience. API and Microservices focus has achieved a lot in enabling
this transformation, supporting real-time interactions and increasing levels of agility through
decoupling applications. But digital business requires more, it needs to become more timesensitive,
contextual and event-driven in nature. Events is how the modern digital business operates, and eventdriven
architecture allows IT to align to this.

Governance involves a lot of different view points, so in this section we will cover what we think are important to govern the development, deployment and maintenance of event-driven solution. The first important question to address is do I need to use event-driven solution? This is what the fit for purpose is about.

Most EDA adoption starts from the adoption of some new programming model based on the reactive manifesto, or by selecting a modern middleware to support loosely coupled microservice, like Kafka, all this in the context of one business application. 
From the first successful project, it is important to consider adopting a more strategic approach:

* to select technologies
* to apply common architecture and design patterns
* to adopt data models governance and control data lineage
* to apply common methodology to quickly onboard new business initiative, and be able to deploy new capability on top of the EDA in question of days or weeks. 

## Fit for Purpose

In EDA context the fit for purpose needs to help responding to the high level question on when to use event-driven solution for a new application, but also to address when to use modern pub/sub event backbone versus queuing products, what data stream processing to use, what are the different use cases and benefits.

We have developed the content in a [separate note](../../concepts/fit-to-purpose) as the subject can become long to describe.

## Architecture patterns

We have already describe in [this section](../../introduction/reference-architecture/#event-driven-architecture) as set of event-driven architecture patterns that can be leveraged while establishing EDA practices which includes how to integrate with data in topic for doing feature engineering. An example of Jupiter notebook can also be reused for this as described in [this data collection article](https://ibm-cloud-architecture.github.io/vaccine-solution-main/solution/cp4d/eventStream/).

Legacy integration and coexistence between legacy applications or mainframe transactional application and microservices is presented in [this section](../../introduction/reference-architecture/#legacy-integration).

The [modern data pipeline](../../introduction/reference-architecture/#modern-data-lake) is also an important architecture pattern where data ingestion layer is the same as the microservice integration middleware and provide buffering capability as well as stateful data stream processing.

From an implementation point of view the following design patterns are often part of the EDA adoption:

* [Event sourcing](../../patterns/event-sourcing/)
* [Command Query Responsibility Segregation](../../patterns/cqrs/)
* [Saga pattern](../../patterns/saga/)
* [Transactional outbox](../../patterns/intro/#transactional-outbox)
* [Event reprocessing with dead letter](../../patterns/dlq/)

You can also review the other [microservice patterns in this note](../../patterns/intro).

## Getting developers on board

## Data lineage

Data governance is a well established practices in most companies. Solutions need to address the following needs:

* Which sources/producers are feeding data?
* What is the data schema?
* How to classify data
* Which process reads the data and how is it transformed?
* When was the data last updated?
* Which apps/ users access private data?

In the context of event-driven architecture, one focus will be to ensure re-use of event topics, 
control the schema definition, have a consistent and governed ways to manage topic as service, 
domain and event data model definition, access control, encryption and traceability. 
As this subject is very important, we have started to address it in a [separate 'data lineage' note](../../methodology/data-lineage/).

## Deployment Strategy

Hybrid cloud, Containerized


security and access control
Strimzi - cruise control
active - active
topic as self service

## Ongoing Maintenance

Procedures performed regularly to keep a system healthy
Cluster rebalancing
 Add new broker - partition reassignment

Product migration


## Operational Monitoring 

assess partition in heavy load
assess broker in heavy load
assess partition leadership assignment

Problem identification
System health confirmation

## Error Handling
Procedures, tools and tactics to resolve problems when they arise