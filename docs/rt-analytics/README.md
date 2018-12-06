# Event stream Processing

## Streaming Analytics ( Real-time analytics )
Within the Event driven architecture the streaming analytics component provides low latency analytical processing of continuous event streams. Required capabilities are:

* Continuous event ingestion and analytics processing
* Processing across multiple event streams
* Low latency processing, data does not have to be stored
* Processing of high-volume streams of data
* Continuous Query and Analysis of the feed
* Correlation across events and streams
* Windowing and stateful processing
* Query and analysis of stored data
* Development and execution of Data pipelines
* Development and execution of Analytics pipelines
* Scoring of Machine Learning models in line in the real time event stream processing

### Streaming Application patterns

Streaming applications are written as multi step flows across the following capabilities

* **Ingest** many sources of events.
* **Prepare** data transformation, filtering, correlate, aggregate on some metrics and leverage other data sources for data enrichment.
* **Detect and Predict** event patterns using scoring, classification
* **Decide** by applying business rules and business logic
* **Act** by directly executing an action,  or in event driven systems publishing an event notification or command.

![](rt-analytics-app-pattern.png)

Streaming applications are written for and deployed to the Streaming Analytics run time.

### Streaming Analytics engines ( runtimes )

In operational terms streaming analytics engines must receive and analyze arriving data continuously:

* The Feed Never Ends
  - The collection is unbounded
  - Not a request response set based model

* The Firehose Doesn’t Stop
  - Keep drinking and keep up
  -   - Processing rate >= Feed rate
  - Must be Resilient and self-healing

These specialized demands and concerns, which are different from many other information processing environments, have lead to highly optimized run times/engines for stateful, parallel processing of analytical workloads across multiple event streams.


### Application programing languages and standards
Across the industry there have been few standards for event stream applications and languages.  Typically streaming engines have provided language specific programming models tied to a specific platform.

Commonly seen languages include
* Python - support for working with data
* Java -  as the pervasive application development language

Other more platform specific languages have emerged when ultimate real time processing performance is required.

More recently Google initiated the Apache Beam project https://beam.apache.org/ to provide a unified programming model for streaming analytics applications.

With Beam there is a higher level unified programming model providing standard way of writing streaming analytics applications in a number of supported languages
* Java
* Python
* Go
* SQL
Streaming Analytics engines typically support this unified programming model through a beam runner which takes the code and deploys as executable code for the specific engine.

The latest details of supporting engines and the capabilities they support can be found here
https://beam.apache.org/documentation/runners/capability-matrix/
but leading ones include Google Cloud DataFlow, Apache Flink, Apache Spark, Apache Apex,  and IBM Streams.


### Support for real time analytics and decision making

The streaming Analytics engine should provide capabilities for the commonly seen real time event stream analytics activities and also enable real-time decision making based on observations across the processed event streams.

This should include:

* GeoSpatial
  - Location based analytics
  - Geofencing & map matching
  - Spatio-temporal hangout detection
* TimeSeries Analysis
  - Timestamped data analysis
  - Anomaly detection & forecasting
* Text Analytics
  - NLP & NLU
  - Sentiment analysis & entity extraction
* Video and Audio
  - Speech to Text conversion
  - Image recognition
* Rules
  - Decisions described as business logic
* CEP
  - Temporal pattern detection
* Entity Analytics
  - Relationships between entities
  - Probabilistic matching



## Products
## IBM Streaming Analytics

## Decision Insights
Decision insight is a stateful operator to manage business decision on enriched event linked to business context and business entities. This is the cornerstone to apply business logic and best action using time related business rules.
[See this note too](../dsi/README.md)

IBM [Operational Decision Manager Product documentation](https://www.ibm.com/support/knowledgecenter/en/SSQP76_8.9.1/com.ibm.odm.itoa.overview/topics/con_what_is_i2a.html


UNDER construction!

![](../under-construction.png)
