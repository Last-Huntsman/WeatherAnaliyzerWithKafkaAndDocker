import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.zuzukov.Application.KafkaProducerApplication;


import java.util.concurrent.Future;

import static org.mockito.Mockito.*;

class KafkaProducerApplicationTest {

    @Test
    void testSendMessage() {
        @SuppressWarnings("unchecked")
        KafkaProducer<String, JSONObject> mockProducer = mock(KafkaProducer.class);
        when(mockProducer.send(any(ProducerRecord.class))).thenReturn(mock(Future.class));
        KafkaProducerApplication.sendTestMessage(mockProducer,false);
        verify(mockProducer, atLeastOnce()).send(any(ProducerRecord.class));
    }
}