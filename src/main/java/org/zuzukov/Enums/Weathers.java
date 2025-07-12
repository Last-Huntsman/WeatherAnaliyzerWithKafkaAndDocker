package org.zuzukov.Enums;



public enum Weathers {


    SUNNY("Солнечно"),

    CLOUDY("Пасмурно"),

    RAIN("Дождь"),

    SNOW("Снег"),

    FOG("Туман");
    private final String name;

    private Weathers(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
