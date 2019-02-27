# EDA Skill Journey

Implementing cloud native, event-driven solution with microservices deployed on kubernetes involves a broad skill set. We are proposing here a tutorial journey to read and study from beginner to expert level.

The reference implementation includes a set of technologies we are using that represents the modern landscape of cloud native applications. A developer who wants to consume this content does not need to know everything at the expert level. But here is a list of things we are using:

* nodejs / Javascript / Typescripts
* Java 1.8
* Python 3.6
* Angular 7, HTML, CSS
* Maven, npm, bash
* WebSphere Liberty or OpenLiberty
* docker
* docker compose
* Helm
* Kubernetes
* Apache Kafka, Kafka API


## Getting started

* [From zero to hero in Java 1.8](https://www.infoworld.com/article/3130466/java/java-8-programming-for-beginners-go-from-zero-to-hero.html)
* [Liberty getting started application](https://github.com/IBM-Cloud/get-started-java)
* [Getting started with Apache Maven](https://maven.apache.org/what-is-maven.html)
* [Getting started nodejs / npm](https://nodejs.org/en/docs/guides/getting-started-guide/)
* [Getting started with Apache Kafka](https://kafka.apache.org/quickstart) and [Confluent blog for getting started](https://www.confluent.io/blog/apache-kafka-getting-started/)
* [Angular tutorial](https://angular.io/tutorial)
* [Docker getting started](https://docs.docker.com/get-started/)
* [Getting started with Open Liberty](https://openliberty.io/guides/getting-started.html)
* [Kubernetes](https://kubernetes.io/docs/tutorials/kubernetes-basics/) and IBM developer [learning path for Kubernetes](https://developer.ibm.com/series/kubernetes-learning-path/)
* [Deploy MicroProfile-based Java microservices on Kubernetes](https://developer.ibm.com/patterns/deploy-microprofile-java-microservices-on-kubernetes/)

### Event Driven Specifics

* [Why EDA now?](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture)
* EDA fundamentals:
    * [Reference architecture](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/reference-architecture)
    * [Event sources](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-event-sources)
    * [Event backbone](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-event-backbone)
    * [Act after an event with IBM Cloud Functions](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-take-action-with-cloud-functions)
    * [Processing continuous streaming events](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-event-streams)
    * [Event-driven cloud-native applications](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-cloud-native-apps)
    * [Event storming methodology](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-storming-methodology) and a [concrete example for container shipment use case.](https://ibm-cloud-architecture.github.io/refarch-kc/analysis/readme/)
* [Getting started with IBM Cloud Event Streams](https://cloud.ibm.com/docs/services/EventStreams?topic=eventstreams-getting_started#getting_started)
* [IBM Cloud Private Event Streams]()
* [Kafka API consumer](http://kafka.apache.org/11/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html) with our [own summary and implementation considerations](./kafka/consumers.md)
* [Kafka API producer](http://kafka.apache.org/11/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html) ith our [own summary and implementation considerations](./kafka/producers.md)
*[IBM Cloud Streaming Analytics introduction](https://cloud.ibm.com/catalog/services/streaming-analytics) and [getting started](https://cloud.ibm.com/docs/services/StreamingAnalytics?topic=StreamingAnalytics-gettingstarted#gettingstarted)


## Intermediate

* [Docker compose - getting started](https://docs.docker.com/compose/gettingstarted/)
* [Understand docker networking](https://docs.docker.com/network/) as we use docker compose to run the reference implementation locally. 
* [The evolving hybrid integration reference architecture](https://developer.ibm.com/articles/mw-1606-clark-trs/): How to ensure your integration landscape keeps pace with digital transformation
* [Kubernetes troubleshouting]()
* [Applying a test driven practice for angular application](https://github.com/ibm-cloud-architecture/refarch-caseportal-app/blob/master/docs/tdd.md)

### Event Driven Specifics

* [Extended Reference Architecture](https://www.ibm.com/cloud/garage/architectures/eventDrivenExtendedArchitecture) with machine learning workbench and event sourcing as data source, and real time analytics for deployment.
* [Event driven design pattern for microservice](./evt-microservices/ED-patterns.md) 
* [IBM Event Streams - National Oceanic and Atmospheric Administration (NOAA) stream analytics app](https://developer.ibm.com/streamsdev/docs/detect-events-with-streams/) Event detection on continuous feed using Streaming Analytics in IBM Cloud. 
* [Kafka monitoring](./kafka/monitoring.md)


## More advanced subjects for event-driven solution

* [Kafka Stream APIs](http://kafka.apache.org/11/javadoc/org/apache/kafka/streams/package-summary.html) and [our summary.](./kafka/kafka-stream.md)
* [Kafka HA and enterprise deployment](./kafka/readme.md)
* [End to end reference implementation of EDA solution](https://ibm-cloud-architecture.github.io/refarch-kc). A concrete end to end solution to illustrate the different event-driven patterns. 
* [Knative introduction](https://developer.ibm.com/articles/knative-what-is-it-why-you-should-care/)

