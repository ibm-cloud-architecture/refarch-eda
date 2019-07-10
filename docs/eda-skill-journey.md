# EDA Skill Journey

Implementing cloud native, event-driven solution with microservices deployed on kubernetes involves a broad skill set. We are proposing here a learning journey for developer with good programming background. This project includes best practices and basic knowledge on the technologies used in the Reefer solution implementation. This solution implementation can be accessed [in this project](https://ibm-cloud-architecture.github.io/refarch-kc) and it includes a set of technologies that represent the modern landscape of cloud native applications (Kafka, maven, java, microprofile, kafka API, Kafka Stream API, Spring boot, Python, Nodejs, and Postgresql) but also some specific analytics and AI components like Streams analytics and machine learning with Jupyter notebook for predictive scoring. A developer who wants to consume this content does not need to know everything at the expert level. You can progress step by step and it will take a good month to digest everything. We are also proposing a bootcamp to build, deploy and re-implement part of the ["Reefer container shipment solution"](https://ibm-cloud-architecture.github.io/refarch-kc). 

!!! note
    We expect you have some beginner to good knowledge around the following technologies.

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

From the list above, the following getting started and tutorials can be studied to get a good pre-requisite knowledge. You can skip those tutorials if you are already confortable on those technologies, or you can come back later, when starting the specifics hands on labs, if you need to.

### Java 

* [From zero to hero in Java 1.8 - an infoworld good article](https://www.infoworld.com/article/3130466/java/java-8-programming-for-beginners-go-from-zero-to-hero.html)
* [Getting started with Open Liberty from the openliberty.io site.](https://openliberty.io/guides/getting-started.html)
* Another [Open Liberty getting started application from IBM Cloud team](https://github.com/IBM-Cloud/get-started-java)
* [Getting started with Apache Maven](https://maven.apache.org/what-is-maven.html)
* [Java microprofile application](https://microprofile.io/)
* [Deploy MicroProfile-based Java microservices on Kubernetes](https://developer.ibm.com/patterns/deploy-microprofile-java-microservices-on-kubernetes/)

### Nodejs

* [Getting started Nodejs and npm](https://nodejs.org/en/docs/guides/getting-started-guide/)

### Angular

One of the repository includes an Angular app, so if you want to be familiar with Angular here are two good articles:
* [Angular tutorial](https://angular.io/tutorial) - This is for the user interface but this is more optional.
* [Applying a test driven practice for angular application](https://github.com/ibm-cloud-architecture/refarch-caseportal-app/blob/master/docs/tdd.md)

### Python

The integration tests, and 
* [Getting started in Python](https://www.python.org/about/gettingstarted/)


### Kubernetes, docker

* As we can use docker compose to control the dependencies between microservices and run all the solution as docker containers, it is important to read the [Docker compose - getting started](https://docs.docker.com/compose/gettingstarted/) article. 
* [Docker getting started](https://docs.docker.com/get-started/)
* [Kubernetes](https://kubernetes.io/docs/tutorials/kubernetes-basics/) and IBM developer [learning path for Kubernetes](https://developer.ibm.com/series/kubernetes-learning-path/) and the Garage course [Kubernetes 101](https://www.ibm.com/cloud/garage/content/course/kubernetes-101/0).
* [Use the "Develop a Kubernetes app with Helm" toolchain on IBM Cloud](https://www.ibm.com/cloud/garage/tutorials/use-develop-kubernetes-app-with-helm-toolchain)
* [Understand docker networking](https://docs.docker.com/network/) as we use docker compose to run the reference implementation locally. 
* [Knative introduction](https://developer.ibm.com/articles/knative-what-is-it-why-you-should-care/)
* [How to deploy, manage, and secure your container-based workloads on IKS](https://www.ibm.com/blogs/bluemix/2017/05/kubernetes-and-bluemix-container-based-workloads-part1/) and [part 2](https://www.ibm.com/blogs/bluemix/2017/05/kubernetes-and-bluemix-container-based-workloads-part2/)

--- 

## Event Driven Specifics

Now the development of event driven solution involves specific technologies and practices. The following links should be studied in the proposed order:

* [Why Event Driven Architecture now?](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture)

### Lab 1: Understand EDA fundamentals (4 hours)

The following content is for architects, and developers who want to understand the technologies and capabilities of an event driven architecture.

* Understand the [Key EDA concepts](./concepts/README.md) like events, event streams, events and messages differences... 
* Be confortable with the [EDA reference architecture with event backbone, microservices and real time analytics](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/reference-architecture).
* Which is extended [with machine learning and AI integrated with real time analytics reference architecture](https://www.ibm.com/cloud/garage/architectures/eventDrivenExtendedArchitecture), integrating machine learning workbench and event sourcing as data source, and real time analytics for deployment.
* Review what can push events with the [Event sources - as event producers article](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-event-sources).
* Read the concept of [Event backbone where Kafka is the main implementation](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-event-backbone). 
* As kafka is the event backbone, review its [key concepts and building blocks](./kafka/readme.md) and then review how to support [High availability and disaster recovery with IBM Event Streams or Kafka Architecture Considerations](./kafka/arch.md).
* What to know about [data replication using kafka and Change Data Capture](https://ibm-cloud-architecture.github.io/refarch-data-ai-analytics/preparation/data-replication/).


### Lab 2: Review Event driven microservice development (3 hours read)

* Read [Event driven design patterns for microservice](./evt-microservices/ED-patterns.md) with the Command Query Responsability Segregation, event sourcing and saga patterns. 
* Read how to [process continuous streaming events](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-event-streams)
* [Event-driven cloud-native applications](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture/event-driven-cloud-native-apps)
* [Getting started with Apache Kafka](https://kafka.apache.org/quickstart) and [the Confluent blog for getting started with Kafka](https://www.confluent.io/blog/apache-kafka-getting-started/)
* Kafka is the core component of  [IBM Cloud Event Streams](https://cloud.ibm.com/docs/services/EventStreams?topic=eventstreams-getting_started#getting_started) running on-premise or on Public cloud.
* The Cloud Private [IBM Event Streams](https://www.ibm.com/cloud/event-streams) product running on  private cloud
* Read introduction to [act on events with IBM Cloud Functions](./evt-action/README.md)


### Lab3: Methodology (2 hours read)

This lab persents how to start an event driven solution implementation. 

* The adopted approach in the industry is the [Event storming methodology](https://ibm-cloud-architecture.github.io/refarch-eda/methodology/readme/) which we are extending with event insight practices to identify potential real time analytics use cases.
* So we use the event storming for the Refrigerator container application. [This article](https://ibm-cloud-architecture.github.io/refarch-kc/analysis/readme/) presents the outcome of this work.
* Once the event storming deliver events, commands, aggregates we can start doing some [Domain design driven](./methodology/ddd.md) and apply it to the use case.

--- 

## Hands-on labs

As next steps beyond getting started and reading our technical point of view, you can try our hands-on  solution implementation and deployment. The ["Reefer container shipment solution"](https://ibm-cloud-architecture.github.io/refarch-kc) is quite complex and includes different components. You do not need to do all, but you should get a good understanding of all those component implementation as most of the code and approach is reusable for your future implementation.

!!! note
        At the end of this training you should have the following solution up and running (See detailed description [here](https://ibm-cloud-architecture.github.io/refarch-kc/design/architecture/#components-view)):

    ![](kc-mvp-components.png)

You can run the solution locally, on IBM Cloud Private, on IBM Kubernetes Services.

### Understand the event storming analysis and derived design

For those who are interested by how to apply the event stormind and domain driven design methodology,  you can review:

* [The solution introduction](https://ibm-cloud-architecture.github.io/refarch-kc/introduction) to get a sense of the goals of this application. (7 minutes read)
* followed by the [event storming analysis report](https://ibm-cloud-architecture.github.io/refarch-kc/analysis/readme/) (30 minutes read).
* and [the derived design](https://ibm-cloud-architecture.github.io/refarch-kc/design/readme/) from this analysis. (15 minutes reading)

### Lab 4: Prepare a local environment

As the goal of those labs is not to redo IKS service creation, or installing products training, we can support to run all the components on the same computer. To do so, you have two options: running with docker compose, or running within Minikube.

First be sure to have cloned the solution implementation repository using git command: 
```
git clone https://github.com/ibm-cloud-architecture/refarch-kc/
```

Open a terminal window and go to the `refarch-kc` folder.

Use the command:
```
./script/clone.sh
``` 

to get all the solution repositories. You should have at least the following repositories:

```
refarch-kc-container-ms
refarch-kc-order-ms
refarch-kc-ui
refarch-kc
refarch-kc-ms
refarch-kc-streams
```

Then do one of the following choice:

1. To run the solution with a local Kafka / zookeeper backbone using docker compose, in [less than 3 minutes with the steps describe in this note](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/local/#start-kafka-and-zookeeper).
1. Or use [Minikube](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/minikube/#pre-requisites) to get kafka, zookeeper and poastgreSQl up and running on kubernetes

### Build and run the solution

!!! goals
    Build and run the solution so you can understand the Java-maven, Nodejs build process with docker stage build.

* [Build and deploy the solution locally using docker compose](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/local/#build-the-solution)
* [Or build and deploy the solution locally using Minikube](https://ibm-cloud-architecture.github.io/refarch-kc/deployments/minikube/#deploy-each-component-of-the-solution)
* [Execute the integration tests](https://ibm-cloud-architecture.github.io/refarch-kc/itg-tests/) to validate the solution end to end.
* [Optional] [Execute the demonstration script](https://ibm-cloud-architecture.github.io/refarch-kc/demo/readme/)

### Review event driven patterns

* Review the [Event sourcing explanations](https://ibm-cloud-architecture.github.io/refarch-eda/evt-microservices/ED-patterns/#event-sourcing)
* Review the [CQRS pattern](https://ibm-cloud-architecture.github.io/refarch-eda/evt-microservices/ED-patterns/#command-query-responsibility-segregation-cqrs-pattern) and the implementation in the [order microservice]().

* Review the CQRS code in the [order management microservice implementation]()

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


## Slack channel

Contact us on '#eda-ac` channel under the [ibmcase.slack.com](http://ibmcase.slack.com) workspace.





* [IBM Event Streams - stream analytics app](https://developer.ibm.com/streamsdev/docs/detect-events-with-streams/) Event detection on continuous feed using Streaming Analytics in IBM Cloud. 
* [Kafka monitoring](./kafka/monitoring.md)
* [Kafka Python API](https://github.com/confluentinc/confluent-kafka-python) and some examples in our [integration tests project](https://ibm-cloud-architecture.github.io/refarch-kc/itg-tests/)
* [Kafka Nodejs API used in the voyage microservice](https://ibm-cloud-architecture.github.io/refarch-kc-ms/voyagems/)
