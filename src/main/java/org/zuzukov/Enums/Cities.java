package org.zuzukov.Enums;

public enum Cities {
    MOSCOW("Москва"),
    SAINT_PETERSBURG("Санкт-Петербург"),
    NOVOSIBIRSK("Новосибирск"),
    YEKATERINBURG("Екатеринбург"),
    KAZAN("Казань"),
    MAGADAN("Магадан"),
    CHUKOTKA("Чукотка"),
    TYUMEN("Тюмень"),
    ROSTOV_ON_DON("Ростов-на-Дону");


    private final String name;

    Cities(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
