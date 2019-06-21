# EDA Skill Journey

Implementing cloud native, event-driven solution with microservices deployed on kubernetes involves a broad skill set. We are proposing here a learning journey for developer with good programming background. This project includes best practices and basic knowledge on the technologies used inthe solution implementation, which represent the most used ones in modern architecture. 

The EDA reference solution implementation [in this project](https://ibm-cloud-architecture.github.io/refarch-kc) includes a set of technology we are using that represent the modern landscape of cloud native applications (Kafka, maven, java, microprofile, kafka API, Kafka Stream API, Spring boot, Python, Nodejs, and Postgresql) but also some specific analytics and AI components like Streams analytics and machine learning with Jupyter notebook. A developer who wants to consume this content does not need to know everything at the expert level. You can progress by steps and it will take a good month to digest everything. We are also proposing a bootcamp to build, deploy and re-implement part of the ["Reefer container shipment solution"](https://ibm-cloud-architecture.github.io/refarch-kc). 

!!! note
    We expect you have some good knowledge around the following technologies.

    * Nodejs / Javascript / Typescripts
    * Java 1.8 amd microprofile architecture
    * Python 3.6
    * Angular 7, HTML, CSS  - This is for the user interface but this is more optional.
    * Maven, npm, bash
    * WebSphere Liberty or OpenLiberty
    * Docker
    * Docker compose
    * Helm
    * Kubernetes
    * Apache Kafka, Kafka API


## Getting started around the core technologies used in EDA

From the list above, the following getting started and tutorials can be studied to get a good pre-requisite knowledge:

* [From zero to hero in Java 1.8 - an infoworld good article](https://www.infoworld.com/article/3130466/java/java-8-programming-for-beginners-go-from-zero-to-hero.html)
* [Open Liberty getting started application](https://github.com/IBM-Cloud/get-started-java)
* [Getting started with Apache Maven](https://maven.apache.org/what-is-maven.html)
* [Getting started Nodejs and npm](https://nodejs.org/en/docs/guides/getting-started-guide/)
* [Getting started with Apache Kafka](https://kafka.apache.org/quickstart) and [the Confluent blog for getting started with Kafka](https://www.confluent.io/blog/apache-kafka-getting-started/)
* [Angular tutorial](https://angular.io/tutorial) - This is for the user interface but this is more optional.
* [Docker getting started](https://docs.docker.com/get-started/)
* [Getting started with Open Liberty](https://openliberty.io/guides/getting-started.html)
* [Kubernetes](https://kubernetes.io/docs/tutorials/kubernetes-basics/) and IBM developer [learning path for Kubernetes](https://developer.ibm.com/series/kubernetes-learning-path/) and the Garage course [Kubernetes 101](https://www.ibm.com/cloud/garage/content/course/kubernetes-101/0).

* [Use the "Develop a Kubernetes app with Helm" toolchain on IBM Cloud](https://www.ibm.com/cloud/garage/tutorials/use-develop-kubernetes-app-with-helm-toolchain)
* [Getting started in Python](https://www.python.org/about/gettingstarted/)
* [Applying a test driven practice for angular application](https://github.com/ibm-cloud-architecture/refarch-caseportal-app/blob/master/docs/tdd.md)

--- 

## Event Driven Specifics

Now the event driven microservice involve specific technologies and practice. The following links should be studied in the proposed order:

* [Why Event Driven Architecture now?](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture)

### EDA fundamentals

* [Key concepts](./concepts/README.md)
* [Reference architecture with event backbone, microservices and real time analytics](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/reference-architecture)
* [Extended Reference Architecture with machine learning and AI integrated with real time analytics](https://www.ibm.com/cloud/garage/architectures/eventDrivenExtendedArchitecture) with machine learning workbench and event sourcing as data source, and real time analytics for deployment.
* [Event sources - as event producers](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-event-sources)
* [Event backbone where Kafka is the main implementation](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-event-backbone)
* [High availability and disaster recovery with IBM Event Streams or Kafka Architecture Considerations](./kafka/arch.md)

### Event driven microservice development

* [Event driven design patterns for microservice](./evt-microservices/ED-patterns.md)  with CQRS, event sourcing and saga patterns.
* [Processing continuous streaming events](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-event-streams)
* [Event-driven cloud-native applications](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-cloud-native-apps)
* [Kafka producer API documentation](http://kafka.apache.org/11/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html) with some of our [event producers best practices](./kafka/producers.md)
* [Kafka consumer API documentation](http://kafka.apache.org/11/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html) with some of our own [Event consumers best practices](./kafka/consumers.md)
* [Kafka Stream APIs](https://kafka.apache.org/documentation/streams/), Java or Scala based API to implement functional processing as a chain of operation to consumer events and generating new event stream. 
* [Act on events with IBM Cloud Functions](./evt-action/README.md)
* [IBM Event Streams - stream analytics app](https://developer.ibm.com/streamsdev/docs/detect-events-with-streams/) Event detection on continuous feed using Streaming Analytics in IBM Cloud. 
* [Kafka monitoring](./kafka/monitoring.md)
* [Kafka Python API](https://github.com/confluentinc/confluent-kafka-python) and some examples in our [integration tests project](https://ibm-cloud-architecture.github.io/refarch-kc/itg-tests/)
* [Kafka Nodejs API used in the voyage microservice](https://ibm-cloud-architecture.github.io/refarch-kc-ms/voyagems/)


### Methodology

* [Event storming methodology](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-storming-methodology)
* [A concrete example to apply event storming, for a container shipment use case.](https://ibm-cloud-architecture.github.io/refarch-kc/analysis/readme/)
* [Domain design driven implementation from event storming](./methodology/ddd.md)

--- 

## Kubernetes, docker, microprofile

* As we can use docker compose to control the dependencies between microservices and run all the solution as docker containers, it is important to read the [Docker compose - getting started](https://docs.docker.com/compose/gettingstarted/) article. 
* Kafka is [Getting started with IBM Cloud Event Streams](https://cloud.ibm.com/docs/services/EventStreams?topic=eventstreams-getting_started#getting_started) the IBM product based on Kafka on public cloud
* [IBM Cloud Private Event Streams](https://www.ibm.com/cloud/event-streams) the IBM product based on Kafka for private cloud
* [Understand docker networking](https://docs.docker.com/network/) as we use docker compose to run the reference implementation locally. 
* [The evolving hybrid integration reference architecture](https://developer.ibm.com/articles/mw-1606-clark-trs/): How to ensure your integration landscape keeps pace with digital transformation
* [Java microprofile application](https://microprofile.io/)
* [Deploy MicroProfile-based Java microservices on Kubernetes](https://developer.ibm.com/patterns/deploy-microprofile-java-microservices-on-kubernetes/)
* [Knative introduction](https://developer.ibm.com/articles/knative-what-is-it-why-you-should-care/)
* [How to deploy, manage, and secure your container-based workloads on IKS](https://www.ibm.com/blogs/bluemix/2017/05/kubernetes-and-bluemix-container-based-workloads-part1/) and [part 2](https://www.ibm.com/blogs/bluemix/2017/05/kubernetes-and-bluemix-container-based-workloads-part2/)

--- 

## Hands-on labs

The next steps beyond getting started and reading our technical point of view, you can try to deeper dive into the solution implementation and deployment: The source of this virtual bootcamp is the ["Reefer container shipment solution"](https://ibm-cloud-architecture.github.io/refarch-kc).

!!! note
        At the end of this training you should have the following solution up and running (See detailed description [here](https://ibm-cloud-architecture.github.io/refarch-kc/design/architecture/#components-view)):

    ![](kc-mvp-components.png)

### Understand the event storming analysis and derived design

For those who are interested by the methodology applied we propose to review:

* [The solution introduction](https://ibm-cloud-architecture.github.io/refarch-kc/introduction) to get a sense of the goals of this application. (7 minutes read)
* Followed by the [event storming analysis report](https://ibm-cloud-architecture.github.io/refarch-kc/analysis/readme/) (15 minutes read).
* and [the design derived](https://ibm-cloud-architecture.github.io/refarch-kc/design/readme/) from this analysis. (15 minutes reading)

### Prepare a local environment

Here you have two options: running with docker compose, or running within Minikude, or the kubernetes single note running with docker (At least on Mac it works!).

* Get a local Kafka backbone environment, using docker compose, up and running to facilitate your development and testing. [Get an event backbone up and running on your laptop in less than 3 minutes](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/local/#start-kafka-and-zookeeper).
* Or use [Minikube](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/minikube/#pre-requisites) to get kafka, zookeeper and poastgreSQl up and running on kubernetes

### Build and run the solution

!!! goals
    Build and run the solution so you can understand the maven, nodejs build process with docker stage build.

* [Build and deploy the solution locally using docker compose](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/local/)
* [Or build and deploy the solution locally using Minikube](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/minikube/#deploy-each-component-of-the-solution)
* [Execute the integration tests](https://ibm-cloud-architecture.github.io/refarch-kc/itg-tests/) to validate the solution end to end.
* [Optional] [Execute the demonstration script](https://ibm-cloud-architecture.github.io/refarch-kc/demo/readme/)

### Review event driven patterns

* Review the [Event sourcing explanations](https://ibm-cloud-architecture.github.io/refarch-eda/evt-microservices/ED-patterns/#event-sourcing)
* Review the [CQRS pattern](https://ibm-cloud-architecture.github.io/refarch-eda/evt-microservices/ED-patterns/#command-query-responsibility-segregation-cqrs-pattern) and the implementation in the [order microservice]().



#### Data replication with Kafka

One of the common usage of using Kafka is to combine it with a Change Data Capture component to get update from a "legacy" data base to the new microservice runtime environment.

We are detailing an approach in [this article](https://ibm-cloud-architecture.github.io/refarch-data-ai-analytics/preparation/data-replication/).

### Other deployments

* [Deploying the solution on IBM Cloud Kubernetes Service](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/iks)
* [Deploying the solution on IBM Cloud Private](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/icp)
* [Develop a toolchain for one of the container manager service](https://ibm-cloud-architecture.github.io/refarch-kc-container-ms/cicd/)
* [Our Kubernetes troubleshooting notes](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/icp/troubleshooting.md)

### Real time analytics and Machine learning

* [IBM Cloud Streaming Analytics introduction](https://cloud.ibm.com/catalog/services/streaming-analytics) and [getting started](https://cloud.ibm.com/docs/services/StreamingAnalytics?topic=StreamingAnalytics-gettingstarted#gettingstarted)

* [Apply predictive analytics on container metrics for predictive maintenance use case](https://ibm-cloud-architecture.github.io/refarch-kc-container-ms/metrics/)








