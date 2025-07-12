package org.zuzukov.Json;

import org.json.JSONObject;
import org.zuzukov.Enums.Cities;
import org.zuzukov.Enums.Weathers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
/**
 * Генерирует данные о погоде для указанного города и времени
 */
public class JsonGenerator {
    private static final Random RANDOM = new Random();
    private static final Weathers[] weathers = Weathers.values();

    public static JSONObject createJsonObject(LocalDateTime localDateTime, LocalDate date, Cities city) {
        JSONObject jsObject = new JSONObject();
        jsObject.put("weather", weathers[RANDOM.nextInt(weathers.length)].getName());
        jsObject.put("city", city.getName());
        jsObject.put("temperature", RANDOM.nextInt(10, 38));
        jsObject.put("timeweather", date);
        jsObject.put("timestamp", localDateTime);
        return jsObject;
    }
}
