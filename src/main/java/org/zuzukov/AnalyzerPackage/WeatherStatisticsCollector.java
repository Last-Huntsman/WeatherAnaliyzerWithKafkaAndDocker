package org.zuzukov.AnalyzerPackage;

import org.json.JSONObject;

public class WeatherStatisticsCollector {
    private int sunnyDaysInMagadan = 0;
    private int sunnyDaysOnChukotka = 0;
    private int rainyDaysInPiter = 0;
    private int consecutiveRainyDaysInTyumen = 0;

    public void process(JSONObject weatherData) {
        String city = weatherData.getString("city");
        String weather = weatherData.getString("weather");


        switch (city) {
            case "Магадан" -> {
                if ("Солнечно".equals(weather)) sunnyDaysInMagadan++;
            }
            case "Чукотка" -> {
                if ("Солнечно".equals(weather)) sunnyDaysOnChukotka++;
            }
            case "Питер" -> {
                if ("Дождь".equals(weather)) rainyDaysInPiter++;
            }
            case "Тюмень" -> {
                if ("Дождь".equalsIgnoreCase(weather)) consecutiveRainyDaysInTyumen++;
                else consecutiveRainyDaysInTyumen = 0;
                if (consecutiveRainyDaysInTyumen >= 2) {
                    System.out.println("В Тюмени можно идти за грибами!");
                }
            }
        }
    }

    public void printStats() {
        System.out.println("Солнечных дней в Магадане: " + sunnyDaysInMagadan);
        System.out.println("Солнечных дней на Чукотке: " + sunnyDaysOnChukotka);
        System.out.println("Дождливых дней в Питере: " + rainyDaysInPiter);
    }
}