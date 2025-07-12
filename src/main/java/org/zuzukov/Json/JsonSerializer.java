package org.zuzukov;

import org.apache.kafka.common.serialization.Serializer;
import org.json.JSONObject;

public class JsonSerializer implements Serializer<JSONObject> {
    @Override
    public byte[] serialize(String s, JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        return jsonObject.toString().getBytes();
    }
}
