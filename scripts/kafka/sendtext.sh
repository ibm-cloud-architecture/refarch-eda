#!/usr/bin/env bash
/opt/kafka_2.11-0.10.1.0/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test-topic << EOB
this is a message for you
and this one too
but this one...I m not sure
EOB
