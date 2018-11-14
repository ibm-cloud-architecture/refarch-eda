# Event Storming Methodology
EventStorming is a workshop format for quickly exploring complex business domains by focusing on *domain events* generated in the context of a business process or a business application. It focuses on communication between product owner, domain experts and developers.  
A **Domain Event** is something meaningful happened in the domain.

## Workshop
May be done after a design thinking workshop where persona map and empathy maps are developed and business pains and goals are defined. The Event storming will add deeper context on the process and the events. It will be fit well in deploying application in an Event Driven Architecture.

### Preparation
* Get a room for at least 6 to 8 persons and walls to stick big paper sheets: you need a lot of space on walls to define the models.
* Have green, orange, blue, red squared sticky notes, masking tapes
* Try to limit the chair, it is important the team stays focused, connected and conversation flows well.

The following diagrams present the elements used during the analysis:

![](evt-stm-item1.png)

![](evt-stm-item2.png)

And how those analysis elements are linked together:

![](evt-stm-oneview.png)
* Actors consume data via user interface and use UI to act on the system via commands
*

![](evt-stm-pivotalevt.png)

### Execution
The Goal is to better understand the business problem to address with the future application. But it can apply to search solution to bottleneck in existing application. It starts by the big picture by building a timeline of domain events as they occur during the business process life span.

Avoid to document process step, focus on events. The timeline will represent the high level sequential process.

1. **Step 1: Domain events discovery:**
Name the domain events in orange sticky note using verb in past tense. Describe **What's happened**. At first just "storm" the event, you may not need to place them on the ordered timeline.  
The events are relevant to the domain experts.
It may not be needed to discover all the events, but important to cover the process end to end.
Identify the start and stop events.

1. **Step 2: Tell the story:**
 * Retail the story by talking about relating event to persona
 * Add questions when some parts are unclear
 * Document assumptions
 * Rephrase event with past tense if needed
 * Focus on happy path, the things going on, on regular time  
 * Add pivotal events
 * Add swim lanes  

Here is an example of ordered domain events with pivotal event and swim lanes:  
 ![](evt-timeline.png)

1. **Step 3: Commands:** address the why did event happen. The focus is moving to the cause and effect sequence. Command is what people do in the domain to create event.

1. **Step 4:


## Applying to the container shipment use case
The following set of diagrams support the analysis of a fridge [container shipment]() to support one of the implementation solution to validate the Event Driven Architecture.  

![]()

### Further Readings
* [Introduction](http://ziobrando.blogspot.com/2013/11/introducing-event-storming.html#.VbhQTn-9KK1)
* [Guide](https://www.boldare.com/blog/event-storming-guide/)
