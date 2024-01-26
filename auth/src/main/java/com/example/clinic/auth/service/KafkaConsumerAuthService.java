package com.example.clinic.auth.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerAuthService {



    @KafkaListener(topics = "auth-notifications", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeAuthorization(String message) {
        System.out.println("Received from auth-notifications: " + message);
    }


}
