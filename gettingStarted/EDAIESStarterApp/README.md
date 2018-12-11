## Starter Application for IBM Event Streams
Starter Application for IBM Event Streams in Java - MicroProfile / Java EE

[![Platform](https://img.shields.io/badge/platform-java-lightgrey.svg?style=flat)](https://www.ibm.com/developerworks/learn/java/)

### Table of Contents
* [Summary](#summary)
* [Requirements](#requirements)
* [Configuration](#configuration)
* [Project contents](#project-contents)
* [Run](#run)

* [Endpoints](#endpoints)

### Summary

The Starter Application for IBM Event Streams in Java - MicroProfile / Java EE provides a demonstration of a Java application running on [WebSphere Liberty](https://developer.ibm.com/wasdev/) that sends and/or receives events using IBM Event Streams.

### Requirements
* [Maven](https://maven.apache.org/install.html) version 3.5.0 or higher
* Java 8: Any compliant JVM should work.
  * [Java 8 JDK from Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
  * [Java 8 JDK from IBM](http://www.ibm.com/developerworks/java/jdk/),
    or [Download a Liberty server package](https://developer.ibm.com/wasdev/downloads/#asset/runtimes-webprofile7-ibm-java)
    that contains the IBM SDK (Windows, Linux)
* Version 1.11.0 of the [kubectl CLI](https://www.ibm.com/support/knowledgecenter/SSBS6K_3.1.0/manage_cluster/cfc_cli.html)
* IBM Cloud Private version of the [Helm CLI](https://www.ibm.com/support/knowledgecenter/SSBS6K_3.1.0/app_center/create_helm_cli.html)

### Configuration
The sample is configured to use the Apache Kafka ® Java clients to communicate with IBM Event streams. It also uses [MicroProfile v1.2](https://microprofile.io/blog/2017/10/eclipse-microprofile-1.2-available) to provide basic application capabilities, like the health endpoint and uses the WebSocket 1.1 specification for frontend to backend communication.

These capabilities are provided through dependencies in the pom.xml file and Liberty features enabled in the server config file found in `src/main/liberty/config/server.xml`.

### Project contents

##### application.kafka package

The `src/main/java/application/kafka` folder in the generated sample contains sample Java class(es), providing an example of how to communicate with Kafka:
* `src/main/java/application/kafka/Producer.java`
* `src/main/java/application/kafka/Consumer.java`

The Java classes in the `application.kafka` package are designed to be independent of the specific usecase for Kafka. The `application.kafka` sample code is used by the `application.demo` package and can also be used to understand the elements required to create your own Kafka application.

##### application.demo package

The `src/main/java/application/demo` folder contains the framework for running the sample in a user interface, providing an easy way to view message propagation.

##### Configuring the sample
The sample has a pre-configured context-root and ports. These can be changed by editing the following files:

* The context root is set in the `src/main/webapp/WEB-INF/ibm-web-ext.xml` file
* The ports are set in the pom.xml file


Note: editing these files may cause the health endpoint test to fail if not also updated
### Run

To build and run the sample locally:

`mvn install liberty:run-server`

To build and run the sample in a Docker container:
1. `mvn install`
1. `docker build -t edaiesstarterapp:v1.0.0 .`
1. `docker run -p 9080:9080 edaiesstarterapp:v1.0.0`

The `build.sh` script provides a convenient way to build the Docker image on Linux or macOS. (Use `chmod +x build.sh` to make it executable.)

The sample will be available at `http://localhost:9080/EDAIESStarterApp/`
### Endpoints

The application exposes the following endpoints:
* Health endpoint: `<host>:<port>/<contextRoot>/health`
* Demo producer endpoint: `<host>:<port>/<contextRoot>/produce`
* Demo consumer endpoint: `<host>:<port>/<contextRoot>/consume`

The context root is set in the `src/main/webapp/WEB-INF/ibm-web-ext.xml` file. The ports are set in the pom.xml file.

### Notices

This project was generated using:
* generator-ibm-java v5.10.0
* ibm-java-codegen-common v3.0.1
* generator-ibm-service-enablement v0.7.0
* generator-ibm-cloud-enablement v^0.13.0
* generator-ibm-java-liberty v8.6.1
