package org.zuzukov;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducerApplication {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerApplication.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        try (var producer = new KafkaProducer<String, String>(properties)) {
            int i=0;
            while (i++<3) {


                var metadata = producer.send(new ProducerRecord<>("weather", "Солнечно "+i )).get();

                LOG.info("====================");
                LOG.info("Metadata:{}", metadata);
                LOG.info("====================");
            }
        }
    }
}
