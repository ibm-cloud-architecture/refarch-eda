# Install IBM Event Streams on ICP
*(Tested on June 2018 on ibm-eventstreams-dev helm chart 0.1.2 on ICP 2.1.0.3)*

You can use the `ibm-eventstreams-dev` Helm chart from ICP catalog the instructions can be found [here](https://developer.ibm.com/messaging/event-streams/docs/install-guide/).  
You need to decide if persistence should be enabled for ZooKeeper and Kafka broker. Allocate one PV per broker and ZooKeeper server or use dynamic provisioning but ensure expected volumes are present.

The following parameters were changed from default settings:  

 | Parameter    | Description | Value    |
 | :------------- | :------------- | :------------- |
 | Kafka.autoCreateTopicsEnable     | Enable auto-creation of topics       | true |
 | persistence.enabled | enable persistent storage for the Kafka brokers | true |
 | persistence.useDynamicProvisioning | dynamically create persistent volume claims | true |
 | zookeeper.persistence.enabled | use persistent storage for the ZooKeeper nodes | true |
  | zookeeper.persistence.useDynamicProvisioning | dynamically create persistent volume claims for the ZooKeeper nodes | true |
  | proxy.externalAccessEnabled | allow external access to Kafka from outside the Kubernetes cluster | true |


For the release name take care to do not use a too long name as there is an issue on name length limited to 63 characters.

The screen shots below presents the release deployment results:
![](images/helm-rel01.png)  

The figure above illustrates the following:
* ConfigMap for UI, Kafka proxy, Kafka REST api proxy.
* The three deployment for each major components: UI, REST and controller.

The figure below is for roles, rolebinding and secret as part of the Role Based Access Control.
![](images/helm-rel02.png)

and the services for zookeeper, Kafka and Event Stream REST api and user interface:  
![](images/helm-rel03.png)

The services expose capabilities to external world via nodePort type:
* admin console port 32492 on the k8s proxy IP address
* REST api port 30031
* stream proxy port bootstrap: 31348, broker 0: 32489...

To get access to the Admin console by using the IP address of the master proxy node and the port number of the service, which you can get using the kubectl get service information command like:
```
kubectl get svc -n greencompute "greenKafka-ibm-es-admin-ui-proxy-svc" -o 'jsonpath={.spec.ports[?(@.name=="admin-ui-https")].nodePort}'

kubectl cluster-info | grep "catalog" | awk 'match($0, /([0-9]{1,3}\.){3}[0-9]{1,3}/) { print substr( $0, RSTART, RLENGTH )}'
```

![](images/event-stream-admin.png)

Use the Event Stream Toolbox to download a getting started application. One example of the generated app is in the IBMEventStreams_GreenKafkaTest folder, and a description on how to run it is in the [readme](../../IBMEventStreams_GreenKafkaTest/README.md)

The application runs in Liberty at the URL: http://localhost:9080/GreenKafkaTest/ and delivers a nice simple interface   
![](images/start-home.png)
to test the producer and consumer of text message:

![](images/app-producer.png)  


![](images/app-consumer.png)  

The following project: [asset analytics](https://github.com/ibm-cloud-architecture/refarch-asset-analytics) goes deeper in stream application implementation.

#### Verifying ICP Kafka installation
Once connected to the cluster with kubectl, get the list of pods for the namespace you used to install Kafka / event streams:
```
$ kubectl get pods -n greencompute
NAME                                                              READY     STATUS    RESTARTS   AGE
greenKafka-ibm-eventstreams-Kafka-sts-0                           1/2       Running   32         1d
greenKafka-ibm-eventstreams-Kafka-sts-1                           2/2       Running   10         1d
greenKafka-ibm-eventstreams-Kafka-sts-2                           2/2       Running   18         1d
greenKafka-ibm-eventstreams-proxy-controller-deploy-6f6796jlzcz   1/1       Running   0          1d
greenKafka-ibm-eventstreams-rest-deploy-54b6d4cbb8-hjnxx          3/3       Running   0          1d
greenKafka-ibm-eventstreams-ui-deploy-68d5488cf7-gn48n            3/3       Running   0          1d
greenKafka-ibm-eventstreams-zookeeper-sts-0                       1/1       Running   0          1d
greenKafka-ibm-eventstreams-zookeeper-sts-1                       1/1       Running   0          1d
greenKafka-ibm-eventstreams-zookeeper-sts-2                       1/1       Running   0          1d
```

Select the first pod: greenKafka-ibm-eventstreams-Kafka-sts-0, then execute a bash shell so you can access the Kafka tools:
```
$ kubectl exec greenKafka-ibm-eventstreams-Kafka-sts-0 -itn greencompute -- bash
bash-3.4# cd /opt/Kafka/bin
```
Now you have access to the same tools as above. The most important thing is to get the hostname and port number of the zookeeper server. To do so use the kubectl command:
```
$ kubectl describe pods greenKafka-ibm-eventstreams-zookeeper-sts-0 --namespace greencompute
```
In the long result get the client port ( ZK_CLIENT_PORT: 2181) information and IP address (IP: 192.168.76.235). Using these information, in the bash in the Kafka broker server we can do the following command to get the topics configured.

```
./Kafka-topics.sh --list -zookeeper  192.168.76.235:2181
```


#### Using the Event Stream CLI
If not done before you can install the Event Stream CLI on top of ICP CLI by first downloading it from the Event Stream console and then running this command:
```
bx plugin install ./es-plugin
```
From there is a quick summary of the possible commands:
```
# Connect to the cluster
bx es init

# create a topic  - default is 3 replicas
bx es topic-create streams-plaintext-input
bx es topic-create streams-wordcount-output --replication-factor 1 --partitions 1

# list topics
bx es topics

# delete topic
bx es topic-delete streams-plaintext-input
```
