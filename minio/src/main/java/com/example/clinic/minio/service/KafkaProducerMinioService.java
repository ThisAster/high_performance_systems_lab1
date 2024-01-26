package com.example.clinic.minio.service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
public class KafkaProducerMinioService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerMinioService (KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        try {
            kafkaTemplate.send(topic, message).get();
            System.out.println("Message sent to topic: " + topic);
        } catch (Exception e) {
            System.err.println("Error sending message to topic: " + topic);
            e.printStackTrace();
        }
    }




}
