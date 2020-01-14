# Kafka Connect

[Kafka connect](https://docs.confluent.io/current/connect/index.html) is an open source component for easily integrate external systems with Kafka. It works with IBM Event Streams and Red Hat AMQ Streams.  
It uses the concepts of source and sink connectors to ingest or deliver data to topics.

![](images/kafka-components.png)

The concepts are detailed in [this note](https://docs.confluent.io/current/connect/concepts.html) or [IBM Event streams](https://ibm.github.io/event-streams/connecting/connectors/) and below is a quick summary:

* **connector** represents a logical job to move data from / to kafka  to / from external systems. A lot of [existing connectors](https://ibm.github.io/event-streams/connectors/) can be reused, or you can implement your own plugin. 
* **workers** are JVM running the connector. For production deployment workers run in cluster. 
* **tasks**: each worker coordinates a set of tasks to copy data. Task states are saved in kafka topics. They can be started, stopped at any time to support resilience, and scalable data pipeline.

![](images/connector-tasks.png)

When a connector is submitted to the cluster, the workers rebalance the full set of connectors in the cluster and their tasks so that each worker has approximately the same amount of work. 

## Characteristics

* Copy vast quantities of data: work at the database level
* Support streaming and batch
* Scale at the organization level, even if it can support a standalone, mono connector appraoch to start small
* Copy data, externalize transformation in other framework
* Run in parallel and distributed servers
* Kafka Connect defines three models: data model, worker model and connector model
* Provide a REST interface to manage and monitor jobs

## Installation

To deploy Kafka connectors on kubernetes, we recommend reading the [IBM  event streams documentation](https://ibm.github.io/event-streams/connecting/setting-up-connectors/) or may be condidering the [Strimzi Kafka connect operator](https://strimzi.io/docs/0.9.0/#kafka-connect-str).
With Event streams on premise deployment the connectors setup can be done via the user interface:

![](images/es-connectors.png)

*Deploying connectors against an IBM Event Streams cluster, you need to have API key with permissions to produce and consume messages for all topics.*

When running in distributed mode, the Connectors need three topics as presented in the `create topics` table [here](https://ibm.github.io/event-streams/connecting/setting-up-connectors/).

As an extendable framework, kafka connect, can have new plugins. To deploy new connector, the docker images needs to be updated and redeployed.

For getting started, a simple docker image can be used to run the connector in standalone.  

## Further Readings

* [Confluent Connector Documentation](https://docs.confluent.io/current/connect/index.html)
* [IBM Event Streams Connectors](https://ibm.github.io/event-streams/connecting/connectors/)