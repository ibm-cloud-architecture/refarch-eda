# Event Storming Methodology
EventStorming is a workshop format for quickly exploring complex business domains by focusing on *domain events* generated in the context of a business process or a business application. It focuses on communication between product owner, domain experts and developers.
A **Domain Event** is something meaningful that happened in the domain.

The event storming method was introduced and publicized by Alberto Brandolini in [Introducing Eventstorming](https://www.eventstorming.com/book/) . This approach has been achieved recognition in the Domain Driven Design (DDD) community as a technique facilitating rapid capture of a solution design and improved team understanding of the design. In this chapter we outline the method and also describe refinements and extesions useful in designs for Event Driven Architecture. This includes the addition of Insight storming to identify and capture value adding predictive insights about possible future events, which can be generates by data analysis, data models, articficial intelligence or machine learning. 

We describe the steps to run an event storming workshop  in general terms.  The output of an actual workshop performing Event storming and Insight Storming on a smple problem - world wide container shipping  is further detailed in [Container shipment analysis example](https://github.com/ibm-cloud-architecture/refarch-kc/tree/master/analysis/readme.md).

## Conducting the Event and Insight Storming Workshop
An Event Storming Workshop may be done after a Design Thinking Workshop in which [Personas](https://www.ibm.com/cloud/garage/content/think/practice_personas/) and [Empathy Maps](https://www.ibm.com/cloud/garage/content/think/practice_empathy_maps/) are developed and business pains and goals are defined. The Event storming will add deeper context on the process and the events. Event storming and Domain Driven Design is a good first step in building an application using the microservices approach that will be follow an Event Driven Architecture.

### Preparation
* Get a room big enough to hold at least 6 to 8 persons and with enough wall space on which to stick big paper sheets: you will need a lot of wall space to define the models.
* Obtain green, orange, blue, and red square sticky notes, black sharpies and blue painters tape
* Do not allow people to open their laptops and try to limit the number of chairs; it is important that the team stays focused and connected and conversation flows well.

### Concepts
Many of the concepts addressed during the event storming workshop are defined in the [Domain Driven Design](https://www.ibm.com/cloud/garage/content/code/domain-driven-design/) approach.
The following diagrams present the elements used during the analysis.  We'll begin with the initial set of concepts used in the process.

 <img src="evt-stm-item1.png" width="700">

Domain events are also named 'business events'.  The first part of the process involves simply identifying the relevant events in a domain and placing them on a timeline.  That often results in questions to be resolved at a later time, or discussions about definitions that need to be recorded in order to make sure everyone agrees on basic domain concepts.

<img src="evt-stm-item2.png" width="700">

A basic timeline of domain events is the initial critical output of the event storming process.  It gives everyone a common understanding of when events take place in relation to each other.  You still need to be able to take this initial level of understanding and then take the next step of moving this toward an implementation.  In making that step, you will need to expand your thinking to encompass the idea of a command, which is the action that kicks off the processing to trigger an event.  As part of understanding the role of the command, you will also want to know who invokes a command (Actors) and what information is needed to allow the command to be executed.  This diagram show how those analysis elements are linked together:

<img src="evt-stm-oneview.png" width="700">

* **Actors** consume data via a user interface and use the UI to interact with the system via commands
* **Commands** are the result of some user decision or policy, and act on relevant data which are part of a Read model in the [CQRS](../readme.md#command-query-responsibility-segregation) pattern.
* **Policies** (represented by Lilac stickies) are reactive logic that take place after an event occurs, and trigger other commands. They always start with the phrase "whenever...". Policies can be a manual step a human would follow (such as a documented procedure or guidance), or they may be automated. When applying the [Agile Business Rule Development methodology](http://abrd.github.io) it will be mapped to a Decision within the [Decision Model Notation](https://www.omg.org/spec/DMN/About-DMN/).
* **External systems** produce events.
* **Data** can be presented to users in a user interface or modified by the system.

Events can be created by commands, by external systems (including IOT devices), they can be triggerred by processing of other events or by some period of elapsed time. When an event is repeated or occurs regularly on a schedule, it is often useful to note that by drawing a clock or calendar icon in the corner of the sticky note.

As the events are identified and sequenced into a time line, you will often find that there are multiple independent subsequences which are not directly coupled to each other and represent different perspectives of the system, but occur in overlapped periods of time.  These parallel event streams can be addressed by putting them into separate **swimlanes**  - delineated using horizontal blue painter's tape.  As the events are organized into a timeline ( possibly with swim lanes) it will be possible to identify **pivotal events** .  Pivotal events indicate major changes in the domain and often form the boundary between one phase of the system and onother.  Pivotal events will typically separate (a [bounded context](https://martinfowler.com/bliki/BoundedContext.html) in DDD terms).  Pivotal events are identified with vertical blue painters tape (crossing all the swimlanes).

An example of a section of a completed event time line with pivotal events and swimlanes is shown below.

<img src="evt-stm-pivotalevt.png" width="700">

### Workshop Execution
The goal of the workshop is to better understand the business problem to address with a future application. But the approach can apply to find solutions to bottlenecks or other issues in existing applications as well. The workshop will help the team understand the big picture of the solution by building a timeline of domain events as they occur during the business process life span.

It's important to avoid documenting process steps; this part of the process is not about capturing an implementation.  Instead, focus on documenting the events. The timeline will represent the high level process as a sequential flow of events.

*  **Step 1: Domain events discovery:**
You begin by writing the domain events in orange sticky note using verbs in past tense. Describe **What's happened**. At first just "storm" the events by having each domain expert generate their individual lists of domain events; you may not need to iniitally place them on the ordered timeline as they write them.  The events must be worded in a way that is relevant to the domain experts. You are explaining what happens in business terms, not what happens inside the implementation of the system.

You don't need to describe all the events in your domain, but it is important to cover the process you are interested in exploring from end to end.  Thus, you need to make sure to identify the start and end events and place them on the timeline at the beginning and end of the wall covered with paper.  The other events identified need to be placed between these two endpoints in the closest approximation that the team can agree to a sequential order.  There will be overlaps at this point - don't worry about that; we'll address this later.

* **Step 2: Tell the story:**
 In this step, you retell the story by talking about how to relate events to particular personas.  A member of the team (often the facilitator, but others can do this as well) will act this out by taking on the perspective on a persona in the domain (such as a "manufacturer" who wants to ship a widget to a customer) and asking which events follow which other events.  Start at the beginning of that persona's interaction and ask "what happens next?"  Pick up and rearrange the events the team has stormed as this happens as you discover events that are duplicates (take those off the board) or that are in the wrong order (move them into the right order).
 
The key here is to add questions or comments (using the red stickies) when some parts are unclear. This is an indication that the team needs to follow up and make clarifications at a later point. Likewise you want to use this time to document assumptions on the definition stickies.  This is also a good time to rephrase events as you proceed through the story.  Sometimes you will need to rephrase an each event description in past tense, or adjust the terms used if needed.
 
In this step you want to focus on the mainline "happy" end-to-end path in order to avoid getting bogged down in details of exceptions and error handling.  That can be added later.

 * **Step 3: Find the Boundaries:**
 The next step of this part of the process is to find the boundaries of your system by looking at the events. There are two types of boundaries we have seen emerge; first are time boundaries.  Often there are specific key "pivotal events" that indicate a change from one aspect of a system to another.  Often this happens at some sort of hand-off from one persona to another, but it can also happen at a change of geographical, legal or other type of boundary. What you will often see is that the terms used on the event stickies changes at these boundaries.  This is an indication that you are seeing a "bounded context" in Domain Driven Design terms.  Highlight pivotal events by putting up blue painters tape vertically behind the event.
 
 The second type of boundary you may find is a subject boundary.  In this case, you often have multiple simultaneous series of events that only come together at a later time.  In this case your indication is also that you see the same terms being used in the event descriptions for a particular series of events, or those events can be "read" from the point of view of a different persona when you are replaying them.  You can delineate these different sets of simultaneous event streams by blue painters tape applied horizontally, dividing the board into different swim lanes.

Below we show an example of a set of ordered domain events with pivotal events and subject swim lanes indicated.  This example comes an example of applying Event Storming to the domain of Container Shipping and is discussed in more detail in [Container Shipment Analysis example](https://github.com/ibm-cloud-architecture/refarch-kc/blob/master/analysis/readme.md) .

 ![](evt-timeline.png)

* **Step 3: Commands:** address why did this event?  The focus is moving to the cause and effect sequence.  A Command is what some persona  in the domain did to create the event.

* **Step 4: Aggregates**: Aggregates represent business concept with local responsibility and grouping events and commands. Most likely aggregates become micro service boundaries.

* **Step 5: Business Context:** it defines terms and concepts with a clear meaning valid in a clear boundary. (The term definition may change outside of the business unit for which this application is developed). The goal here is to defined the boundaries and the term definitions.

* **Step 6: Data:** Data for the user interface so user can make decision are part of the read model. For each command and event we may add data description of the expected attributes and data elements needed to take such decision. Here is a simple example for a `shipment order placed` event created from a `place a shipment order action`.

  <img src="evt-stm-data.png" width="400">

  This first level of data definition will help for assessing the microservice scope and responsibility too.

* **Step 7: Insight:**  In  Event Storming for Event Driven Archticture (EDA) solutions it is helpful to include an additional method step at this point identifying useful predictive analytics insights.

This amounts to asking workshop participants the question: "What data would be helpful at each event trigger to assist the human user or automated event triggering policy make the best possible decision of how and when to act?"

An important motivation driving use of EDA is that it simplifies design and realization of highly responsive systems which react immediately, intelligently, i.e. in a personalized and context aware way, and optimally to new events as they occur. This immediately suggests that predictive analytics and models to generate predictive insights have an important role to play. Predictive analytic insights are effectively probabilistic statements about which future events are likely to occur and what are the likely properties of those events. They are typicaly generated using models crreated by Data scientists or using Atifical Intalligence (AI) or Machine learning (ML). Correlating or joining independently gathered sources of information may also generate important predictive insights or be input to predictive analytic models.   

Business owners and stakeholders in the Event storming workshop will have good intuitions on:
*  which probabilistic insights are likely to lead to better decision making and action? 
   * action could take the form of an immediate response to an event when it occur
   * or it could be proactive behavior to head off an undesirable event 
*  what combined sources of information are likely to help create a model to predict this insight?

  With Event Storming so far we have been looking backwards at each event because an event is something which is known to have already happened. With this perspective when we think about data which can help an actor decide when and how to issue a command, there is an implicit suggestion that this is based on properties of earlier known and captured business events.
  With insights storming we extend the approach to also look forward by considering *what is the probability that a particular event will occur at soem future time and what are its expected property values*. How would this change or actions, what would we do now in advance of that event actually happening ?.

 Insights can be published into a bus and subscribed to by any decision step guidance.  We  think of them as *Derived Events*, which rather than being the factual recording of a past event - *something which has happened* -is a forward looking or predictive view  *these are the probable properties of a future event of this type*
 
 The insight method step amounts to getting workshop participants to identify value adding derived events and data source needed for them which will improve decision making and proactive behavior in the solution using the questions above. 

  Identification of derived events enables integration of analytic models and machine learning into the designed solution. Event and derived event feeds can be processed, filtered, joined, aggregated , modeled and  scored to create valuable predictive insights. 

 New notations specific to the insight step are: 
 * use of a pale blue shape for derived events 
 * use of a parallelogram shape to show when events and derived events are combined to enable deeper insight models and predictions.
  It is important to identify predictive insights early as possible in the development life cycle; adding this step to the event storming workshop is the best opportunity.

The two diagrams below show the results of the insight storming step for the use case of contaner shipment analysis.  The first diagram captured insights and associated linkages for each  refrigerated container, identifying when automated changes to the thermostat settings can be made, when unit maintenance should be scheduled and when the container contents must be considered spoiled.  The second digram captures insights which could trigger recommendations to adjust ship course and speed in response to expected severe weather or congestion at the next port of call. 

  <img src="evt-stm-insight-cntnr.PNG" width="700">
  
  <img src="evt-stm-insight-ship.PNG" width="700">

## Event Storming to User Stories / Epics
When developing using agile methodology, one of the important element of the project management is the user stories or epics construction. The commands and policies can be describe easily as user stories, as commands and decisions are done by actors. The actor could be a system too.
For the data we have to support the CUD operations as user stories, mostly supported by a system actor.

  <img src="evt-stm-userstories.png" width="700">

Events are the result / outcome of a user stories. And can be added as part of the acceptance criteria of the user stories to verify the event really occurs.

## Applying to the container shipment use case
To support one of the implementation solution to validate the Event Driven Architecture, we have developed the event storming and design thinking main artifacts for a [container shipment analysis example](https://github.com/ibm-cloud-architecture/refarch-kc/tree/master/analysis/readme.md) - including monitoring of refrigerated containers .


### Further Readings
* [Introduction to event storming from Alberto Brandolini ](http://ziobrando.blogspot.com/2013/11/introducing-event-storming.html#.VbhQTn-9KK1)
* [Event Storming Guide](https://www.boldare.com/blog/event-storming-guide/)
* [Insight storming Guide](InsightStorming/readme.md)
* [Wikipedia Domain Driven Design](https://en.wikipedia.org/wiki/Domain-driven_design)
* [Eric Evans: "Domain Driven Design - Tacking complexity in the heart of software"](https://www.amazon.com/Domain-Driven-Design-Tackling-Complexity-Software)
* [Patterns related to Domain Driven Design](https://martinfowler.com/tags/domain%20driven%20design.html) by Martin Fowler
