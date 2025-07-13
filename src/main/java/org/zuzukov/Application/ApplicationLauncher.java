package org.zuzukov.Application;
/*
* Стартер для продюсера и консюмера
* */
public class ApplicationLauncher {
    public static void main(String[] args) {

        Thread consumerThread = new Thread(() -> {
            KafkaConsumerApplication consumerApp = new KafkaConsumerApplication();
            consumerApp.Start();
        });


        Thread producerThread = new Thread(() -> {
            KafkaProducerApplication producerApp = new KafkaProducerApplication();
            producerApp.Start();
        });


        consumerThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        producerThread.start();
    }
}