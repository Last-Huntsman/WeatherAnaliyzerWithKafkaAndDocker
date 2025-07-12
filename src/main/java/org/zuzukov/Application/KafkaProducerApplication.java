package org.zuzukov.Application;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zuzukov.Enums.Cities;
import org.zuzukov.Enums.Weathers;
import org.zuzukov.Json.JsonGenerator;
import org.zuzukov.Json.JsonSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;
/**
 * Отправляет сообщения в брокер
 */
public class KafkaProducerApplication {
    private static final String TOPIC = "weather";
    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerApplication.class);
    private static final Cities[] cities = Cities.values();

    public static void main(String[] args) {
        var properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        try (var producer = new KafkaProducer<String, JSONObject>(properties)) {
            for (int i = 0; i < 7; i++) {
                sendTestMessage(producer, true);
            }
        }
    }

    public static void sendTestMessage(KafkaProducer<String, JSONObject> producer, boolean threadSleep) {
        LocalDate date = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        for (Cities city : cities) {
            JSONObject jsObject = JsonGenerator.createJsonObject(localDateTime, date, city);
            producer.send(new ProducerRecord<>(TOPIC, jsObject));

            LOG.info("Отправлено сообщение: {}", jsObject);
            if(threadSleep) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }
    }



}
