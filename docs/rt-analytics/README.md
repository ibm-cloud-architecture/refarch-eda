# Event stream Processing

As we discussed coming into this section the ability to process continuous Event Streams to derive real time insights/intelligence  has become one of the  essential elements of modern event driven solutions.

In this section we will take more detailed look at what this means in terms of required capabilities and the technology choices that are available to provide these as part of the Event Driven Architecture.

## Streaming Analytics ( Real-time analytics )

Streaming analytics provides the capabilities to look into and understand the events flowing through unbounded real time event streams. "Streaming Applications" are developed which process the event flow and allow data and analytical functions to be applied to information in the stream.

Streaming applications are written as multi step flows across the following capabilities
* **Ingest** many sources of events.
* **Prepare** data transformation, filtering, correlate, aggregate on some metrics and leverage other data sources for data enrichment.
* **Detect and Predict** event patterns using scoring, classification
* **Decide** by applying business rules and business logic
* **Act** by directly executing an action,  or in event driven systems publishing an event notification or command.

![](rt-analytics-app-pattern.png)

### Basic Streaming analytics capabilities
To support the real time analytical processing of the unbounded event streams the following capabilities are foundational to the event stream processing component in the Event Driven Architecture

* Continuous event ingestion and analytical processing
* Processing across multiple event streams
* Low latency processing, data does not have to be stored
* Processing of high-volume/velocity  streams of data
* Continuous Query and Analysis of the feed
* Correlation across events and streams
* Windowing and stateful processing
* Query and analysis of stored data
* Development and execution of Data pipelines
* Development and execution of Analytics pipelines
* Scoring of Machine Learning models in line in the real time event stream processing

### Support for real time analytics and decision making

With those foundational capabilities in place, there are frequently seen event stream types and processing capabilities which should be supported. Brining these out as functions which can applied to the stream processing in the Streaming Application code simplifies the problem down and speeds up the development time.

These include:

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

### Application programing languages and standards
Across the industry there have been few standards for event stream applications and languages.  Typically streaming engines have provided language specific programming models tied to a specific platform.

Commonly seen languages includ:
* Python - support for working with data and popularity with data scientists/data engineers
* Java -  as the pervasive application development language

Other platform specific languages have emerged when ultimate real time processing performance is required.

More recently Google initiated the Apache Beam project https://beam.apache.org/ to provide a unified programming model for streaming analytics applications.

With Beam there is a higher level unified programming model providing a standard way of writing streaming analytics applications in a number of supported languages
* Java
* Python
* Go
* SQL

Streaming Analytics engines typically support this unified programming model through a beam runner which takes the code and convert it to platform native executable code for the specific engine.

The latest details of supporting engines and the capabilities they support can be found here
https://beam.apache.org/documentation/runners/capability-matrix/
but leading ones include Google Cloud DataFlow, Apache Flink, Apache Spark, Apache Apex,  and IBM Streams.

### Run time characteristics

In operational terms streaming analytics engines must receive and analyze arriving data continuously:

* The Feed Never Ends
  - The collection is unbounded
  - Not a request response set based model

* The Firehose Doesn’t Stop
  - Keep drinking and keep up
  - Processing rate >= Feed rate
  - Must be Resilient and self-healing

These specialized demands and concerns, which are different from many other information processing environments, have lead to highly optimized run times/engines for stateful, parallel processing of analytical workloads across multiple event streams.


## Products

## Streaming Analytics

The market for streaming analytics products is quite confused with lots of different offering and very few standards to bring them together.  The potential product selection list for the streaming analytics component in the event driven architecture would need to consider:

Top Open Source projects:
* Flink - real time streaming engine, both real time and batch analytics in one tool.
* Spark Streaming - micro batch processing through spark engine.
* Storm - Has not shown enough adoption.
* Kafka Streams - new/emerging API access  for processing event streams in Kafka

Major Cloud Platform Providers support:
* Google Cloud DataFlow – proprietary engine open source streams application language ( Beam )
* Azure Stream Analytics – proprietary engine , SQL interface
* Amazon Kinesis - proprietary AWS

IBM offerings
* IBM Streams/streaming Analytics  (High performing parallel processing engine for real time analytics work loads)
* IBM Event streams ( Kafka based event log/streaming platform)

Evaluation of the various options, highlights
* The proprietary engines from the major providers, Google, MicroSoft, Amazon and IBM Streams continue to provide significant benefits in terms of performance and functionality for real time analysis of high volume realtime event streams.
* Kafka streams provides a convenient programming model for microservices to interact with the event stream data, but doesnt provide the optimized stream processing engine required for high volume real time analytics.

Our decision for the Event Driven Architecture is to include:

* IBM streams as the performant, functionally rich real time event stream processing engine
* Event Streams ( Kafka Streams ), for manipulation of event streams within microservices

IBM streams also supports Apache Beam as the open source Streams Application language,  which would allow portability of streams applications across, Flink, Spark, Google DataFlow...


## Decision Insights
Decision insight is a stateful operator to manage business decision on enriched event linked to business context and business entities. This is the cornerstone to apply business logic and best action using time related business rules.
[See this note too](../dsi/README.md)

IBM [Operational Decision Manager Product documentation](https://www.ibm.com/support/knowledgecenter/en/SSQP76_8.9.1/com.ibm.odm.itoa.overview/topics/con_what_is_i2a.html


UNDER construction!

![](../under-construction.png)
