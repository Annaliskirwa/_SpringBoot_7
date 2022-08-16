package com.example.Kafka.controller;

import com.example.Kafka.model.KafkaModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/kafka")
public class KafkaController {
    private KafkaTemplate<String, KafkaModel> kafkaTemplate;
    @Autowired
    public KafkaController(KafkaTemplate<String, KafkaModel> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    @PostMapping
    public KafkaModel post(@RequestBody KafkaModel kafkaModel){
        kafkaTemplate.send("myTopic", kafkaModel);
        return kafkaModel;
//        This kafka model is returned in the kafka consumer console
    }

    @KafkaListener(topics="myTopic")
    public void getFromKafka(KafkaModel kafkaModel){
//        System.out.println(kafkaModel.toString());
        log.info("*********Get From Kafka**************");
        log.info(kafkaModel.getField1() + kafkaModel.getField2());
    }
}
