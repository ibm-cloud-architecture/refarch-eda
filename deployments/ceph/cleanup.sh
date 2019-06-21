
# Delete the Helm Chart 
helm delete ibm-rook-rbd-cluster-v0.8.3 --purge 
sleep 1m
helm delete rook-ceph-v0.8.3 --purge 
sleep 1m

# Delete the policies
kubectl delete -f rook-ceph-cluster-role-binding.yaml
kubectl delete -f rook-cluster-role.yaml
kubectl delete -f rook-pod-security-policy.yaml

# Delete namespaces
kubectl delete namespace rook-ceph