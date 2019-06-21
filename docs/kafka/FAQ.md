# Frequently asked questions

## Kafka concepts?

See [this introduction](readme.md)

## How to support exactly once delivery?

See the section in the producer implementation considerations [note](producers.md).

Also it is important to note that the Kafka Stream API supports exactly once semantics with the config: `processing.guarantee=exactly_once`. Each task within a read-process-write flow may fail so this setting is important to be sure the right answer is delivered, even in case of task failure, and the process is executed exactly once. 