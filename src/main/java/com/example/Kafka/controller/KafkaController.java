package com.example.Kafka.controller;

import com.example.Kafka.model.KafkaModel;
import com.example.Kafka.model.KafkaModel2;
import com.google.gson.Gson;
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
//    private KafkaTemplate<String, KafkaModel> kafkaTemplate;
private KafkaTemplate<String, String> kafkaTemplate;
private Gson jsonConverter;

    @Autowired
    public KafkaController(KafkaTemplate<String, String> kafkaTemplate, Gson jsonConverter){
        this.kafkaTemplate = kafkaTemplate;
        this.jsonConverter = jsonConverter;
    }
    @PostMapping
    public KafkaModel post(@RequestBody KafkaModel kafkaModel){
        kafkaTemplate.send("myTopic", jsonConverter.toJson(kafkaModel));
        return kafkaModel;
    }

    @PostMapping("/v2")
    public KafkaModel2 post(@RequestBody KafkaModel2 kafkaModel2){
        kafkaTemplate.send("myTopic2", jsonConverter.toJson(kafkaModel2));
        return kafkaModel2;
    }

    @KafkaListener(topics="myTopic")
    public void getFromKafka(String kafkaModel){
        log.info("*********Get From Kafka**************");
        log.info(kafkaModel);

        KafkaModel kafkaModel1 = (KafkaModel) jsonConverter.fromJson(kafkaModel, KafkaModel.class);
        log.info("**********After json converter***********");
        log.info(kafkaModel1.getField1()+kafkaModel1.getField2());
    }

    @KafkaListener(topics = "myTopic2")
    public void getFromKafka2(String kafkaModel2){
        log.info("*********Get From Kafka**************");
        log.info(kafkaModel2);

        KafkaModel2 kafkaModel = (KafkaModel2) jsonConverter.fromJson(kafkaModel2, KafkaModel2.class);
        log.info("**********After json converter***********");
        log.info(kafkaModel.getTitle() + kafkaModel.getDescription());
    }
}
