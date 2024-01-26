package com.example.clinic.auth.controller;

import com.example.clinic.auth.service.KafkaTopicAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka") // Общий префикс для Kafka API
public class KafkaTopicAuthController implements CommandLineRunner {

    private final KafkaTopicAuthService kafkaTopicAuthService;

    @Autowired
    public KafkaTopicAuthController(KafkaTopicAuthService kafkaTopicAuthService) {
        this.kafkaTopicAuthService = kafkaTopicAuthService;
    }

    // Метод для создания топиков через REST API
    @PostMapping("/create-topic")
    public String createTopic(
            @RequestParam String topicName,
            @RequestParam int partitions,
            @RequestParam short replicationFactor
    ) {
        kafkaTopicAuthService.createTopic(topicName, partitions, replicationFactor);
        return "Topic " + topicName + " created successfully";
    }

    // Метод для создания стандартных топиков при запуске приложения
    @Override
    public void run(String... args) throws Exception {
        kafkaTopicAuthService.createTopic("auth-notifications", 3, (short) 3);

        System.out.println("Default Kafka topics created");
    }
}