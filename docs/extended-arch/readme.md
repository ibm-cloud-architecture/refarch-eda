# Extended Architecture

<img src="../hl-arch-extended.png" width="1024px">

## Integration with Analytics/Machine Learning

The extended architecture  extends the basic EDA reference architecture with concepts showing how Data Science , Artificial Intelligence and Machine Learning can be incorporated into an EDA solution.

The starting point for data scientists to be able to derive machine learning models or analyze data for trends and behaviors is the existence of the data in form that they can consume it. For real time intelligent solutions, data scientists typical inspect event histories and decision/action records from a system and reduce these to some simplified model which will score new event data, as it arrives.

**Step 1 Getting the data for the data scientist: **
With real time event streams, the challenge is  that we are dealing with unbounded data or a continuous flow of events. To make this consumable for the data scientist we need to capture the relevant data and store it so that it can be pulled into the analysis, and model building process as required.

Following our event driven reference architecture the event stream would be a Kafka topic on the Event Backbone.  From here there are two possibilities for making that event data available and consumable to the data scientist:

* The event stream/event log could be accessed directly through Kafka and pulled into the analysis process
* The event stream could be pre processed by the streaming Analytics system and stored for future use in the analysis process. We have a choice of store type which could  be used here, within public  IBM cloud object storage [Cloud Object Store ](https://www.ibm.com/cloud/object-storage) can be used a cost effective historical store.

Both approaches are valid, pre processing through streaming analytics provides opportunity for greater manipulation of the data, storing over time windows etc. However the more interesting distinction is where we may also use a predictive ( ML model )to score arriving  events/stream data in real time. In this case we could use streaming analytics to extract and save the event data for analysis/model building/model training and also to score ( execute ) a derived model in line in the real time against arriving event data.

* The event and decision/action Data is made available in Cloud Object storage for model building through Streaming Analytics

* Models may be developed by tuning and parameter fitting, standard form fitting, classification techniques , and  text analytics methods.

* Increasingly Artificial Intelligence (AI) and Machine Learning (ML) frameworks are use to discover and train useful predictive models  as an alternative to parameterizing existing model types manually.

* These techniques lead to process and data flows where the predictive model is trained offline using event histories from the event and decision/action store possibly augmented with some supervisory outcome labelling – ass illustrated by the path from the Event Backbone and Stream Processing store into Learn/Analyze

* A model trained in this way will include some “scoring” API which can be invoked with fresh event data to generate a model based prediction for future behavior and event properties of that specific context.

* The scoring function is then easily reincorporated into the Streaming analytics processing to generate predictions and insights

The combined techniques of:
1. Event Driven Architecture
2. Identification of predictive insights using Event storming
3. Developing models for these insights using ML
4. Real-time scoring of the insight models using a streaming analytics processing framework

lead us to create real time intelligent applications.

These are scalable easily extensible and adaptable application responding in near real time to new situations. There are easily extended to build out and evolve from an initial MVP because of the loose coupling in the EDA, and Streams process domains.

### Data Scientist Workbench
To complete our extended architecture for  integration with analytics and machine learning, we should consider the toolset and frameworks which the data scientist may used to derive the models.

[Watson Studio](https://www.ibm.com/marketplace/watson-studio) provides  provides tools for data scientists, application developers and subject matter experts to collaboratively and easily work with data to build and train models at scale.


For more information please see [Getting started ](https://dataplatform.cloud.ibm.com/docs/content/getting-started/overview-ws.html) with Watson Studio.

## Legacy Integration

Whilst we may be creating new digital business applications as self contained systems we will also see the need to integrate legacy apps and data bases into the event driven system.

Two ways of coming directly into the event driven architecture come where

1. Where legacy applications are connected with MQ. We  can connect directly from MQ to the Kafka in the event backbone.  See [IBM Event Streams getting started with MQ](https://ibm.github.io/event-streams/connecting/mq/)

2. Where databases support change data capture, it is possible to publish changes as events to Kafka and hence into the event infrastructure.[See the confluent blog for more details](https://www.confluent.io/blog/no-more-silos-how-to-integrate-your-databases-with-apache-kafka-and-cdc)
