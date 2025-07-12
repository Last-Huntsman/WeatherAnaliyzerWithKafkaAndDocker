

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KafkaConsumerApplicationTest {

    @Test
    void testJsonParsing() {
        JSONObject json = new JSONObject();
        json.put("weather", "sunny");
        json.put("city", "Moscow");
        json.put("temperature", 25);

        assertEquals("sunny", json.getString("weather"));
        assertEquals("Moscow", json.getString("city"));
        assertEquals(25, json.getInt("temperature"));
    }
}