
#!/bin/bash
if [ $# -eq 1 ]
then
  mode=$1
else
  mode="dev"
fi
# Set global variables here:
nspace=greencompute
svcName=gc-kafka-svc
volName=gc-kafka-pv

echo "Install Kafka..."
echo "... verify volumes"
zkpv=$(kubectl get pv | grep $volName | awk '{print $1}')
if [ -z "$zkpv" ]
then
  echo "create persistence volume for kafka"
  if [ "$mode" = "dev"  ]
  then
    # TODO assess if we need separate volume config
    kubectl apply -f dev/kafka-pv.yaml
  else
    kubectl apply -f prod/kafka-pv.yaml
  fi
fi


echo "... verify services"
kksv=$(kubectl get svc --namespace $nspace | grep gc-kafka-svc | awk '{print $1}')
if [ -z "$kksv" ]
then
  echo "create service for Kafka"
  if [ "$mode" = "dev"  ]
  then
    kubectl apply -f dev/kafka-service.yaml  --namespace $nspace
  else
    kubectl apply -f prod/kafka-service.yaml  --namespace $nspace
  fi
fi
echo "... verify statefuleset"
zk=$(kubectl get statefuleset --namespace $nspace | grep gc-kafka | awk '{print $1}')
if [ -z "$zk" ]
then
  if [ "$mode" = "dev" ]
  then
    echo "create kafka statefuleset for dev"
    kubectl apply -f dev/kafka-statefulset.yaml  --namespace $nspace
  else
    echo "create kafka statefuleset for prod"
    kubectl apply -f prod/kafka-statefulset.yaml  --namespace $nspace
  fi
fi
echo "... installation done"
kubectl get pods --namespace $nspace
