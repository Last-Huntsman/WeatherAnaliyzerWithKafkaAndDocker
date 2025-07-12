package org.zuzukov.Application;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zuzukov.Json.JsonDeserializer;

import java.time.Duration;
import java.util.Properties;
import java.util.regex.Pattern;

public class KafkaConsumerApplication {
    private static Logger Log = LoggerFactory.getLogger(KafkaConsumerApplication.class);

    public static void main(String[] args) {
        var properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "org.zuzukov");


        try (var consumer = new KafkaConsumer<String, JSONObject>(properties)) {
            consumer.subscribe(Pattern.compile("weather"));

            while (true) {
                ConsumerRecords<String, JSONObject> records = consumer.poll(Duration.ofMillis(1000));

                if (!records.isEmpty()) {
                    records.forEach(record -> System.out.println(record.value()));
                    consumer.commitAsync();
                }
            }
        }
    }
}
