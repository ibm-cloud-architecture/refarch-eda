# Kafka Deployment
We are proposing three deployment approaches:
* Using IBM Event Streams (See [separate note](../eventstreams/README.md))
* Using Kafka on development environment, mostly developer workstation
* Using IBM Cloud private for production

We are defining two types of manifests, one set for development environment and one for production. The manifests and scripts are under each deployment folders.

## Development
For kafka the manifests are in this project under the `deployments/kafka/dev` folder. We are using the google image: `gcr.io/google_samples/k8skafka:v1`.

We tested on MacOS with Docker Edge and Kubernetes.

We are also providing scripts to deploy Kafka:

```shell
$ pwd
> deployments/kafka
$ ./deployKafka.sh
$ kubectl get pods -n greencompute
NAME                            READY     STATUS    RESTARTS   AGE
gc-kafka-0                      1/1       Running   0          2m
gc-zookeeper-57dc5679bb-bh29q   1/1       Running   0          10m
```

### Verifying Kafka is connected to zookeeper
The goal is to connect to the kafka running container and use the scripts inside kafka bin folder:

```shell
# connect to the running container:
$ kubectl exec  -ti gc-kafka-0 /bin/bash -n greencompute
# next is the prompt inside the container:
kafka@gc-kafka-0:/$ cd /opt/kafka/bin
# for example create a topic for testing
kafka@gc-kafka-0:/$./kafka-topics.sh --create  --zookeeper gc-client-zookeeper-svc.greencompute.svc.cluster.local:2181 --replication-factor 1 --partitions 1 --topic text-topic
```

This previous command create a `text-topic` and to verify the configured existing topics use the command (inside the container):

```shell
kafka@gc-kafka-0:/$./kafka-topics.sh --list --zookeeper gc-client-zookeeper-svc.greencompute.svc.cluster.local:2181
```

The URL of the zookeeper matches the hostname defined when deploying zookeeper service (see [installing zookeeper note](../zookeeper/README.md) ):

```shell
kubectl describe svc gc-client-zookeeper-svc
```

### Verifying pub/sub works with text messages

