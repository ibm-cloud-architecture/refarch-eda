# Strimzi Kafka deployment on Openshift or Kubernetes

Strimzi uses the Cluster Operator to deploy and manage Kafka (including Zookeeper) and Kafka Connect clusters. When the Cluster Operator is up, it starts to watch for certain OpenShift or Kubernetes resources containing the desired Kafka or Kafka Connect cluster configuration.

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

Verify Docker hub strimzi account to get the lastest image tag.

```shell
# Start a consumer on test topic

oc run kafka-consumer -ti --image=strimzi/kafka:latest-kafka-2.4.0 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server my-cluster-kafka-bootstrap:9092 --topic test --from-beginning
# Start a text producer
oc run kafka-producer -ti --image=strimzi/kafka:latest-kafka-2.4.0 --rm=true --restart=Never -- bin/kafka-console-producer.sh --broker-list my-cluster-kafka-bootstrap:9092 --topic test
# enter text
```
