# Track refactoring
From one page fix all links, and consider migration done when links point to page ported to new docs-mk folder. The target page may not be done.

## to move 

## Done
    - introduction
      - Overview: introduction/overview/index.md
      - Reference Architecture: introduction/reference-architecture/index.md
      - Business Use Cases: introduction/usecases/index.md
      - Target Audiences: introduction/target-audiences/index.md
    - Learning Journey:
        - Get started (101 content): journey/101/index.md
    - Concepts:
      - Terms & Definitions: concepts/terms-and-definitions/index.md
      - Event streaming versus Queuing: concepts/events-versus-messages/index.md
      - Fit for purpose: concepts/fit-to-purpose/index.md
      - Service mesh: concepts/service-mesh/index.md
      - Agile Integration: concepts/integration/index.md
      - Devising the data models: concepts/model/index.md
      - Flow Architecture: concepts/flow-architectures.md
    - Advantages of EDA:
      - Microservice decoupling: advantages/microservice/index.md
      - Reactive systems: advantages/reactive/index.md
      - Resiliency: advantages/resiliency/index.md
      - Scalability: advantages/scalability/index.md
    - Patterns in EDA:
      - Introduction: patterns/intro/index.md
      - Event Sourcing: patterns/event-sourcing/index.md
      - CQRS: patterns/cqrs/index.md
      - Saga: patterns/saga/index.md
      - Dead Letter Queue: patterns/dlq/index.md
      - Data Intensive App: patterns/data-pipeline/index.md
      - Near real-time analytics: patterns/realtime-analytics/index.md
      - API management: patterns/api-mgt/index.md
      - Situational decision: patterns/cep/index.md
    - Technology:
      - Kafka Overview: technology/kafka-overview/index.md
      - Avro Schema: technology/avro-schemas/index.md
      - Event Streams: technology/event-streams/index.md
      - MQ in EDA context: technology/mq/index.md
      - Kafka Consumers: technology/kafka-consumers/index.md
      - Kafka Producers: technology/kafka-producers/index.md
  - Methodology:
      - Event Storming: methodology/event-storming/index.md
      - Domain-Driven Design: methodology/domain-driven-design/index.md
      - Data lineage: methodology/data-lineage/index.md
      - Data Intensive App Development: methodology/data-intensive/index.md
      - governance
  - Scenarios:
    - Overview: scenarios/overview/
    - Reefer Shipment Solution: https://ibm-cloud-architecture.github.io/refarch-kc/
    - Vaccine at Scale: https://ibm-cloud-architecture.github.io/vaccine-solution-main/
    - Near real-time Inventory: https://ibm-cloud-architecture.github.io/eda-rt-inventory-gitops

## moved content to verify links . 

- Patterns in EDA:  
    - Topic Replication: patterns/topic-replication/index.md

      

      
     
* Technology

    * mirror maker
    * streams
    * event streams
    * flink

    - Kafka Streams: technology/kafka-streams/index.md
    - Mirror Maker 2:  technology/kafka-mirrormaker/index.md


## To revisit the structure and content

* Pattern > Topic replication with DR and MM2
* Scenario: kafka-mm2, reefer,
- SAGA with MQ Orchestration: scenarios/saga-orchestration/