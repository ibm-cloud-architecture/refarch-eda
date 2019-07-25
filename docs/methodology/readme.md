# Event driven methodology

!!! Warning
    Still under development


The event-driven microservices implementation methodology does not differ from agile microservice development, but some specific development activities needs to be added to the traditional workflow. 

The following figure illustrates the iterative loops aroung sprint activities, combined with a larger loop for release software to production and learn from there.

![](evt-methodology.png)

The main actors represented here are the archictect or technical squad leader responsible to conduct the technical assessments and problem solver, and the developers responsible to develop component and microservices. 

The activities start by defining business objective, hypothesis and measurement process for evaluating the business impacts of this new software project. As part of an event driven implementation we want to focus on identifying the events, using the [event storming workshop](eventstorming.md) which engages the business and product owner into defining the process flow from an event point of view. 

The combination of event storming and Domain Driven Design helps to build bounded context and the Ubiquitous Language. From there we can define the microservices to develop and define the way to manage the communication between those services to address the data flow and data eventual consistency support.