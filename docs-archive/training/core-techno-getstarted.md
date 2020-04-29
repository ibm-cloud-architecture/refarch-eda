# Getting started around the core technologies used in EDA

!!! abstract
    We expect you have some good understanding of the following technologies:

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

From the list above, the following getting started and tutorials can be studied to get a good pre-requisite knowledge for EDA adoption. You can skip those tutorials if you are already confortable on those technologies.

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

The integration tests are done in Python to illustrate how to integrate kafka with python, and also because it is simple to do develop tests with this scripting language: 

* A good [getting started in Python](https://www.python.org/about/gettingstarted/)


### Kubernetes, docker


* In case you do not know it, there is this [Docker getting started tutorial](https://docs.docker.com/get-started/)
* As we can use docker compose to control the dependencies between microservices and run all the solution as docker containers, it is important to read the [Docker compose - getting started](https://docs.docker.com/compose/gettingstarted/) article. 
* [Kubernetes](https://kubernetes.io/docs/tutorials/kubernetes-basics/) and IBM developer [learning path for Kubernetes](https://developer.ibm.com/series/kubernetes-learning-path/) and the Garage course [Kubernetes 101](https://www.ibm.com/cloud/garage/content/course/kubernetes-101/0).
* [Use the "Develop a Kubernetes app with Helm" toolchain on IBM Cloud](https://www.ibm.com/cloud/garage/tutorials/use-develop-kubernetes-app-with-helm-toolchain)
* [Understand docker networking](https://docs.docker.com/network/) as we use docker compose to run the reference implementation locally. 
* This is still optional but as we will adopt Knative, [this introduction](https://developer.ibm.com/articles/knative-what-is-it-why-you-should-care/) is relevant to read.
* [How to deploy, manage, and secure your container-based workloads on IKS](https://www.ibm.com/blogs/bluemix/2017/05/kubernetes-and-bluemix-container-based-workloads-part1/) and [part 2](https://www.ibm.com/blogs/bluemix/2017/05/kubernetes-and-bluemix-container-based-workloads-part2/)

