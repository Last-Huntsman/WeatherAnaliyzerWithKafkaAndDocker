package org.zuzukov.Application;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zuzukov.AnalyzerPackage.WeatherStatisticsCollector;
import org.zuzukov.Json.JsonDeserializer;

import java.time.Duration;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Читает сообщения из брокера и отправляет в WeatherStatistics
 */
public class KafkaConsumerApplication {
    private static Logger Log = LoggerFactory.getLogger(KafkaConsumerApplication.class);
    private static WeatherStatisticsCollector weatherStatisticsCollector = new WeatherStatisticsCollector();

    public static void main(String[] args) {
        var properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "org.zuzukov");

        startStatsPrinter();
        try (var consumer = new KafkaConsumer<String, JSONObject>(properties)) {
            consumer.subscribe(Pattern.compile("weather"));

            try {
                while (true) {
                    ConsumerRecords<String, JSONObject> records = consumer.poll(Duration.ofMillis(1000));
                    if (!records.isEmpty()) {
                        records.forEach(record -> {
                            try {
                                Log.info("Получено сообщение: {}", record.value());
                                weatherStatisticsCollector.process(record.value());
                            } catch (Exception ex) {
                                Log.error("Ошибка при обработке записи", ex);
                            }
                        });
                        consumer.commitAsync((offsets, exception) -> {
                            if (exception != null) {
                                Log.warn("Не удалось зафиксировать offset", exception);
                            }
                        });
                    }


                }
            } catch (Exception e) {
                Log.error("Ошибка в потреблении сообщений", e);
            } finally {
                weatherStatisticsCollector.printStats();
                consumer.close();
            }
        }

    }
    private static void startStatsPrinter() {
        Thread statsThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30_000);
                    Log.info("=== Актуальная статистика по погоде ===");
                    weatherStatisticsCollector.printStats();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        statsThread.setDaemon(true);
        statsThread.start();
    }

}
