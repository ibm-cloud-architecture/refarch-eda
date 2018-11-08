#!/bin/bash
if [ -z "$POD" ]
then
  echo "get kafka pod name"
  export POD=$(kubectl get pods | grep gc-kafka | awk '{print $1}')
fi
echo $POD

echo "Get ip address of the node where kafka broker runs"
#ipAdd=$(kubectl describe pod $POD  | grep "Node:" | awk '{print $2}' | cut -d '/' -f1)
echo $ipAdd

echo "Get port exposed node port number for kafka broker"
# portN=$(kubectl describe svc gc-kafka-hl-svc | grep "Port:" | awk '{print $3}' | cut -d '/' -f1)
echo $portN
echo "Send some text"
kubectl exec $POD -- bash -c "/opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:32224 --topic test-topic << EOB
this is a message for you and this one too but this one...
I m not sure
EOB"
