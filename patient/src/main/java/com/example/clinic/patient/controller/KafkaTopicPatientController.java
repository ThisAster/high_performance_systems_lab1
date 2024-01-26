package com.example.clinic.patient.controller;

import com.example.clinic.patient.service.KafkaTopicPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicPatientController implements CommandLineRunner {

    private final KafkaTopicPatientService kafkaTopicPatientService;

    @Autowired
    public KafkaTopicPatientController(KafkaTopicPatientService kafkaTopicPatientService) {
        this.kafkaTopicPatientService = kafkaTopicPatientService;
    }

    @Override
    public void run(String... args) throws Exception {
        kafkaTopicPatientService.createPatientTopic("patient-notifications", 3, (short) 3);
        System.out.println("Default Kafka topics created");
    }
}