# Consumers considerations 


## Offset management
When dealing with heavy load storing offset in zookeeper is non advisable. To manage offset we use the new consumer API. The code in ibm.cte.esp.AssetInjector class commits offset synchronously when a specified number of assets are read from the topic and the persistence to the back end succeed.

When designing a consumer the following requirements need to be analyzed:

Do we need to have multiple consumers running in parallel to scale horizontally: this means having multiple partitions and use fine grained control over offset persistence. If there is not such need, the High Level Consumer approach can be used and it will commit offsets for all partitions.
Is it possible to loose message from topic? if so, when a consumer restarts it will start consuming the topic from the end of the queue.
Do the solution is fine with at-least-once delivery or exactly-once is a must have? As the operation to store a message and the storage of offsets are two separate operations, and in case of failure between them, it is possible to have stale offsets, which will introduce duplicate messages when consumers restart to process from last known committed offset. "exactly-once" means grouping record and offset persistence in an atomic operation.
What are the criteria to consider a message as "consumed"?
