# Event Sourcing

Any component / application can be an event producer. Still to emit event you need some code changes and supporting event backbone.

There are some design considerations to integrate as an event producer:
* what is the payload useful for other?
* what the size that can be consume?
* Should we standardize to an event structure to be consistent cross applications and portable? There are work on specifications at [Cloud Events](https://cloudevents.io/)

## Producers
* Microservices
* MQ queues
* Web Application with click stream

### Supporting Products
* [Kafka API for Java]() , Nodejs
* [Springboot streams]()


UNDER construction!

![](../under-construction.png)
