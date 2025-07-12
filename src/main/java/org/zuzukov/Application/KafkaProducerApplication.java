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
import org.zuzukov.Json.JsonSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class KafkaProducerApplication {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerApplication.class);
    private static final Random RANDOM = new Random();
    private static final Weathers[] weathers = Weathers.values();
    private static final Cities[] cities = Cities.values();
    private static int DaysPlus;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());


        try (var producer = new KafkaProducer<String, JSONObject>(properties)) {

            while (DaysPlus++ < 7) {
                for (Cities city : cities) {
                    JSONObject jsObject = new JSONObject();
                    jsObject.put("weather", weathers[RANDOM.nextInt(weathers.length)].getName());
                    jsObject.put("cities", city.getName());
                    jsObject.put("temperature", RANDOM.nextInt(10, 38));
                    jsObject.put("timeweather", LocalDate.now().plusDays(DaysPlus));
                    jsObject.put("timestamp", LocalDateTime.now());

                    var metadata = producer.send(new ProducerRecord<>("weather", jsObject)).get();

                    LOG.info("====================");
                    LOG.info("Metadata:{}", metadata);
                    LOG.info("====================");
                    Thread.sleep(1);

                }

            }
        }
    }
}
