package com.example.Kafka.config;

import com.example.Kafka.model.KafkaModel;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Bean
//    public ProducerFactory<String, KafkaModel> producerFactory(){
    public ProducerFactory<String, String> producerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
//    public KafkaTemplate<String, KafkaModel> kafkaTemplate(){
//        return new KafkaTemplate<>(producerFactory());
//    }
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
//    public ConsumerFactory<String, KafkaModel> consumerFactory(){
    public ConsumerFactory<String, String> consumerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"myGroupId");

//        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(KafkaModel.class));
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new StringDeserializer());


    }

    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, KafkaModel> kafkaListenerContainerFactory(){
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
//        ConcurrentKafkaListenerContainerFactory<String, KafkaModel> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();

        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

        return concurrentKafkaListenerContainerFactory;
    }

//    Creating a serialization bean
    @Bean
    public Gson jsonConverter(){
        return new Gson();
    }
}
