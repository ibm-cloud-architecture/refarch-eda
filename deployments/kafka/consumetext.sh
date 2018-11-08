#!/bin/bash
if [ -z "$POD" ]
then
  echo "get kafka pod name"
  export POD=$(kubectl get pods | grep gc-kafka | awk '{print $1}')
fi
echo $POD
kubectl exec $POD -- bash -c "/opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server  localhost:32224 --topic test-topic --from-beginning"
