package application.kafka;

import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Consumer {

    private final String CONSUMER_GROUP_ID = "CONSUMER_GROUP_ID";
    private final String APP_NAME = "EDAIESStarterApp";
    private final String DEFAULT = "DEFAULT";
    private final long POLL_DURATION = 1000;
    private final String USERNAME = "token";
    private final String API_KEY = System.getenv("CONSUMER_API_KEY");

    private String consumerGroupId;
    private KafkaConsumer<String, String> kafkaConsumer;

    private Logger logger = Logger.getLogger(Consumer.class);


    public Consumer(String bootstrapServerAddress, String topic) throws InstantiationException {
        BasicConfigurator.configure();
        setOrGenerateConsumerGroupId();

        if (topic == null) {
            throw new InstantiationException("Missing required topic name.");
        } else if (bootstrapServerAddress == null) {
            throw new InstantiationException("Missing required bootstrap server address.");
        }
        try {
            kafkaConsumer = createConsumer(bootstrapServerAddress);
        } catch (KafkaException e) {
            throw new InstantiationException(e.getMessage());
        }
        kafkaConsumer.subscribe(Arrays.asList(topic));
    }

    private KafkaConsumer<String, String> createConsumer(String brokerList) {

        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(SslConfigs.SSL_PROTOCOL_CONFIG, "TLSv1.2");
        properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "resources/security/certs.jks");
        properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "password");
        properties.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        String saslJaasConfig = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\""
            + USERNAME + "\" password=" + API_KEY + ";";
        properties.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfig);

        KafkaConsumer<String, String> kafkaConsumer = null;

        try {
            kafkaConsumer = new KafkaConsumer<>(properties);
        } catch (KafkaException kafkaError) {
            logger.error("Error creating kafka consumer.", kafkaError);
            throw kafkaError;
        }
        
        return kafkaConsumer;
    }

    private void setOrGenerateConsumerGroupId() {
        consumerGroupId = System.getenv(CONSUMER_GROUP_ID);
        
        if (consumerGroupId == null) { 
            consumerGroupId = APP_NAME;
        } else if (consumerGroupId.equals(DEFAULT)) {
            consumerGroupId = UUID.randomUUID().toString(); 
        }
    }

    public ConsumerRecords<String, String> consume() {
        ConsumerRecords<String, String> records = kafkaConsumer.poll(POLL_DURATION);
        return records;
    }
    
    public void shutdown() {
        kafkaConsumer.close();
        logger.info(String.format("Closed consumer: %s", consumerGroupId));
    }
}