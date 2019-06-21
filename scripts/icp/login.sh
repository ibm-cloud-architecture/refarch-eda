#
# UPDATE VARIABLES TO MATCH THE ENVIRONMENT
#

# Define ICP Cluster name
CLUSTER_NAME=mycluster.icp

echo 'Logging onto IBM Cloud Private CLI'
echo 
cloudctl login -a https://$CLUSTER_NAME:8443 --skip-ssl-validation

echo 'Logging onto Docker Registry'
echo 
docker login $CLUSTER_NAME:8500

echo 'Initializing Helm'
echo
helmICP init --client-only
helm version