# Weather Kafka Analytics

## 📌 Описание

Проект представляет собой Kafka-приложение, моделирующее погодные условия в разных городах и собирающее статистику на основе полученных данных.

Компоненты:
- **Kafka Producer** — генерирует погодные данные и отправляет их в Kafka-топик `weather`.
- **Kafka Consumer** — читает сообщения из Kafka и анализирует статистику по городам.
- **WeatherStatisticsCollector** — обрабатывает сообщения и собирает статистику (солнечные дни, дождливые и т.п.).

---

## 🛠️ Технологии

- Java 21
- Apache Kafka (через Docker)
- Docker Compose
- Maven
- JSON (org.json)
- SLF4J (логгирование)

---

## 🚀 Быстрый старт

### 1. Собери `.jar` файл:

```bash
mvn clean package -DskipTests
```

> Убедись, что у тебя есть `jar-with-dependencies` (fat jar), который попадёт в `target/`.

---

### 2. Запусти всё через Docker Compose:

```bash
docker compose up --build
```

Docker Compose поднимет:

- Kafka и Zookeeper (wurstmeister)
- Приложение (producer + consumer)

---

### 3. Структура проекта

```
.
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── src/
│   └── main/java/org/zuzukov/
│       ├── Application/
│       │   ├── ApplicationLauncher.java
│       │   ├── KafkaConsumerApplication.java
│       │   └── KafkaProducerApplication.java
│       ├── AnalyzerPackage/
│       │   └── WeatherStatisticsCollector.java
│       ├── Enums/
│       │   ├── Cities.java
│       │   └── Weathers.java
│       └── Json/
│           ├── JsonDeserializer.java
│           ├── JsonSerializer.java
│           └── JsonGenerator.java
```

---

## ⚙️ Конфигурации Kafka

### Producer

- Топик: `weather`
- Сериализаторы:
  - Key: `StringSerializer`
  - Value: `JsonSerializer`

### Consumer

- Группа: `org.zuzukov`
- `auto.offset.reset`: `latest` (можно изменить на `earliest` при необходимости)
- `enable.auto.commit`: `false` — используется асинхронный `commitAsync`

---

## 📈 Что делает `WeatherStatisticsCollector`

Проводит анализ полученных JSON-сообщений. Выводит:

- Солнечные дни в Магадане
- Солнечные дни на Чукотке
- Дождливые дни в Питере
- Подряд идущие дождливые дни в Тюмени
  - Если >= 2 подряд — сообщает: **"В Тюмени можно идти за грибами!"**

---

## 🐳 Dockerfile

```Dockerfile
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*-jar-with-dependencies.jar app.jar
CMD ["java", "-jar", "app.jar"]
```

---

## 🧱 docker-compose.yml

```yaml
version: '3'

services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"

  kafka:
    image: wurstmeister/kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
    depends_on:
      - zookeeper
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock

  weather-app:
    build:
      context: .
      dockerfile: Dockerfile
    depends_on:
      - kafka
    environment:
      BOOTSTRAP_SERVERS: kafka:9092
```

---

## 💡 Возможные улучшения

- Добавить REST-интерфейс для получения статистики
- Сохранять статистику в PostgreSQL или MongoDB
- Поддержка Avro/Protobuf вместо JSON
- Добавить Unit-тесты на сборщик статистики и producer

---

## 📬 Авторы

- **zuzukov** — реализация Kafka-приложения и анализатора погоды

---
