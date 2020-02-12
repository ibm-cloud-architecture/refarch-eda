# Kafka Connect

[Kafka connect](https://kafka.apache.org/documentation/#connect) is an open source component for easily integrate external systems with Kafka. It works with IBM Event Streams and Red Hat AMQ Streams.   It uses the concepts of source and sink connectors to ingest or deliver data to Kafka topics.

![Connect component](images/kafka-components.png)

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

The  Kafka connect framework fits well into a kubernetes deployment. We have different options for that deployment.

We recommend reading the [IBM  event streams documentation](https://ibm.github.io/event-streams/connecting/setting-up-connectors/) for installing Kafka connect with event streams or may be condidering the [Strimzi Kafka connect operator](https://strimzi.io/docs/0.9.0/#kafka-connect-str) to use to deploy to Kubernetes cluster.

With Event streams on premise deployment the connectors setup can be done via the user interface:

![Event Streams connector](images/es-connectors.png)

*Deploying connectors against an IBM Event Streams cluster, you need to have API key with permissions to produce and consume messages for all topics.*

When running in distributed mode, the Connectors need three topics as presented in the `create topics` table [here](https://ibm.github.io/event-streams/connecting/setting-up-connectors/).

As an extendable framework, kafka connect, can have new plugins. To deploy new connector, the docker image defnining the connector needs to be updated and redeployed.

## Getting started with kafka connect standalone mode

For development, a simple kafka docker image can be used to run the connector in standalone mode. We are using a local kafka cluster started with docker-compose as defined in the compose file [here](https://github.com/ibm-cloud-architecture/refarch-kc/blob/master/docker/backbone-compose.yml). 

* The docker network should be `kafkanet`, if not already created do the following

```shell
docker network create kafkanet
```

* Start the kafka broker and zookeeper under the `refarch-kc/docker` folder:

```shell
docker-compose -f backbone-compose.yml up -d
```

* Start a container with kafka code, to run a standalone connector: you need to use a worker configuration and a connector properties files. Those files will be mounted under the /home folder:

```shell
docker run -ti  -rm --name kconnect -v $(pwd):/home --network kafkanet -p 8083:8083 bitnami/kafka:2 bash
```

Need to map the port 8083, to access the REST APIs.

* Inside the container starts the standalone connector:

```shell
cd /opt/bitnami/kafka
./bin/connect-standalone.sh /home/kafka-connect/worker-standalone.properties /home/kafka-connect/file-source.properties
```

The above file configures a file reader to source the `access_log.txt` file to the `clickstream` topic:

```properties
name=local-file-source
connector.class=FileStreamSource
tasks.max=1
file=/home/kafka-connect/access_log.txt
topic=clickstream
```

The standalone connector worker configuration specifies where to connect, and what converters to use:

```properties
bootstrap.servers=kafka1:9092
key.converter=org.apache.kafka.connect.json.JsonConverter
value.converter=org.apache.kafka.connect.json.JsonConverter

# Local storage file for offset data
offset.storage.file.filename=/tmp/connect.offsets
```

The execution trace shows the producer id

```log
INFO [Producer clientId=connector-producer-local-file-source-0] Cluster ID: tj8y0hiZSYWHB9vLHGP1Ew (org.apache.kafka.clients.Metadata:261)
```

To validate the data are well published run another container with the consumer console tool:

```shell
docker run -ti  --name sinktrace --rm  --network kafkanet bitnami/kafka:2 bash -c "
/opt/bitnami/kafka/bin/kafka-console-consumer.sh --bootstrap-server kafka1:9092 --topic clickstream --from-beginning"
```

As the Json converter was used the trace show the message was wrapped into a json document with schema and payload.

```json
{"schema":{"type":"string","optional":false},"payload":"46.166.139.20 - - [01/Dec/2015:23:22:09 +0000] \"POST /xmlrpc.php HTTP/1.0\" 200 370 \"-\" \"Mozilla/4.0 (compatible: MSIE 7.0; Windows NT 6.0)\""}
```

## Connecting to Event Streams remote cluster

The configuration to connect to event streams on IBM Cloud needs to define the broker adviser URLS and the API key.

This API key must provide permission to produce and consume messages for all topics, and also to create topics.

With Event streams on Cloud the [following document](https://cloud.ibm.com/docs/services/EventStreams?topic=eventstreams-kafka_connect) explains what properties to add to the worker and connectors configuration.

```properties
bootstrap.servers=broker-3-qnsdz.kafka.svc01.us-east.eventstreams.cloud.ibm.com:9093,broker-1-qnprt...
security.protocol=SASL_SSL
ssl.protocol=TLSv1.2
sasl.mechanism=PLAIN
sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required username="token" password="98....";

```

With Event Streams as part of the Cloud Pak for integration, the administration console explain the steps to setup connectors, and get configuration.

## Distributed mode

As discribed in the [product documentation](https://ibm.github.io/event-streams/connecting/setting-up-connectors/), with distributed mode at least three topics need to be added:

* **connect-configs**: This topic will store the connector and task configurations.
* **connect-offsets**: This topic is used to store offsets for Kafka Connect.
* **connect-status**: This topic will store status updates of connectors and tasks.

Then the connector configuration needs to specify some other properties (See [kafka documentation](https://kafka.apache.org/documentation/#connectconfigs)):

* group.id to specify the connect cluster name.
* key and value converters.
* replication factors and topic name for the three needed topics, if Kafka connect is able to create topic on the cluster.
* When using Event Streams as kafka cluster, add the `sasl` properties as described in the [product documentation](https://cloud.ibm.com/docs/services/EventStreams?topic=eventstreams-kafka_connect#distributed_worker).

See [this properties file](https://github.com/ibm-cloud-architecture/refarch-kc/blob/master/docker/kafka-connect/distributed-workers.properties) as an example.

To illustrate the Kakfa Connect distributed mode, we will add a source connector from a Mongo DB data source using [this connector](https://www.mongodb.com/kafka-connector). 

![Mongo source ](images/kconnect-mongo.png)

When using as a source, the connector publishes data changes from MongoDB into Kafka topics for streaming to consuming apps. Data is captured via Change Streams within the MongoDB cluster and published into Kafka topics. The installation of a connector is done by adding the jars from the connector into the plugin path as defined in the connector properties. In the case of mongodb kafka connector the manual installation instructions are in [this github](https://github.com/mongodb/mongo-kafka/blob/master/docs/install.md). The download page includes an uber jar.

As we run the kakfa connect as docker container, the approach is to build a new docker image based one of the Kafka image publicly available. For example from the bitnami image. The dockerfile looks like:

```dockerfile
FROM bitnami/kafka:2
COPY mongo-kafka-connect-1.0.0-all.jar  /opt/bitnami/kafka/libs
```

Then we can build the image and start the new connector:

```shell
docker build -t ibmcase/kafkaconnect .
docker run -ti  --rm --name kconnect -v $(pwd):/home --network kafkanet -p 8083:8083 ibmcase/kafkaconnect bash -c ""
```

## Verifying the connectors via the REST api

The documentation about the REST APIs for the distributed connector is in [this site](https://docs.confluent.io/current/connect/references/restapi.html).

For example the http://localhost:8083/connectors is the base URL when running locally.

## Deploy the connector as a service within Openshift cluster

Using [Strimzi](https://strimzi.io/) operator we can deploy Kafka connector distributed cluster. 

To Be done! 

## Further Readings

* [Apache Kafka connect documentation](https://kafka.apache.org/documentation/#connect)
* [Confluent Connector Documentation](https://docs.confluent.io/current/connect/index.html)
* [IBM Event Streams Connectors](https://ibm.github.io/event-streams/connecting/connectors/)
* [MongoDB Connector for Apache Kafka](https://github.com/mongodb/mongo-kafka)