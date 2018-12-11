package application.kafka;

import java.net.ConnectException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Producer {

    private final String topic;
    private final String USERNAME = "token";
    private final String API_KEY = System.getenv("PRODUCER_API_KEY");

    private KafkaProducer<String, String> kafkaProducer;
    
    private Logger logger = Logger.getLogger(Producer.class);

    public Producer(String bootstrapServerAddress, String topic) throws InstantiationException {
        BasicConfigurator.configure();
        this.topic = topic;
        if (topic == null) {
            throw new InstantiationException("Missing required topic name.");
        } else if (bootstrapServerAddress == null) {
            throw new InstantiationException("Missing required bootstrap server address.");
        }
        try {
            kafkaProducer = createProducer(bootstrapServerAddress);
        } catch (KafkaException e) {
            throw new InstantiationException(e.getMessage());
        }
    }

    private KafkaProducer<String, String> createProducer(String brokerList) {
        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        properties.put(CommonClientConfigs.CONNECTIONS_MAX_IDLE_MS_CONFIG, 10000);
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 7000);
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(SslConfigs.SSL_PROTOCOL_CONFIG, "TLSv1.2");
        properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "resources/security/certs.jks");
        properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "password");
        properties.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        String saslJaasConfig = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\""
            + USERNAME + "\" password=" + API_KEY + ";";
        properties.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfig);
        
        KafkaProducer<String, String> kafkaProducer = null;
        
        try {
            kafkaProducer = new KafkaProducer<>(properties);
        } catch (KafkaException kafkaError ) {
            logger.error("Error while creating producer.", kafkaError);
            throw kafkaError;
        }
        return kafkaProducer;
    }

    public RecordMetadata produce(String message) throws InterruptedException, ExecutionException, ConnectException {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, message);
        RecordMetadata recordMetadata = kafkaProducer.send(record).get();
        return recordMetadata;
    }

    public void shutdown() {
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}