package com.example.clinic.minio.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;
@Service
public class KafkaTopicMinioService {
    private final KafkaAdmin kafkaAdmin;

    public KafkaTopicMinioService(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    public void createMinioTopic(String topicName, int partitions, short replicationFactor) {
        NewTopic topic = new NewTopic(topicName, partitions, replicationFactor);
        kafkaAdmin.createOrModifyTopics(topic);
        System.out.println("Топик создан: " + topicName + " портаций - " + partitions + " репликаций - " + replicationFactor);
    }



}
