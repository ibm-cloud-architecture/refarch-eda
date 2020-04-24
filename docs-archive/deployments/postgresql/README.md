# Deploying Postgresql to ICP

Update 05/10/2019 - ICP 3.2.1

## Pre-requisites

* Access to an ICP cluster with an up to date catalog 
* Once logged to the admin consoler (something like: https://172.16.254.80:8443) go to the Command Line Tools menu and download the IBM Cloud Private CLI. Rename the downloaded file to cloudctl and move it to a folder in your $PATH (e.g. /usr/local/bin/cloudctl)
* Download the kubeclt CLI that match ICP version. Rename and move the tool to /usr/local/bin/
* Download the kubeclt CLI that match ICP version. Rename and move the tool to /usr/local/bin/
* Get psql to access the postgresql.  

## Steps

* Login to the cluster: 

```
cloudctl login -a https://172.16.254.80:8443 -u admin -p <passwordyoushouldknow>  --skip-ssl-validation
```

When selecting the postgresql tile in the database category of the catalog (https://172.16.254.80:8443/catalog/) the Overview gives some steps to follow, but those are from the product documentation and they may need some update. Below are the specifics we did:

* For the namespace we use `greencompute`, so the secret was something like:

```
$ kubectl create secret generic postgresql-pwd-secret --from-literal='postgresql-password=<>' --namespace greencompute
secret "postgresql-pwd-secret" created
```

* Create a persistence volume. You can use HostPath for development purpose, or if you have a NFS or ceph cluster available adapt the CRD file

```yaml
apiVersion: v1,
kind: PersistentVolume,
metadata:
    name: posgresql-pv,
spec:
    capacity:
      storage: 10Gi
    hostPath:
      path: /bitnami/postgresql,
      type: ""
    accessModes:
      ReadWriteOnce
    persistentVolumeReclaimPolicy: Retain
```

For NFS use the following changes:
```
 spec:
    nfs:
      server:
      path: /bitnami/postgresql
```

* As we deploy postgres in a namespace scope, we need to specify an image policy to authorize access to docker.io repository:

```yaml
apiVersion: securityenforcement.admission.cloud.ibm.com/v1beta1
kind: ImagePolicy
namespace: greencompute
metadata:
  name: postgresql-image-policy
spec:
  repositories:
    - name: docker.io/*
      policy:
        va:
          enabled: false
```

save the file as `securitypolicies.yml` and then run:

```
$ kubectl apply -f securitypolicies.yml -n greencompute
$ kubectl describe ImagePolicy postgresql-image-policy -n greencompute

```

* Use helm to install the release. Here is an example

```
$ export PSWD=$(k get secret postgresql-pwd-secret -n greencompute -o jsonpath="{.data.postgresql-password}"  | base64 --decode; echo)
$ helm install stable/postgresql --name postgresql --namespace greencompute --set postgresqlPassword=$PSWD,postgresqlDatabase=postgres --tls
```

* Access to the database with psql running locally on your computer

In one terminal start a port forwarding using: `kubectl port-forward  postgresql-postgresql-0 5432:5432 &>> /dev/null &`. Now we can connect our local `psql` CLI to the remote server via a command like:

```
$ psql "dbname=postgres host=127.0.0.1 user=postgres port=5432 password=$PSWD"

postgres=# \d containers
 id           | character varying(255)      |           | not null | 
 brand        | character varying(255)      |           |          | 
 capacity     | integer                     |           | not null | 
 created_at   | timestamp without time zone |           | not null | 
 current_city | character varying(255)      |           |          | 
 latitude     | double precision            |           | not null | 
 longitude    | double precision            |           | not null | 
 status       | integer                     |           |          | 
 type         | character varying(255)      |           |          | 
 updated_at   | timestamp without time zone |           | not null | 
```

For more information about the `psql` tool see [this note.](http://postgresguide.com/utilities/psql.html)

## Troubleshooting

>  admission webhook "trust.hooks.securityenforcement.admission.cloud.ibm.com" denied the request: Deny "docker.io/bitnami/postgresql:10.7.0", no matching repositories in ClusterImagePolicy and no ImagePolicies in the "greencompute" namespace

 Be sure to use a ImagePolicy and not a cluster policy when using namespace deployment.

> Error: release postgresql failed: Internal error occurred: admission webhook "trust.hooks.securityenforcement.admission.cloud.ibm.com" denied the request: Deny "docker.io/bitnami/postgresql:10.7.0", no matching repositories in the ImagePolicies

Be sure to authorize docker.io/* in the ImagePolicy.


## More Readings

* [ICP 2.1 Postgresql install recipe:](https://developer.ibm.com/recipes/tutorials/deploy-postgresql-into-ibm-cloud-private/) older recipeusing the configuration user interface in the ICP console.
* [postgresql helm chart explanation and configuration](https://github.com/helm/charts/tree/master/stable/postgresql): a must read.
* [Installing postgresql via Helm](https://medium.com/@nicdoye/installing-postgresql-via-helm-237e026453b1)
* [Reefer container management microservice using Springboot, kafka and postgresql](https://github.com/ibm-cloud-architecture/refarch-kc-container-ms/tree/master/SpringContainerMS)