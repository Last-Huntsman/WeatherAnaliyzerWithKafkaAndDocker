
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.zuzukov.Application.KafkaProducerApplication;
import org.zuzukov.Enums.Cities;


import static org.mockito.Mockito.*;

class KafkaProducerIntegrationTest {

    @Test
    void testProducerSendsMessages() {
        @SuppressWarnings("unchecked")
        KafkaProducer<String, JSONObject> mockProducer = mock(KafkaProducer.class);
        KafkaProducerApplication.sendTestMessage(mockProducer,false);
        verify(mockProducer, times(Cities.values().length)).send(any(ProducerRecord.class));
    }
}