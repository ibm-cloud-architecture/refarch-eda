---
title: Advantages of Event-Driven Reference Architectures
description: Advantages of event-driven architectures, related to characteristics of scalability.
---

# Scalability

Event-Driven architectures are highly scalable. The use of an event-driven backbone allows for the addition (and removal) of consumers based on the number of messages waiting to be consumed from a topic. This is good for architectures where in-stream transformation is needed, like data science workflows. Messages can be consumed and transformed extremely fast, which is advantageous for processes where millisecond decision making is necessary. 

When using a system like Kafka, the data (in a given topic) is partitioned by the broker, which allows for parallel processing of the data for consumpton. Consumers are usually assigned to one partition.  

As well as scaling up, there is also the ability to scale down (even to zero). When scaling up and down happens autonomously, promoting energy and cost efficiency, it is referred to as 'elasticity'. 

