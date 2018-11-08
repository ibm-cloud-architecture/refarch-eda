
#!/bin/bash
set p = $(echo $PWD | awk -v h="scripts" '$0 ~h')
if [[ $PWD = */scripts ]]; then
 cd ..
fi

# Set global variables here:
nspace=greencompute
volName=zookeeper-pv
svcName=zookeeper-svc

echo "Removing zookeeper..."

echo "... verify deployment / statefulset"
zk=$(kubectl get deployment --namespace $nspace | grep zookeeper | awk '{print $1}')
if [ ! -z "$zk" ]
then
    echo "delete zookeeper deployment"
    kubectl delete deployment  gc-zookeeper  --namespace $nspace
else
  zk=$(kubectl get statefulset --namespace $nspace | grep zookeeper | awk '{print $1}')
  if [ ! -z "$zk" ]
  then
    echo "delete zookeeper statefulset"
    kubectl delete statefulset  gc-zookeeper  --namespace $nspace
  fi
fi

echo "... verify volumes"
zkpv=$(kubectl get pv | grep $volName | awk '{print $1}')
if [ ! -z "$zkpv" ]
then
  echo "remove persistence volume for zookeeper"
  kubectl delete pv gc-zookeeper-pv
fi

echo "... verify services"
zksv=$(kubectl get svc --namespace $nspace | grep $svcName | awk '{print $1}')
if [ ! -z "$zksv" ]
then
  echo "remove service for zookeeper"
  kubectl delete svc gc-client-zookeeper-svc  --namespace $nspace
  kubectl delete svc gc-srv-zookeeper-svc  --namespace $nspace
fi

echo "... removing done"
kubectl get pods --namespace $nspace
