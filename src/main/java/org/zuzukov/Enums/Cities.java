package org.zuzukov.Enums;

public enum Cities {
    MOSCOW("Москва"),
    SAINT_PETERSBURG("Санкт-Петербург"),
    NOVOSIBIRSK("Новосибирск"),
    YEKATERINBURG("Екатеринбург"),
    KAZAN("Казань"),
    SAMARA("Самара"),
    OMSK("Омск"),
    CHELYABINSK("Челябинск"),
    ROSTOV_ON_DON("Ростов-на-Дону"),
    IRKUTSK("Иркутск");

    private final String name;

    Cities(String name) {
        this.name = name;
    }

}
