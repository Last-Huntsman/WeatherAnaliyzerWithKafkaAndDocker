package org.zuzukov;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

public class KafkaConsumerApplication {
    private static Logger Log = LoggerFactory.getLogger(KafkaConsumerApplication.class);

    public static void main(String[] args) {
        var properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "zuzukov-group-");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");


        try (var consumer = new KafkaConsumer<String, String>(properties)) {
            consumer.subscribe(Pattern.compile("weather"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                if (!records.isEmpty()) {
                    records.forEach(record -> Log.info("Record: {}", record));
                    consumer.commitAsync(); // Асинхронный коммит
                }
            }
        }
    }
}
