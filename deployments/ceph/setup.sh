#
# UPDATE VARIABLES TO MATCH THE ENVIRONMENT
#

# Define the location of images 
IMAGE_DIR=/DIRECTORY_HAVING_IMAGES

# Create namespaces
kubectl create namespace rook-ceph 

# Install Rook 
helm repo add rook-beta https://charts.rook.io/beta --tls
helm search rook-ceph --tls
helm install --name rook-ceph-v0.8.3 rook-beta/rook-ceph --version v0.8.3 -f rook-ceph-operator-values.yaml --debug --namespace rook-ceph --tls

# Apply security policies
kubectl apply  -f rook-pod-security-policy.yaml
kubectl apply  -f rook-cluster-role.yaml
kubectl create -f rook-ceph-cluster-role-binding.yaml

echo "Waiting for the pods to come up"
sleep 2m
./status.sh

# Install Ceph cluster 
helm install --name ibm-rook-rbd-cluster-v0.8.3 -f ceph-values.yaml $IMAGE_DIR/ibm-rook-rbd-cluster --debug --namespace rook-ceph --tls