package ru.isaev;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicAdmin {
    @Bean
    public NewTopic getCatResponseTopic() {
        return TopicBuilder.name("topic-cat-response").build();
    }
}