Two scripts exist in the `scripts` folder in this repository. Those scripts are using [kafkacat](https://docs.confluent.io/current/app-development/kafkacat-usage.html) tool from Confluent. You need to add the following in your hostname resolution configuration (DNS or /etc/hosts), matching you IP address of your laptop.

```shell
192.168.1.89 gc-kafka-0.gc-kafka-hl-svc.greencompute.svc.cluster.local
```

Start the consumer in a terminal window

```shell
./scripts/consumetext.sh
```

And start the producer in a second terminal:

```shell
./script/producetext.sh
```

You should see the text:

```shell
try to send some text
to the text-topic
Let see...
% Reached end of topic text-topic [0] at offset 3
```

### Run Kafka in Docker On Linux

If you run on a linux operating system, you can use the [Spotify **Kafka** image](https://hub.docker.com/r/spotify/kafka/) from dockerhub as it includes Zookeeper and **Kafka** in a single image.

It is started in background (-d), named "**Kafka**" and mounting scripts folder to /scripts:

```shell
docker run -d -p 2181:2181 -p 9092:9092 -v `pwd`:/scripts --env ADVERTISED_HOST=`docker-machine ip \`docker-machine active\`` --name kafka --env ADVERTISED_PORT=9092 spotify/kafka
```

Then remote connect to the docker container to open a bash shell:

```shell
docker exec  -ti kafka/bin/bash
```

Create a topic: it uses zookeeper as a backend to persist partition within the topic. In this deployment zookeeper and **Kafka** are running on the localhost inside the container. So port 2181 is the client port for zookeeper.

```shell
cd /opt/kafka/bin
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic mytopic
./kafka-topics.sh --list --zookeeper localhost:2181
```

We have done shell scripts for you to do those command and test your local **Kafka**. The scripts are under `../scripts/kafka`

* createtopic.sh
* listtopic.sh
* sendText.sh  Send a multiple lines message on mytopic topic- open this one in one terminal.
* consumeMessage.sh  Connect to the topic to get messages. and this second in another terminal.

### Considerations

One major requirement to address which impacts kubernetes Kafka Services configuration and Kafka Broker server configuration is to assess remote access need: do we need to have applications not deployed on Kubernetes that should push or consume message to/from topics defined in the Kafka Brokers running in pods. Normally the answer should be yes as all deployments are Hybrid cloud per nature.
As the current client API is doing its own load balancing between brokers we will not be able to use ingress or dynamic node port allocation.

Let explain by starting to review Java code to access brokers. The properties needed to access

```java
public static String BOOTSTRAP_SERVERS = "172.16.40.133:32224,172.16.40.137:32224,172.16.40.135:32224";
Properties properties = new Properties();
properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
kafkaProducer = new KafkaProducer<>(properties);
....

```

To connect to broker  their addresses and port numbers need to be specified. This information should come from external properties file, but the code above is for illustration. The problem is that once deployed in Kubernetes,  Kafka broker runs as pod so have dynamic port numbers if we expose a service using NodePort, and the IP address may change overtime while pod are scheduled to Node. The list of brokers need to be in the format: <host>:<port>, <host>:<port>,<host>:<port>. So host list, without port number will not work, forbidden the use of virtual host name defined with Ingress manifest and managed by Kubernetes ingress proxy. An external load balancer will not work too.
Here is an example of return message when the broker list is not set right: `Connection to node -1 could not be established. Broker may not be available`.

There are two options to support remote connection: implement a proxy, deployed inside the Kubernetes cluster, with 3 or 5 hostnames and port to expose the brokers, or use static NodePort. As of now for development we used NodePort:

```yaml
apiVersion: v1
kind: Service
metadata:
  labels:
    app: gc-kafka
  name: gc-kafka-svc
spec:
  type: NodePort
  ports:
  - name: kafka-port
    port: 32224
    nodePort: 32224
    targetPort: 32224
  selector:
    app: gc-kafka
```

So we use a port number for internal and external communication. In statefulset we use a google created tool to start the kafka server and set parameters to override the default the `conf/server.properties`.

```shell
command:
- "exec kafka-server-start.sh /opt/kafka/config/server.properties --override broker.id=${HOSTNAME##*-} \
  --override listeners=PLAINTEXT://:32224 \
```

When consumer or producer connect to a broker in the list there are some messages exchanged, like getting the cluster ID and the endpoint to be used which corresponds to a virtual DNS name of the exposed service:
`gc-kafka-0.gc-kafka-hl-svc.greencompute.svc.cluster.local`:

```shell
INFO  org.apache.kafka.clients.Metadata - Cluster ID: 4qlnD1e-S8ONpOkIOGE8mg
INFO  o.a.k.c.c.i.AbstractCoordinator - [Consumer clientId=consumer-1, groupId=b6e69280-aa7f-47d2-95f5-f69a8f86b967] Discovered group coordinator gc-kafka-0.gc-kafka-hl-svc.greencompute.svc.cluster.local:32224 (id: 2147483647 rack: null)
```

So the code may not have this entry defined in the DNS. I used /etc/hosts to map it to K8s Proxy IP address. Also the port number return is the one specified in the server configuration, it has to be one Kubernetes and Calico set in the accepted range and exposed on each host of the cluster. With that connection can be established.

#### Verifying deployment

We can use the tools delivered with Kafka by using the very helpful `kubectl exec` command.

* Validate the list of topics from the developer's workstation using the command:

```shell
$ kubectl exec -ti gc-Kafka-0 -- bash -c "kafka-topics.sh --list --zookeeper gc-srv-zookeeper-svc.greencompute.svc.cluster.local:2181 "

or
Kafka-topics.sh --describe --topic text-topic --zookeeper gc-srv-zookeeper-svc.greencompute.svc.cluster.local:2181
```

* start the consumer from the developer's workstation

```shell
kubectl get pods | grep gc-Kafka
kubectl exec gc-Kafka-0 -- bash -c "Kafka-console-consumer.sh --bootstrap-server  localhost:9093 --topic test-topic --from-beginning"
```

the script `deployment/Kafka/consumetext.sh` executes those commands. As we run in the Kafka broker the host is localhost and the port number is the headless service one.

* start a text producer

Using the same approach we can use broker tool:
```shell
$ kubectl exec gc-Kafka-0 -- bash -c "/opt/Kafka/bin/Kafka-console-producer.sh --broker-list localhost:9093 --topic test-topic << EOB
this is a message for you and this one too but this one...
I m not sure
EOB"
```

Next steps... do pub/sub message using remote IP and port from remote server. The code is in [this project]().

### Troubleshooting

For ICP troubleshooting see this centralized [note](https://github.com/ibm-cloud-architecture/refarch-integration/blob/master/docs/icp/troubleshooting.md)

#### Assess the list of Topics

```shell
# remote connect to the Kafka pod and open a bash:
kubectl exec -ti Kafka-786975b994-9m8n2 bash
bash-4.4# ./Kafka-topics.sh  --zookeeper 192.168.1.89:30181 --list
```

Purge a topic with bad message: delete and recreate it

```shell
./Kafka-topics.sh  --zookeeper 192.168.1.89:30181 --delete --topic test-topic
./Kafka-topics.sh  --zookeeper 192.168.1.89:30181 --create --replication-factor 1 --partitions 1 --topic test-topic
```

#### Timeout while sending message to topic

The error message may look like:

```shell
Error when sending message to topic test-topic with key: null, value: 12 bytes with error: (org.apache.Kafka.clients.producer.internals.ErrorLoggingCallback)
org.apache.Kafka.common.errors.TimeoutException: Failed to update metadata after 60000 ms.
```

This can be linked to a lot of different issues, but it is a communication problem. Assess the following:

* port number exposed match the broker's one.
* host name known by the server running the producer or consumer code.
