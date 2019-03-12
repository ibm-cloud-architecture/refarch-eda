# EDA Skill Journey

Implementing cloud native, event-driven solution with microservices deployed on kubernetes involves a broad skill set. We are proposing here a tutorial journey from beginner to expert level.

The reference implementation includes a set of technologies we are using that represents the modern landscape of cloud native applications. A developer who wants to consume this content does not need to know everything at the expert level. But here is a list of technologies we are using in [our reference implementation](https://ibm-cloud-architecture.github.io/refarch-kc):

* Nodejs / Javascript / Typescripts
* Java 1.8
* Python 3.6
* Angular 7, HTML, CSS
* Maven, npm, bash
* WebSphere Liberty or OpenLiberty
* Docker
* Docker compose
* Helm
* Kubernetes
* Apache Kafka, Kafka API


## Getting started

From the previous list the following getting started and tutorials can be studied to get a good pre-requisite knowledge:

* [From zero to hero in Java 1.8](https://www.infoworld.com/article/3130466/java/java-8-programming-for-beginners-go-from-zero-to-hero.html)
* [Liberty getting started application](https://github.com/IBM-Cloud/get-started-java)
* [Getting started with Apache Maven](https://maven.apache.org/what-is-maven.html)
* [Getting started Nodejs and npm](https://nodejs.org/en/docs/guides/getting-started-guide/)
* [Getting started with Apache Kafka](https://kafka.apache.org/quickstart) and [Confluent blog for getting started](https://www.confluent.io/blog/apache-kafka-getting-started/)
* [Angular tutorial](https://angular.io/tutorial)
* [Docker getting started](https://docs.docker.com/get-started/)
* [Getting started with Open Liberty](https://openliberty.io/guides/getting-started.html)
* [Kubernetes](https://kubernetes.io/docs/tutorials/kubernetes-basics/) and IBM developer [learning path for Kubernetes](https://developer.ibm.com/series/kubernetes-learning-path/) and the Garage course [Kubernetes 101](https://www.ibm.com/cloud/garage/content/course/kubernetes-101/0).
* [Deploy MicroProfile-based Java microservices on Kubernetes](https://developer.ibm.com/patterns/deploy-microprofile-java-microservices-on-kubernetes/)
* [Use the "Develop a Kubernetes app with Helm" toolchain](https://www.ibm.com/cloud/garage/tutorials/use-develop-kubernetes-app-with-helm-toolchain)

### Event Driven Specifics

The following links should be studied in the proposed order and can serve as a long education journey:

* [Why EDA now?](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture)
* EDA fundamentals:
    * [Reference architecture](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/reference-architecture)
    * [Event sources](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-event-sources)
    * [Event backbone](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-event-backbone)
    * [Act after an event with IBM Cloud Functions](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-take-action-with-cloud-functions)
    * [Processing continuous streaming events](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-event-streams)
    * [Event-driven cloud-native applications](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-cloud-native-apps)
* Methodology to understand how to jumpstart an event-driven solution with the business team: 
    * [Event storming methodology](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-storming-methodology)
    * [A concrete example to apply event storming, for a container shipment use case.](https://ibm-cloud-architecture.github.io/refarch-kc/analysis/readme/)
* Getting started with the reference implementation. The first step is to get a local Kafka environment for development and testing. 
    * [Get an event backbone up and running on your laptop in less than 3 minnutes](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/local/#start-kafka-and-zookeeper) so you can play.

* As we can use docker compose to control the dependencies between microservices and run all the solution as docker containers, it is important to read the [Docker compose - getting started](https://docs.docker.com/compose/gettingstarted/) article. 
* Kafka is [Getting started with IBM Cloud Event Streams](https://cloud.ibm.com/docs/services/EventStreams?topic=eventstreams-getting_started#getting_started) the IBM product based on Kafka on public cloud
* [IBM Cloud Private Event Streams](https://www.ibm.com/cloud/event-streams) the IBM product based on Kafka for private cloud
* [Kafka API consumer](http://kafka.apache.org/11/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html) with our [own summary and implementation considerations](./kafka/consumers.md)
* [Kafka API producer](http://kafka.apache.org/11/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html) ith our [own summary and implementation considerations](./kafka/producers.md)
* [Kafka Stream APIs](https://kafka.apache.org/documentation/streams/), Java or Scala based API to implement functional processing as a chain of operation to consumer events and generating new event stream. 
* [IBM Cloud Streaming Analytics introduction](https://cloud.ibm.com/catalog/services/streaming-analytics) and [getting started](https://cloud.ibm.com/docs/services/StreamingAnalytics?topic=StreamingAnalytics-gettingstarted#gettingstarted)


## Next steps beyond getting started

* [Understand docker networking](https://docs.docker.com/network/) as we use docker compose to run the reference implementation locally. 
* [The evolving hybrid integration reference architecture](https://developer.ibm.com/articles/mw-1606-clark-trs/): How to ensure your integration landscape keeps pace with digital transformation
* [Our Kubernetes troubleshouting notes](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/icp/troubleshooting.md)
* [Applying a test driven practice for angular application](https://github.com/ibm-cloud-architecture/refarch-caseportal-app/blob/master/docs/tdd.md)

### KC Solution labs

* [Deploying the solution locally](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/local/)
* [Deploying the solution on IBM Cloud Kubernetes Service](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/iks)


### Event Driven Specifics

* [Extended Reference Architecture](https://www.ibm.com/cloud/garage/architectures/eventDrivenExtendedArchitecture) with machine learning workbench and event sourcing as data source, and real time analytics for deployment.
* [Event driven design pattern for microservice](./evt-microservices/ED-patterns.md) 
* [IBM Event Streams - National Oceanic and Atmospheric Administration (NOAA) stream analytics app](https://developer.ibm.com/streamsdev/docs/detect-events-with-streams/) Event detection on continuous feed using Streaming Analytics in IBM Cloud. 
* [Kafka monitoring](./kafka/monitoring.md)
* [Kafka Python API](https://github.com/confluentinc/confluent-kafka-python) and some examples in our [integration tests project](https://ibm-cloud-architecture.github.io/refarch-kc/itg-tests/)
* [Kafka Nodejs API]
* [Knative introduction](https://developer.ibm.com/articles/knative-what-is-it-why-you-should-care/)

## More advanced subjects

* [Kafka HA and enterprise deployment](./kafka/readme.md)
* [End to end reference implementation of EDA solution](https://ibm-cloud-architecture.github.io/refarch-kc). A concrete end to end solution to illustrate the different event-driven patterns. 
* [Mirror maker to maintain replicas cross clusters](https://cwiki.apache.org/confluence/pages/viewpage.action?pageId=27846330)
* [Integrate Kadka with MQ](https://ibm.github.io/event-streams/connecting/mq/)


