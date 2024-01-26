package com.example.clinic.patient.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerPatientService {



    @KafkaListener(topics = "patient-notifications", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePatientNotifications(String message) {
        System.out.println("Received from patient-notifications: " + message);
    }

}
