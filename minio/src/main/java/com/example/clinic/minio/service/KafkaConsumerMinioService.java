package com.example.clinic.minio.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service

public class KafkaConsumerMinioService {
    @KafkaListener(topics = "minio-notifications", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePatientNotifications(String message) {
        System.out.println("Received from minio-notifications: " + message);
    }

}
