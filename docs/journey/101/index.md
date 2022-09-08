---
title: Learning Journey - getting started (101 content)
description: Learning the Basic of Event Streams, Event Driven Solution
---

<InlineNotification kind="warning">
<strong>Updated 05/28/2022 - Ready for consumption - Open Issue for request for improvement</strong>
</InlineNotification>

This chapter is to get you understanding what is Event-Driven architecture,
what is Kafka, how to consider Messaging as a service as a foundation for
event-driven solution, and getting started on IBM Event Streams and IBM MQ.

## Important concepts around Event-driven solution and Event-driven architecture

First to get an agreement on the [terms and definitions](../../concepts/terms-and-definitions/) used in all 
this body of knowledge content, with a clear definitions for `events`, `event streams`, `event backbone`, `event sources`....

The main up-to-date [reference architecture presentation is in this section](../../introduction/reference-architecture/#event-driven-architecture) with all the component descriptions.

 ![hl-arch-ra](../../introduction/reference-architecture/images/hl-arch-ra.png)


This architecture is built after a lot of customer engagements, projects, and review, and 
it is driven to support the [main technical most common use cases](../../introduction/usecases/#technical-use-cases) 
we observed for EDA adoption so far.

## Understand Kafka technology

You may want to read from the [Kafka] documentation](https://kafka.apache.org/documentation/#intro_nutshell) to understand the main concepts, but we have
also summarized those into [this chapter](../../technology/kafka-overview/) and if you like quick 
video, here is a seven minutes review of what Kafka is:

<iframe width="560" height="315" src="https://www.youtube.com/embed/aj9CDZm0Glc" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

The major building blocks are summarized in this diagram:

![](./images/kafka-components.png)

To learn more about:

* Kafka Cluster see [this 101 content](../../technology/kafka-overview/#kafka-components)
* For Producer introduction [see this section](../../technology/kafka-producers/#understanding-kafka-producers)
* For Consumer introduction [see this section](../../technology/kafka-consumers/)
* For [Kafka Connector framework introduction](../../technology/kafka-connect/)

## So what is IBM Event Streams?

Event Streams is the IBM packaging of Kafka for the Enterprise. It uses an award winner integrated user interface, kubernetes operator based on open source Strimzi, 
schema registry based on OpenSource and connectors. 
The phylosophie is to bring together Open Source leading products to support event streaming solution developments on kubernetes platform. 
Combined with event-end point management, no-code integration and IBM MQ, Event Streams brings Kafka to the enterprise.

It is available as [a Managed Service](https://www.ibm.com/cloud/event-streams)
or as part of Cloud Pak for integration. [See this developer.ibm.com article about Event Streams on Cloud](https://developer.ibm.com/articles/event-streams-fundamentals/?mhsrc=ibmsearch_a&mhq=ibm%20event%20streams) 
for a quick introduction.

The Event Streams product documentation as part of **IBM Cloud Pak for Integration** is [here](https://ibm.github.io/event-streams/about/overview/).

### Runnable demonstrations

* You can install the product using the Operator Hub, IBM Catalog and the OpenShift console. 
[This lab](../../technology/event-streams/es-cp4i/#install-event-streams-using-openshift-console) can help you get an environment up and running quickly.
* You can then run the **Starter Application** as explained in [this tutorial](../../technology/event-streams/es-cp4i/#run-starter-application). 
* You may need to demonstrate how to deploy Event Streams with CLI, and [this lab](../../technology/event-streams/es-cp4i/#install-event-streams-using-clis) will teach you how to leverage our gitops repository
* To go deeper in GitOps adoption and discussion, [this tutorial](../../use-cases/gitops/) is using two public repositories to demonstrate how to do the automatic deployment of event streams
and an event-driven solution.
* Run a simple Kafka Streams and Event Stream demo for [real-time inventory](../../scenarios/realtime-inventory/) using this demonstration scripts and GitOps repository.

### Event streams resource requirements

See the [detailed tables](https://ibm.github.io/event-streams/installing/prerequisites/#helm-resource-requirements) in the product documentation.

### Event Streams environment sizing

A lot of information is available for sizing:

* [Cloud Pak for integration system requirements](https://www.ibm.com/docs/en/cloud-paks/cp-integration/2021.3?topic=installation-system-requirements)
* [Foundational service specifics](https://www.ibm.com/docs/en/cloud-paks/cp-integration/2021.3?topic=requirements-sizing-foundational-services)
* [Event Streams resource requirements](https://ibm.github.io/event-streams/installing/prerequisites/)
* Kafka has a [sizing tool (eventsizer)](https://eventsizer.io/) that may be questionable but some people are using it.

But in general starts small and then increase the number of nodes over time.

Minimum for production is 5 brokers and 3 zookeepers.
 

## Use Cases

We have different level of use cases in this repository: 

The generic use cases which present the main drivers for EDA adoption are [summarized here](../../introduction/usecases/). 

But architects need to search and identify a set of key requirements to be supported by thier own EDA:

* get visibility of the new data created by existing applications in near real-time to get better business insights and propose new business opportunities to the end-users / customers. 
This means adopting data streaming, and intelligent query solutions.
* integrate with existing transactional systems, and continue to do transaction processing while adopting eventual data consistency, characteristics of distributed systems. (Cloud-native serverless or microservices are distributed systems)
* move to asynchronous communication between serverless and microservices, so they become more responsive, resilient, and scalable (characteristics of ‘Reactive applications’).
* get loosely coupled but still understand the API contracts. API being synchronous (RES, SOAP) or async (queue and topic).
* get clear visibility of data governance, with cataloging applications to be able to continuously assess which apps securely access what, with what intent.


We have done some reference implementations to illustrate the major EDA design patterns:

| Scenario                           | Description         | Link        |
| --------------------------------| ------------------  |:----------- |
| Shipping fresh food over sea _(external)_ | The EDA solution implementation using event driven microservices in different language, and demonstrating different design patterns. | [EDA reference implementation solution](https://ibm-cloud-architecture.github.io/refarch-kc/)|
| Vaccine delivery at scale _(external)_ | An EDA and cross cloud pak solution | [Vaccine delivery at scale](https://ibm-cloud-architecture.github.io/vaccine-solution-main/)
| Real time anomaly detection _(external)_ | Develop and apply machine learning predictive model on event streams | [Refrigerator container anomaly detection solution](https://ibm-cloud-architecture.github.io/refarch-reefer-ml/)|

## Why microservices are becoming event-driven and why we should care?

This [article](/advantages/microservice) explains why microservices are becoming event-driven 
and relates to some design patterns and potential microservice code structure.

## Messaging as a service? 

Yes EDA is not just Kafka, IBM MQ and Kafka should be part of any serious EDA deployment.
The main reasons are explained in [this fit for purpose section](../../concepts/fit-to-purpose/#mq-versus-kafka) and in
the [messaging versus eventing presentation](../../concepts/events-versus-messages/).

An introduction to MQ is summarized in [this note](../../technology/mq/) in the 301 learning journey, developers need to get the [MQ developer badge](https://developer.ibm.com/learningpaths/ibm-mq-badge/)
as it cover the basics for developing solution on top of MQ Broker.

## Fit for purpose

We have a [general fit for purpose document](../../concepts/fit-to-purpose/) that can help you reviewing messaging versus eventing,
MQ versus Kafka, but also Kafka Streams versus Apache Flink.


## Getting Started With Event Streams

### With IBM Cloud

The most easy way to start is to create one `Event Streams on IBM Cloud` service instance,
and then connect a starter application. See this [service at this URL](https://cloud.ibm.com/catalog/services/event-streams).

### Running on you laptop

As a **developer**, you may want to start quickly playing with a local Kafka Broker and MQ Broker 
on you own laptop. We have developed such compose files in [this gitops project](https://github.com/ibm-cloud-architecture/eda-gitops-catalog) under
the `RunLocally` folder.

  ```sh
  # Kafka based on strimzi image and MQ container
  docker-compose -f maas-compose.yaml up &
  # Kafka only
  docker-compose -f kafka-compose.yaml up &
  ```
The kafka images are based on Strimzi images, but act as Event Streams one. So any code developed and configured
on this image will run the same when deployed on OpenShift using Event Streams.

### Running on OpenShift

Finally if you have access to an OpenShift Cluster, version 4.7 +, you can deploy 
Event Streams as part of IBM Cloud Pak for Integration very easily using the
OpenShift Admin console.

See this simple [step by step tutorial here](../../technology/event-streams/es-cp4i/) which covers
how to deployment and configuration an Event Streams cluster using the OpenShift Admin
 Console or the `oc CLI` and our manifest from our [EDA gitops catalog repository](https://github.com/ibm-cloud-architecture/eda-gitops-catalog).

### Show and tell 

Once you have deployed a cluster, access the administration console and use the
very good [getting started application](https://ibm.github.io/event-streams/getting-started/generating-starter-app/).

### Automate everything with GitOps 

If you want to adopt a pure GitOps approach we have demonstrated how to use
ArgoCD (OpenShift GitOps) to deploy an Event Streams cluster and maintain it states
in a simple real time inventory demonstration in [this repository](https://github.com/ibm-cloud-architecture/rt-inventory-gitops).   


## Methodology: getting started on a good path

We have adopted the [Event Storming workshop](../../methodology/event-storming) to discover the business process from an event point of view,
and then apply [domain-driven design](../../methodology/domain-driven-design) practices to discover aggregate, business entities, commands, actors and bounded contexts.
Bounded context helps to define microservice and business services scope. Below is a figure to illustrate the DDD elements used during 
those workshops:

![](../../methodology/event-storming/images/evt-stm-oneview.png)

Bounded context map define the solution. Using a lean startup
approach, we focus on defining a minimum viable product, and preselect a set of services to implement.

We have detailed how we apply this methodology for a [Vaccine delivery solution](https://ibm-cloud-architecture.github.io/vaccine-solution-main/) 
in [this article](https://ibm-cloud-architecture.github.io/vaccine-solution-main/design/dtw/) which can help you understand how to use the method for your own project.


## Frequently asked questions

A [separate FAQ document](../../technology/faq) groups the most common questions around Kafka.