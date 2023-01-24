package com.example.Kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    private static final String Topic = "kafkaPoc";
    public void sendStringMessage(String name){
        kafkaTemplate.send(Topic, name);
    }
}
