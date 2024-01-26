package com.example.clinic.minio.controller;


import com.example.clinic.minio.service.KafkaTopicMinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicMinioController implements CommandLineRunner {

    private final KafkaTopicMinioService kafkaTopicMinioService;

    @Autowired
    public KafkaTopicMinioController(KafkaTopicMinioService kafkaTopicMinioService) {
        this.kafkaTopicMinioService = kafkaTopicMinioService;
    }

    @Override
    public void run(String... args) throws Exception {
        kafkaTopicMinioService.createMinioTopic("minio-notifications", 3, (short) 3);
        System.out.println("Default Kafka topics created");
    }
}