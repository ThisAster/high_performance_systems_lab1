package com.example.clinic.patient.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

@Service
public class KafkaTopicPatientService {

    private final KafkaAdmin kafkaAdmin;

    public KafkaTopicPatientService(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    public void createPatientTopic(String topicName, int partitions, short replicationFactor) {
        NewTopic topic = new NewTopic(topicName, partitions, replicationFactor);
        kafkaAdmin.createOrModifyTopics(topic);
        System.out.println("Топик создан: " + topicName + " портаций - " + partitions + " репликаций - " + replicationFactor);
    }
}