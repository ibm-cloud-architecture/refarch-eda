#!/usr/bin/env bash
/opt/kafka_2.11-0.10.1.0/bin/kafka-console-consumer.sh --bootstrap-server  localhost:9092 --topic mytopic --from-beginning
