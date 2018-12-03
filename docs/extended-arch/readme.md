# Extended Architecture

<img src="../hl-arch-extended.png" width="1024px">
#
# Legacy Integration

While some Event driven applications will  stand alone, in many cases they will  require integration with existing (legacy ) enteprise applications and data sources.

There can be advanatges in enabling these integrations to be event driven at the earliest point.  Developers could modify code to emit events,  which then become available through the EDA to the event driven application developers.

Less introusive options may be presented where
* Messaging (MQ) solutions are deployed in the legacy environments which can be bridged into the cloud native EDA.
* Change Data Capture capabilities could publish changes as events to the EDA for legacy databases.

## Integration with Analytics/Machine Learning

## Dashboard
 Event based solution needs to present different type of user interface:  operational dashboards to assess the state of the runtime components and business oriented dashboard, also known as Business Activity Monitoring.

There is a need to keep visibility of event paths inside the architecture. Dashboards will be connected to the event backbone and to event store.

[Read more ...](docs/evt-dashboard/README.md)


## Data scientist workbench**:
There are opportunities to have data scientists connecting directly event subscriber from their notebook to do real time data analysis, data cleansing and even train and test model on the data from the event payload. The data can be kept in data store but the new model can be deployed back to the streaming analytics component...

[Read more ...](docs/ml-workbench/README.md)
