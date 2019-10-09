

This section describes how to apply domain driven design with event based application and describe the high level steps which uses output from the event storming session and derives a set of micro services design specifications.

[Event storming](./eventstorming.md) is part of the domain driven design methodology. And domain-driven design was deeply describe in [Eric Evans's "Domain Driven Design: Tackling Complexity in the Heart of Software" book](https://www.amazon.com/Domain-Driven-Design-Tackling-Complexity-Software/dp/0321125215/ref=asc_df_0321125215/?tag=hyprod-20&linkCode=df0&hvadid=312118197030&hvpos=1o1&hvnetw=g&hvrand=3363692763187919455&hvpone=&hvptwo=&hvqmt=&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=9032152&hvtargid=pla-449269547899&psc=1) from 2004. At IBM we also summarized the concepts needed for implementing microservice in [Kyle Brown's article](https://www.ibm.com/cloud/garage/practices/code/domain-driven-design/overview). 

The goals for the design step are: 

* To support highly modular cloud native microservices. 
* To adopt event coupled microservices - facilitating independent modification and evolution of each microservice separately.
* To allow applying event-driven patterns such as event sourcing, CQRS and SAGA to address some of the challenges of microservice: data eventual consitency, transaction cross domain, and complex queries between aggregates managed by different services.

## Starting materials generated during Event Storming and Analysis

We make use of the following materials generated during Event Storming and analysis of the Container Shipment example problem: 

* Event Sequence flow.
* Events – business description.
* Critical events. 
* Aggregates and services:   
    * Users – role based user stories.   
    * Commands.
    * Event linkages.
    * Policies. 
    * Event prediction and probability flows. 

The derivation of these material was described in: [the event storming introduction](eventstorming.md).

Here is an example of starting material illustrating the beginning of the process:

![](images/event-storming-order.png)


## Steps in the design process 

Here we describe in generic terms, each step in the process of deriving event-linked microservice specifications.

---

### Step 1: Assess domain and subdomains

Domain is what an organization does, and it includes the how to perform its operations. It is composed of sub domains. A Core Domain is a part of the business Domain that is of primary importance to the success of the organization, the organization needs to excel at it.

During the event storming analysis, you define the domain and groups a set of subdomains like orders, invoice, customer, ... and external systems. Here is an example of such domain and subdomains:

![](images/domain-subdomains.png)

We have three core subdomains and the rest as support.

---

### Step 2: Defined the potential applications

At the high level, when doing the analysis, you should have some insight decision of the top level application to develop, but it is also important to see list other systems to interact to. A classic system context diagram is a nice tool to represent that. The EDA reference implementation solution includes such [system context diagram](https://ibm-cloud-architecture.github.io/refarch-kc/design/architecture/#system-context).

---

### Step 3: Define the ubiquitous language

This is where the work from the event storming and the relation with the business experts should help. Domain experts use their jargon while technical team members have their own language tuned for discussing the domain in terms of design. The terminology of day-to-day discussions is disconnected from the terminology embedded in the code, so the ubiquitous language helps to allign knowledge with design elements, code and tests. (Think about EJB, JPA entity, all the JEE design jargon versus ShippingOrder, order provisioning, fullfillment... )

The vocabulary of that ubiquitous language includes the class names and prominent operation names. The language includes terms to discuss rules that have been made explicit in the model. Be sure to commit the team to exercising that language relentlessly in all communication within the business and in the code. Use the same language in diagrams, writing, and especially speech.

Play with the model as you talk about the system. Describe scenarios out loud using the elements and interactions of the model, combining concepts in ways allowed by the model. Find easier ways to say what you need to say, and then take those new ideas back down to the diagrams and code.

#### Entities and Value Objects

Entities are part of the ubiquitous language, and represent business concepts that can be uniquely identified by some atributes. They have a life cycle that is important to model.

Value Object also represent things in the domain but without identity, and they are frequently transient, created for an operation and then discarded.

Some time in a certain context a value object could become an entity. As an example, an Address will be most of the time a value object, excepts in a mapping app or in an agenda app.

Below is an example of entities (Customer and Shipping Order) and value objects (delivery history and delivery specification):

![](images/ent-vo.png)


#### Aggregate boundaries

An aggregate is a cluster of associated objects that we treat as a unit for the purpose of data changes. An entity is most likely and aggregate and every things related to it define its boundaries. 
 
#### Bounded Contexts

Within a business context every use of a given domain term, phrase, or sentence, **the Ubiquitous Language** inside the boundary has a specific contextual meaning. So order context is a boundary context and groups order, ordered product type, pickup and shipping addresses, delivery specifications, delivery history. 

#### Repositories

Repository represents the infrastructure service to persist the root aggregate during its full life cycle.
Client applications request objects from the repository using query methods that select objects based on criteria specified by the client, typically the value of certain attributes.

#### Event linked microservices design - structure 

A complete event driven microservices specification (the target of this design step) includes specifications of the following: 

* Event Topics 
    * Used to configure the Event Backbone 
* Event types within each event topic 
* Microservices: 
    * They may be finer grained than aggregates or mapped to aggregate boundaries.
    * They may separate query and command; possibly multiple queries. 
    * They could define demonstration control and serve main User Interface.
    * Reference the related Entities and value objects within each microservice.
    * Define APIs  ( Synchronous or asynchronous) using standards like openAPI. Could be done bottom up from the code, as most of TDD implementation will lead to. 
    * Topics and events Subscribed to.
    * Events published / emitted.  
* List of end to end interactions:
    * List of logic segments per microservice 
* Recovery processing, scaling:
    * We expect this to be highly patterned and template driven not requiring example-specific design. 

--- 

### Step 4: Define modules

* Each aggregate will be implemented as some composition of:   
    (1) a command microservice managing state changes to the entities in this aggregate  
    (2) possibly one or more separate (CQRS) query services providing internal or external API query capabilities   
    (3) additional simulation, predictive analytics or User Interface microservices   
* The command microservice will be built around a collection of active entites for the aggregate, keyed by some primary key.
* The separation of each aggregate into specific component microservices as outlined above, will be a complete list of microservices for the build / sprint. 
* Identify the data collections, and collection organization (keying structure) in each command and query microservice for this build.

---

### Step 5: Limit the context and scope for this particular build / sprint

We assume that we are developing a particular build for a sprint within some agile development approach, deferring additional functions and complexity to later sprints:

* Working from the initial list of aggregates, select which aggregates will be included in this build
* For each aggregate the possible choices are:
    1. to completely skip and workaround the aggregate in this build.
    1. to include a full lifecycle implementation of the aggregate   
    1. to provide a simplified lifecycle implementation - typically a table of entities is initialized at start up, and state changes to existing entities are tracked   
* Determine whether there are simulation services or predictive analytics service to be included in the build 
* Identify the external query APIs and command APIs which this build should support 
* Create entity lifecycle diagrams for entites having a full lifecycle implementation in this build / sprint.

---

### Step 6: Generate microservice interaction diagrams for the build

* The diagram will show API calls initiating state change. They should map the commands discovered during the event storming.
* It shows for each interaction whether this is a synchronous API calls or an asynchronous event interaction via the event backbone.
* The diagram labels each specific event interaction between microservices trigerring a state change.
* Typically queries are synchronous API calls since the caller cannot usefully proceeed until a result is returned.
* From these, we can extract:   
    1. a complete list of event types on each event backbone topic, with information passed on each event type.  
    1. the complete list of “logic segments” for each microservice processing action in response to an API call or initiating event. 

* When, at the next level of detail, the individual fields in each event are specified and typed, the [CloudEvents standard](https://github.com/cloudevents/spec) may be used as a starting point.

--- 

### Step 7: Specify recovery approach in case a microservice fails  

* If a microservice fails it will need to recover its internal state by reloading data from one or more topics, from the latest committed read.  
* In general, command and query microservices will have a standard pattern for doing this.
* Any custom event filtering and service specific logic should be specified.

### Concepts and rationale underlying the design approach

*What is the difference between event information stored in the event backbone and state data stored in the microservices?* 

The event information stored persistently in the event backbone is organized by topic and, within each topic, entirely by event time-of-occurrence. While the state information in a microservice is a list (collection) of all **currently active** entities of the owning aggregate (e.g. all orders, all voyages etc) and the **current** state of each such entity. The entity records are keyed by primary key, like an OrderID.
While implementing microservice using event sourcing, CQRS, the persisted entity records are complementary to the historically organized information in the event backbone. 

*When is it acceptable to be using synchronous interactions between services instead of asynchronous event interacts through the event backbone?*   

For non-state-changing queries, for which the response is always instantaneously available a synchronous query call may be acceptable and will provide a simpler more understandable interface. Any processing which can be though of as being triggered by some state change in another aggregate should be modelled with an asynchronous event, because as the solution evolves other new microservices may also need to be aware of such event. We do not want to have to go back and change logic existing service where this event originated to have that microservice actively report the event to all potential consumers. 

*How do we save microservices from having to maintain data collections with complex secondary indexing for which eventual consistency will be hard to implement?*

* Each command  microservice should do all its state changing updates using the primary key lookup only for its entities.
* Each asynchronous event interaction between microservices should carry primary entityIds ( orderID, VoyageID, shipID) for any entities associated with the interaction.
* Each query which might require speciaoized secondary indexing to respond to queries can be implemented in a separate CQRS query service which subscribes to events  to do all internal updating and receives events from the event backbone in a ( Consistent) eventually correct order. 
* This allows for recovery of any failed service by rebuilding it in "eventually correct" order.

### Applied DDD for the reference implementation

See [this note for details.](https://ibm-cloud-architecture.github.io/refarch-kc-order-ms/ddd-applied/)
