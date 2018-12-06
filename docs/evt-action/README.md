# Taking An Action with Cloud Functions

IBM Cloud Functions is IBM's "Server-less" compute offering, and while one of the appeals of server-less is in the provision of cost effective compute time,  it provides a simplified *event driven programming model* which is very valuable for event driven solutions.

With cloud functions:

* Developers  write functional logic called *Actions*
* *Actions* can be written in a number of supported languages including Java, Python, Node, Swift, Go, ...
* *Actions* are triggered from events being published to Kafka topics ( The event backbone )
* Functions brings up the required compute to run the Action
* Functions shuts down the compute when the Action is complete
* Functions automatically scales for event volume/velocity

For event driven systems this simple event driven programming model which abstracts the complications of event handling, and load balancing to ensure you have enough subscribing consumers ready to handle the velocity of events through the system, is very powerful.

My developer just write the code which executes the required action/business logic.

## Supporting Products and suggested reading

IBM Cloud Functions is a commercial service offering version of the Apache Openwhisk project  https://openwhisk.apache.org/

IBM Cloud Functions product offering https://www.ibm.com/cloud/functions

Getting Started with Cloud Functions  https://console.bluemix.net/openwhisk/

Using Cloud functions with event trigger in Kafka  https://github.com/IBM/ibm-cloud-functions-message-hub-trigger
