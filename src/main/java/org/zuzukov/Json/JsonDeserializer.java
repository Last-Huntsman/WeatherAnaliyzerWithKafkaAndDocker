package org.zuzukov.Json;

import org.apache.kafka.common.serialization.Deserializer;
import org.json.JSONObject;

public class JsonDeserializer implements Deserializer<JSONObject>{
    @Override
    public JSONObject deserialize(String s, byte[] bytes) {
        if(bytes == null){
            return null;
        }
        JSONObject json = new JSONObject(new String(bytes));
        return json;
    }
}