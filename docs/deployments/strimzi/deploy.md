# Strimzi Kafka deployment on Minikube

## Create a namespace

```
kubectl create namespace kafka
```

## Apply Strimzi installation file

```
curl -L https://github.com/strimzi/strimzi-kafka-operator/releases/download/0.12.0/strimzi-cluster-operator-0.12.0.yaml \
  | sed 's/namespace: .*/namespace: kafka/' \
  | kubectl -n kafka apply -f -
```

## Provision Kafka cluster

```
kubectl apply -f https://raw.githubusercontent.com/strimzi/strimzi-kafka-operator/0.12.0/examples/kafka/kafka-persistent-single.yaml -n kafka
```

## Create a topic

```
kubectl apply -f test.yaml -n kafka
```

This creates a topic `test` in your kafka cluster.
