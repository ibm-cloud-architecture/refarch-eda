# Strimzi Kafka deployment on Openshift or Kubernetes

[Strimzi](https://strimzi.io/) uses the Cluster Operator to deploy and manage Kafka (including Zookeeper) and Kafka Connect clusters. When the Cluster Operator is up, it starts to watch for certain OpenShift or Kubernetes resources containing the desired Kafka or Kafka Connect cluster configuration.

## Create a namespace or openshift project

```shell
kubectl create namespace kafka-strimzi

oc create project kafka-strimzi
```

## Download the strimzi artefacts

For the last [release github](https://github.com/strimzi/strimzi-kafka-operator/releases). Then modify the Role binding yaml files with the namespace set in previous step.

```shell
sed -i '' 's/namespace: .*/namespace: kafka-strimzi/' install/cluster-operator/*RoleBinding*.yaml
```

## Define Custom Resource Definition for kafka

```shell
oc apply -f install/cluster-operator/ -n jb-kafka-strimzi
```

This should create the following resources:

| Names | Resource | Command |
| :---: | :---: | :---: |
| strimzi-cluster-operator | Service account | oc get sa |
| strimzi-cluster-operator-entity-operator-delegation, strimzi-cluster-operator, strimzi-cluster-operator-topic-operator-delegation | Role binding | oc get rolebinding |
| strimzi-cluster-operator-global, strimzi-cluster-operator-namespaced, strimzi-entity-operator, strimzi-kafka-broker, strimzi-topic-operator | Cluster Role | oc get clusterrole |
| strimzi-cluster-operator, strimzi-cluster-operator-kafka-broker-delegation | Cluster Role Binding | oc get clusterrolebinding |
| kafkabridges, kafkaconnectors, kafkaconnects, kafkamirrormaker2s kafka, kafkatopics, kafkausers | Custom Resource Definition | oc get customresourcedefinition |

## Deploy Kafka cluster

Change the name of the cluster in one the yaml in the `examples/kafka` folder.

Using non presistence:

```shell
oc apply -f examples/kafka/kafka-ephemeral.yaml -n jb-kafka-strimzi
oc get kafka
# NAME         DESIRED KAFKA REPLICAS   DESIRED ZK REPLICAS
# my-cluster   3                        3
# Or
kubectl  apply -f examples/kafka/kafka-ephemeral.yaml -n jb-kafka-strimzi
```

When looking at the pods running we can see the three kafka and zookeeper nodes, but also an entity operator pod.

Using persistence:

```shell
oc apply -f examples/kafka/kafka-persistent.yaml -n jb-kafka-strimzi
```

## Topic Operator

The role of the `Topic Operator` is to keep a set of KafkaTopic OpenShift or Kubernetes resources describing Kafka topics in-sync with corresponding Kafka topics.

### Deploy the operator

```shell
oc apply -f install/topic-operator/ -n jb-kafka-strimzi
```

This will add the following:

| Names | Resource | Command |
| :---: | :---: | :---: |
| strimzi-topic-operator | Service account | oc get sa |
| strimzi-topic-operator| Role binding | oc get rolebinding |
| kafkatopics | Custom Resource Definition | oc get customresourcedefinition |

### Create a topic

Edit a yaml file like the following:

```yaml
apiVersion: kafka.strimzi.io/v1beta1
kind: KafkaTopic
metadata:
  name: test
  labels:
    strimzi.io/cluster: my-cluster
spec:
  partitions: 1
  replicas: 3
  config:
    retention.ms: 7200000
    segment.bytes: 1073741824
```

```shell
oc apply -f test.yaml -n jb-kafka-strimzi

oc get kafkatopics
```

This creates a topic `test` in your kafka cluster.

## Test with producer and consumer pods

Use kafka-consumer and producer tools from Kafka distribution. Verify within Dockerhub under the Strimzi account to get the lastest image tag (below we use -2.4.0 tag).

```shell
# Start a consumer on test topic

oc run kafka-consumer -ti --image=strimzi/kafka:latest-kafka-2.4.0 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server my-cluster-kafka-bootstrap:9092 --topic test --from-beginning
# Start a text producer
oc run kafka-producer -ti --image=strimzi/kafka:latest-kafka-2.4.0  --rm=true --restart=Never -- bin/kafka-console-producer.sh --broker-list my-cluster-kafka-bootstrap:9092 --topic test
# enter text
```

If you want to use the strimzi kafka docker image to run the above scripts locally but remotely connect to a kafka cluster you need multiple things to happen:

* Be sure the kafka yaml file include the external route stamza:

```yaml
spec:
  kafka:
    version: 2.4.0
    replicas: 3
    listeners:
      plain: {}
      tls: {}
      external:
        type: route
```

* Get the host ip address from the Route resource

```shell
oc get routes my-cluster-kafka-bootstrap -o=jsonpath='{.status.ingress[0].host}{"\n"}'
```

* Get the TLS certificate from the broker

```shell
oc get secrets
oc extract secret/my-cluster-cluster-ca-cert --keys=ca.crt --to=- > ca.crt
# transform it fo java truststore
keytool -import -trustcacerts -alias root -file ca.crt -keystore truststore.jks -storepass password -noprompt
```

* Start the docker container by mounting the local folder with the truststore.jks to the `/home`

```shell
docker run -ti -v $(pwd):/home strimzi/kafka:latest-kafka-2.4.0  bash
# inside the container uses the consumer tool
bash-4.2$ cd /opt/kafka/bin
bash-4.2$ ./kafka-console-consumer.sh --bootstrap-server  my-cluster-kafka-bootstrap-jb-kafka-strimzi.gse-eda-demos-fa9ee67c9ab6a7791435450358e564cc-0001.us-east.containers.appdomain.cloud:443 --consumer-property security.protocol=SSL --consumer-property ssl.truststore.password=password --consumer-property ssl.truststore.location=/home/truststore.jks --topic test --from-beginning
```

* For a producer the approach is the same but suing the producer properties:

```
./kafka-console-producer.sh --broker-list  my-cluster-kafka-bootstrap-jb-kafka-strimzi.gse-eda-demos-fa9ee67c9ab6a7791435450358e564cc-0001.us-east.containers.appdomain.cloud:443 --producer-property security.protocol=SSL --producer-property ssl.truststore.password=password --producer-property ssl.truststore.location=/home/truststore.jks --topic test   
```
