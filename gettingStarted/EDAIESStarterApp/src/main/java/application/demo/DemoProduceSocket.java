package application.demo;

import java.io.IOException;
import java.io.StringReader;
import java.net.ConnectException;
import java.util.concurrent.ExecutionException;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.log4j.Logger;

import application.demo.RecordDataEncoder;
import application.kafka.Producer;
import application.demo.RecordData;

@ServerEndpoint(value = "/demoproduce", encoders = {RecordDataEncoder.class})
public class DemoProduceSocket {

    private Session currentSession = null;
    private Producer producer = null;

    private DemoController demoController = null;

    private Logger logger = Logger.getLogger(DemoProduceSocket.class);

    private final String BOOTSTRAP_SERVER_ENV_KEY = "BOOTSTRAP_SERVER";
    private final String TOPIC_ENV_KEY = "TOPIC";

    private String topic;

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        logger.debug(String.format("Socket opened with id %s", session.getId()));
        currentSession = session;
        String bootstrapServerAddress = System.getenv(BOOTSTRAP_SERVER_ENV_KEY).replace("\"", "");
        topic = System.getenv(TOPIC_ENV_KEY).replace("\"", "");
        try {
            producer = new Producer(bootstrapServerAddress, topic);
        } catch(InstantiationException e) {
            onError(e);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try(JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            String action = jsonMessage.getString("action");
            logger.debug(String.format("Message received from session %s with action %s", session.getId(), action));
            switch (action) {
                case "start":
                    if(demoController != null) {
                        demoController.produceMessages = false;
                    }
                    String customContent = jsonMessage.getString("custom");
                    demoController = new DemoController(customContent);
                    Thread thread = new Thread(demoController);
                    thread.start();
                    break;
                case "stop":
                    if(demoController != null) {
                        demoController.produceMessages = false;
                    }
                    break;
                default:
                    logger.warn("Received message with unknown action, expected 'start' or 'stop'.");
                    break;
            }
        } catch (JsonException | IllegalStateException e) {
            onError(e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) throws InterruptedException {
        if(demoController != null) {
            demoController.produceMessages = false;
        }
        if (!producer.equals(null)) {
            producer.shutdown();
        }
        logger.info(String.format("Client connection for session %s closed.", session.getId()));
    }

    @OnError
    public void onError(Throwable throwable) {
        logger.error(throwable);
        try {
            CloseReason closeReason = new CloseReason(
                CloseReason.CloseCodes.UNEXPECTED_CONDITION, 
                throwable.getMessage()
            );
            currentSession.close(closeReason);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private class DemoController implements Runnable {

        volatile boolean produceMessages = true;
        private String customContent;
        private final long sleepTimeMs = 2000;

        public DemoController(String customContent) {
            if(customContent == null) {
                this.customContent = "";
            } else {
                this.customContent = customContent;
            }
        }

        @Override
        public void run() {
            while(produceMessages) {
                try {
                    RecordData recordData = produceMessage();
                    logger.debug(String.format("Updating session %s with new RecordData %s", currentSession.getId(), recordData.encode()));
                    currentSession.getBasicRemote().sendObject(recordData);
                    Thread.sleep(sleepTimeMs);
                } catch ( InterruptedException | IOException | EncodeException e) {
                    onError(e);
                }
            }
            logger.debug(String.format("Exiting DemoController run loop for session %s.", currentSession.getId()));
        }

        private RecordData produceMessage() {
            logger.debug(String.format("Producing message with payload %s", customContent));
            RecordData recordData;
            try {
                RecordMetadata recordMetadata = producer.produce(customContent);
                recordData = new RecordData(
                    RecordData.Status.DELIVERED,
                    recordMetadata.topic(),
                    recordMetadata.partition(),
                    recordMetadata.offset(),
                    recordMetadata.timestamp()
                );
            } catch (KafkaException | InterruptedException | ExecutionException | ConnectException e) {
                if (e instanceof ConnectException) {
                    onError(e);
                }
                recordData = new RecordData(RecordData.Status.ERROR, topic);
            }
            return recordData;
        }
    }
}