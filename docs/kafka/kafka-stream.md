# Kafka Streaming

Kafka Streams is a graph of processing nodes to implement the logic to process event streams. Each node process events from the parent node. We recommend reading this excellent introduction from Jay Kreps @confluent: [Kafka stream made simple](https://www.confluent.io/blog/introducing-kafka-streams-stream-processing-made-simple/) to get a good understanding of why Kafka stream was created. 

To summarize, **Kafka** Stream has the following capabilities:

* Stream processing is helpful for handling out-of-order data, *reprocessing* input as code changes, and performing stateful computations. It uses producer / consumer, stateful storage and consumer groups. It treats both past and future data the same way.
* Embedded library for your application to use.
* Integrate tables for state persistence with streams of events.
* Consumes continuous real time flows of records and publish new flows.
* Supports exactly-once processing semantics to guarantee that each record will be processed once and only once even when there is a failure.
* Stream APIs transform, aggregate and enrich data, per record with milli second latency, from one topic to another one.
* Supports stateful and windowing operations by processing one record at a time.
* Can be integrated in java application. No need for separate processing cluster. It is a Java API. But a Stream app is executed outside of the broker code, which is different than message flow in an ESB.
* Elastic, highly scalable, fault tolerance, it can recover from failure.

![](images/kafka-stream-arch.png)

* An application's processor topology is scaled by breaking it into multiple tasks.
* Tasks can then instantiate their own processor topology based on the assigned partitions.

It is a very important technology to process real-time data for analytics and event processing, developing stateless or stateful processing.

In general code for processing event does the following:

* Set a properties object to specify which brokers to connect to and what kind of serialization to use.
* Define a stream client: if you want stream of record use KStream, if you want a changelog with the last value of a given key use KTable (Example of using KTable is to keep a user profile with userid as key). In the container shipment implementation we use KTable to keep the Reefer container inventory in memory. 
* Create a topology of input source and sink target and the set of actions to perform in between.
* Start the stream client to consume records.

Programming with KStream and Ktable is not easy at first, as there are a lot of concepts for data manipulations, serialization and operations chaining. It also uses function programming and chaining.

A stateful operator uses the streaming Domain Specific Language, and is used for aggregation, join and time window operators. Stateful transformations require a state store associated with the stream processor. The code below comes from Kafka examples and is counting word occurrence in text:

```
    final StreamsBuilder builder = new StreamsBuilder();
    final Pattern pattern = Pattern.compile("\\W+");

    KStream<String, String> textLines = builder.stream(source);

    KTable<String, Long> wordCounts = textLines
       .flatMapValues(textLine -> Arrays.asList(pattern.split(textLine.toLowerCase())))
       .print(Printed.toSysOut()
       .groupBy((key, word) -> word)
       .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));
    wordCounts.toStream().to(sink, Produced.with(Serdes.String(), Serdes.Long()));

    KafkaStreams streams = new KafkaStreams(builder.build(), props);
    streams.start();
```

* [KStream](https://Kafka.apache.org/10/javadoc/org/apache/Kafka/streams/kstream/KStream.html) represents KeyValue records coming as event stream from the topic.
* flatMapValues() transforms the value of each record in "this" stream into zero or more values with the same key in a new KStream. So here the text line is split into words. The parameter is a [ValueMapper](https://Kafka.apache.org/10/javadoc/org/apache/Kafka/streams/kstream/ValueMapper.html) which applies transformation on values but keeps the key.
* groupBy() Group the records of this KStream on a new key that is selected using the provided KeyValueMapper. So here it creates new KStream with the extracted word as key.
* count() counts the number of records in this stream by the grouped key. Materialized is an api to define a store to persist state. So here the state store is "counts-store". As store is a in-memory table.
* Produced defines how to provide the optional parameter types when producing to new topics.
* KTable is an abstraction of a changelog stream from a primary-keyed table.

See [this article from Confluent](https://docs.confluent.io/current/streams/architecture.html) for deeper kafka stream architecture presentation.

### Example to run the Word Count application:

1. Be sure to create the needed different topics once the Kafka broker is started (test-topic, streams-wordcount-output):

```
docker exec -ti Kafka /bin/bash
cd /scripts
./createtopics.sh
```

1. Start a terminal window and execute the command to be ready to send message.

```
$ docker exec -ti Kafka /bin/bash
# can use the /scripts/openProducer.sh or...
root> /opt/Kafka_2.11-0.10.1.0/bin/Kafka-console-producer.sh --broker-list localhost:9092 --topic streams-plaintext-input
```

1. Start another terminal to listen to the output topic:

```
$ docker exec -ti Kafka /bin/bash
# can use the /scripts/consumeWordCount.sh or...
root> /opt/Kafka_2.11-0.10.1.0/bin/Kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic streams-wordcount-output --from-beginning --formatter Kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.Kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.Kafka.common.serialization.LongDeserializer
```

1. Start the stream client to count word in the entered lines

```
mvn exec:java -Dexec.mainClass=ibm.cte.Kafka.play.WordCount
```

Outputs of the WordCount application is actually a continuous stream of updates, where each output record is an updated count of a single word. A KTable is counting the occurrence of word, and a KStream send the output message with updated count.

## Other examples

We have implemented the container microservice of the Container Shipment solution using kstreams processing. See the presentation [here](https://ibm-cloud-architecture.github.io/refarch-kc-container-ms/kstreams), and go to the following code to see tests for the different process flow.

* [Basic kstream processing on order events](https://github.com/ibm-cloud-architecture/refarch-kc-container-ms/blob/master/kstreams/src/test/java/ut/TestOrderCreation.java)

## Further reading

* The API and [product documentation](https://kafka.apache.org/21/documentation/streams/developer-guide/).
* [Deep dive explanation for the differences between KStream and KTable](https://www.michael-noll.com/blog/2018/04/05/of-stream-and-tables-in-kafka-and-stream-processing-part1/)