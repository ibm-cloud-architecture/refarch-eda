---
title: Consumer Groups in Event Streams
description: Consumer Groups in Event Streams
---

## Consumer groups

You can review the major concepts for Kafka Consumer in [this note](../../kafka-producers-consumers/#kafka-consumers) and the [product documentation](https://cloud.ibm.com/docs/EventStreams?topic=EventStreams-consuming_messages#consumer-groups).
The following figure summarizes the concepts involved in this lab: Consumers belong to **consumer groups** and partitions are dynamically allocated to consumer(s) within the group.

![](../images/consumer-groups.png)

## Considerations

Kafka automatically detects failed consumers so that it can reassign partitions to working consumers. 

The consumer can take time to process records, so to avoid the consumer group controler removing consumer taking too long, it is possible to set the [max.poll.interval.ms](https://kafka.apache.org/documentation/#max.poll.interval.ms) consumer property. If poll() is not called before expiration of this timeout, then the consumer is considered failed and the group will rebalance in order to reassign the partitions to another member. 
The second mechanism is the heartbeat consumers send to the group cordinator to show they are alive. The [session.timeout.ms](https://kafka.apache.org/documentation/#session.timeout.ms) specifies the max value to consider before removing a non responding consumer. 

## Offset

Consumers do a read commit for the last processed record: 

![of-1](./images/offsets.png)

When a consumer starts and is assigned a partition to consume, it will start at its group's committed offset or latest or ealiest as [auto.offset.reset](https://kafka.apache.org/documentation/#auto.offset.reset) (When there is a committed offset, the auto.offset.reset property is not used).

If a consumer fails after processing a message but before committing its offset, the committed offset information will not reflect the processing of the message. 

![of-2](./images/offsets-2.png)

This means that the message will be processed again by the next consumer in that group to be assigned the partition.

In the case where consumers are set to auto commit, it means the offset if committed at the poll() level and if the service crashed while processing of this record as: 

![of-3](./images/offsets-3.png)

then the record (partition 0 - offset 4) will never be processed.

## Consumer lag

The consumer lag for a partition is the difference between the offset of the most recently published message and the consumer's committed offset.

If the lag starts to grow, it means the consumer is not able to keep up with the producer's pace.

The risk, is that slow consumer may fall behind, and when partition management may remove old log segments, leading the consumer to jump forward to continnue on the next log segment. Consumer may have lost messages.

![](./images/offsets-4.png)

You can use the kafka-consumer-groups tool to see the consumer lag, or use the Event Streams User Interface:

![](./images/es-cg-1.png)

The group can be extended to see how each consumer, within the group, performs on a multi partitions topic:

![](./images/es-cg-2.png)


## Consumer group APIs

### List the consumer groups using CLI

```shell
ibmcloud es groups
```

Results:

```shell
Group ID   
ContainerAnomalyRetrySpringConsumers   
ContainerSpringConsumers   
OrderSpringConsumers   
ordercmd-command-consumer-grp   
ordercmd-event-consumer-grp   
orderquery-container-consumer   
orderquery-orders-consumer   
reefer-telemetry-reactive   
voyage-consumer-group   

```

### Get the details of a consumer group

```shell
ibmcloud es group ordercmd-command-consumer-grp 
```

and the results:

```shell
Details for consumer group ordercmd-command-consumer-grp
Group ID                        State   
ordercmd-command-consumer-grp   Stable   

Topic                            Partition   Current offset   End offset   Offset lag   Client                      Consumer                                                         Host   
eda-integration-order-commands   0           11               11           0            ordercmd-command-consumer   ordercmd-command-consumer-337bb052-371b-431a-b386-3a0e99fafb18   /169.254.0.3   
```

### Reset a group

Sometime it is needed to reprocess the messages. The easiest way is to change the groupid of the consumers to get an implicit offsets reset, but it is also possible to reset for some topic to the earliest offset:


```shell
 ibmcloud es group-reset --group ordercmd-command-consumer-grp --all-topics --mode earliest --execute
```

The previous command is the same as using the kafka tool:

```shell
kafka-consumer-groups \
                    --bootstrap-server kafkahost:9092 \
                    --group ordercmd-command-consumer-grp \
                    --reset-offsets \
                    --all-topics \
                    --to-earliest \
                    --execute
```

To get the processing for a specific topic:

```
 ibmcloud es group-reset --group ordercmd-command-consumer-grp --topic orders
```

### Delete a group

This works only for empty group

```
ibmcloud es group-delete reefer-telemetry
```