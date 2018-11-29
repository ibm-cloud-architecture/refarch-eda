# Insights Storming Methodology

In this section we will look at how the Event Storming design methodology can be extended to identify potential insights which could be of value to the business.

By identifying potential insight areas up front in the  design phase we can engage data scientists and start the processes of capturing the data, analyisng the business and deriving inital predictive models so that they have the opoprtunity to learn and become effective in our MVP solutions.


## Event Storming recap

Participants in an Event Storming Session identify events, organize them in a time line, group them by triggering action or precursors and look for data which could help actors or policies make the best possible decision about what to do next

* Events are identified (with orange sticky notes) and placed in a timeline providing an overall explanation of what is happening
* Each event is a recod of some action which has happened at a specific past time
* For each event a trigger is identified. Typically it is one of:
  - A (human) actor issuing a command
  - A consequence of a precursor event
  - An external system delivering a triggering event
  - Elapsed time triggering this event
* For precursor event triggers,  Event Storming tells us to identify the “policy” capturing the relationship to the precursor event (using a lilac sticky note)
* For each command trigger,  Event Storming tells us to identify the data which enabled the human actor to decide that action was needed and issue the appropriate command.
* The above gathered information on policies and commands provides a starting point for designing a microservices organized Minimum Viable Product (MVP) solution.

One of the benefits of Event Storming is that modelling a complex ecosystem as a timeline of events and identifying event relationships in the manner above leads to a consistent team wide understanding of the overall system.

With Event Storming we look backwards at each event.  An event is something which is known to have already happened at some point of time in the past. With this perspective when we think about data which can help an actor decide when and how to issue a command, there is an implicit suggestion that this is based on properties of earlier known and captured business events.

## Looking forward with Insights Storming
With Insights Storming we extend the approach to also look forward by considering *what if we could know in advance that this event was going to occur*. How would this change or actions, what would we do now in advance of that event actually happening ?.

Having this forward looking insight combined with the known business data from earlier events can enable both human actors and event triggering policies to make better decisions about how to react to new events as they occur.

## Addidiotnal Insight Storming Steps
By following the event storming process  we will have reached the point of understanding the business events and will have a model for the business.

With insight storming we continue from this point with the same team in the workshop environment and we

* Ask participants what insights or predictions would enable optimal or improved behavior for each of the defined MVP events.
* Or more simply just ask what if we could know in advance that this event was going to occur what action would we take now
* Discuss  possible data sources, or previously generated predictive insights which may already be available or could reasonably be acquired.

Throughout the workshop we shoud encourage probabilistic statements such as at this point “this customer is highly like to … ”, or  “expected congestion at port X suggests…”  when reviewing data which could guide decision or action.

The level of discussion prompted by event storming with the  timeline of easily understandable events provides great context for discussing these sorts of predictive insights and their value.

As an example lets consider our refrigerated container monitoring scneario.  In the Event Storming workshop we may have identified a *container refrigeration unit has failed event* as a trigger event, that is we need to take some action when we see an occurance of it because it has business impact.

The business impact of  the refrigeration unit failing  in the container can be significant in terms of damage to and therefore business loss of the goods.

With Insights Storming we ask ourseleves what if we could know in advance that this event was going to occur, that is that if we knew the refrigeration unit would fail, or more acurately would have a high probability of failure at this time.

Our actions would now be pre emptive, we would trigger the actions which hopefully prevent the failure, or protect the goods before the failure occurs.

So lets consider how this may be achievable.

We know that we can monitor through real time event streams the refrigerated containers temerature, the  operational characteristics of the compressor unit ( power consumption ), and  we have knowlegde of external infulencers, weather, position on ship.

We have the information which would enable us to derive a potential future failure of the unit. Refrigeration units typically show a change in power consumption and defrost cycles when they start to fail, we can derive from enviornment conditions the likely demand on the unit to maintain required temperature.

With Streaming Analaytics and Machine Learning we can process these real time event streams and devlop/refine  a machine learning model which would predict a future failure of the unit  and its likelyhood of dropping the controlled temerature above its threshold.

We could think of this as generating a **"Derived Event"**, which rather than being the factual recording of an event which has happend *The refregeration unit has failed*, is a  forward looking or predictive event *The refrigeration unit is probably going to fail in 2 days time*

If we now step back to our event time line and business model from event storming, we can also bring in these derived events when considering our actions/policies.

Intuitively we would expect business owners and stakeholders to have a good understanding of which probabilistic information is likely to help guide good decision making.

Business modelers and system architects may have good insight into what available or acquirable data or models could be used to generate reliable predictions.

We expect that Insight Storming used in this way will enable business owners, business modelers,  system architects, developers and technologists to reach an agreed understanding of what could be achieved in an MVP solution in their domain using predictive analytics.

## Understanding event stream data

While Event Storming workshops encourage us to focus in on the occurance of a well define Business Event which has happend, when looking to derive insights we need to gain an understanding of a wider set of events:

* We will need to look across and make sense of related  events  over periods of time.
* We will need to understand  real time "technical event streams", for example the power and temerature feeds for the refrigerated units in our example above.

To achieve maximum potential from insights storming, we need to have a feel for the art of the possible. That is we need to understand the potential data and content of event streams that we have or could reasonably get which would enable our data scientists to derive the insights.

Where we are considering or have already adopted an Event Driven Architecture within our technology stack we have great opportunity to put in place the capabilities to capture the event sources and apply appropriate real time analytical processing to genetrate the predictive insights. The IBM Streams and IBM Streaming Analytics on IBM Cloud products are example technologies which delivering this capability.

We discuss this in more detail here Extended Architecture For Machine Learning and Legacy integration.

With Insights Storming we  identify potential insights which would have significant business value early in the design phase of a project. With an eventy driven architecture, connected event streams, and Streaming analytics in place  we have the opportunity to analyse and understand the real time event streams, to get a feel for the achievable insights.
