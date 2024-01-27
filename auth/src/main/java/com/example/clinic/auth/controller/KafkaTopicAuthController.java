package com.example.clinic.auth.controller;

import com.example.clinic.auth.service.KafkaTopicAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicAuthController implements CommandLineRunner {

    private final KafkaTopicAuthService kafkaTopicAuthService;

    @Autowired
    public KafkaTopicAuthController(KafkaTopicAuthService kafkaTopicAuthService) {
        this.kafkaTopicAuthService = kafkaTopicAuthService;
    }
    @Override
    public void run(String... args) {
        kafkaTopicAuthService.createTopic("auth-notifications", 3, (short) 1);
        System.out.println("Default Kafka topics created");
    }
}