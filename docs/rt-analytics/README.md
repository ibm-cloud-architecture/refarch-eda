# Real-time analytics
The real-time analytics component supports
* Continuous event ingestion and analytics
* Correlation across events and streams
* Windowing and stateful compute
* Consistent/high-volume streams of data

The application pattern can be represented by the following diagram:
![](docs/rt-analytic-app-pattern.png)

* many sources of event are in the ingestion layer via connectors and event producer code.
* the data preparation involves data transformation, filtering, correlate, aggregate some metrics and data enrichment by leveraging other data sources.
* detect and predict events pattern using scoring, classification
* decide by applying business rules and business logic
* act by triggering process, business services, modifying business entities...


## Products
### IBM Stream Analytics

### Decision Insights
Decision insight is a stateful operator to manage business decision on enriched event linked to business context and business entities. This is the cornerstone to apply business logic and best action using time related business rules.
[See this note too](../dsi/README.md)

IBM [Operational Decision Manager Product documentation](https://www.ibm.com/support/knowledgecenter/en/SSQP76_8.9.1/com.ibm.odm.itoa.overview/topics/con_what_is_i2a.html

  
UNDER construction!

![](../under-construction.png)
