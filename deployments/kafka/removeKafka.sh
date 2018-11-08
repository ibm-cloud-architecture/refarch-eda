
#!/bin/bash
set p = $(echo $PWD | awk -v h="scripts" '$0 ~h')
if [[ $PWD = */scripts ]]; then
 cd ..
fi

# Set global variables here:
nspace=greencompute
svcName=gc-kafka-svc
volName=gc-kafka-pv

echo "Removing kafka..."

echo "... verify statefulset"
zk=$(kubectl get statefulset --namespace $nspace | grep kafka | awk '{print $1}')
if [ ! -z "$zk" ]
then
    echo "delete kafka statefulset"
    kubectl delete statefulset  gc-kafka  --namespace $nspace
fi

echo "... verify volumes"
zkpv=$(kubectl get pv | grep $volName | awk '{print $1}')
if [ ! -z "$zkpv" ]
then
  echo "remove persistence volume for kafka"
  kubectl delete pv $volName
fi

echo "... verify services"
zksv=$(kubectl get svc --namespace $nspace | grep $svcName | awk '{print $1}')
if [ ! -z "$zksv" ]
then
  echo "remove service for kafka"
  kubectl delete svc gc-kafka-hl-svc  --namespace $nspace
  kubectl delete svc $svcName  --namespace $nspace
fi

echo "... removing done"
kubectl get pods --namespace $nspace
