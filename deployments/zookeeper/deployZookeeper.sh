
#!/bin/bash
if [ $# -eq 1 ]
then
  mode=$1
else
  mode="dev"
fi
# Set global variables here:
nspace=greencompute
volName=zookeeper-pv
svcName=zookeeper-svc

echo "Install zookeeper..."
echo "... verify volumes"
zkpv=$(kubectl get pv | grep $volName | awk '{print $1}')
if [ -z "$zkpv" ]
then
  echo "create persistence volume for zookeeper"
  if [ "$mode" = "dev"  ]
  then
    # TODO assess if we need separate volume config
    kubectl apply -f dev/zookeeper-pv.yaml
  else
    kubectl apply -f prod/zookeeper-pv.yaml
  fi
fi

echo "... verify services"
zksv=$(kubectl get svc --namespace $nspace | grep $svcName | awk '{print $1}')
if [ -z "$zksv" ]
then
  echo "create service for zookeeper"
  kubectl apply -f zookeeper-service.yaml  --namespace $nspace
fi

echo "... verify deployment / statefulset"
if [ "$mode" = "dev" ]
then
  zk=$(kubectl get deployment --namespace $nspace | grep zookeeper | awk '{print $1}')
  if [ -z "$zk" ]
  then
    echo "create zookeeper deployment for dev"
    kubectl apply -f dev/zookeeper-deployment.yaml  --namespace $nspace
  fi
else
  zk=$(kubectl get statefulset --namespace $nspace | grep zookeeper | awk '{print $1}')
  if [ -z "$zk" ]
  then
    echo "create zookeeper statefulset"
    kubectl apply -f prod/zookeeper-statefulset.yaml  --namespace $nspace
  fi
fi
echo "... installation done"
kubectl get pods --namespace $nspace
