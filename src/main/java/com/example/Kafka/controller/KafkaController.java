package com.example.Kafka.controller;

import com.example.Kafka.model.KafkaModel;
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
//        This kafka model is returned in the kafka consumer console
    }

    @KafkaListener(topics="myTopic")
    public void getFromKafka(String kafkaModel){
//        System.out.println(kafkaModel.toString());
        log.info("*********Get From Kafka**************");
        log.info(kafkaModel);

        KafkaModel kafkaModel1 = (KafkaModel) jsonConverter.fromJson(kafkaModel, KafkaModel.class);
        log.info("**********After json converter***********");
        log.info(kafkaModel1.getField1()+kafkaModel1.getField2());
    }
}
