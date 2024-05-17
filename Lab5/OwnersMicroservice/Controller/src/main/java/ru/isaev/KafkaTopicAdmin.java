package ru.isaev;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicAdmin {
    @Bean
    public NewTopic getCatResponseTopic() {
        return TopicBuilder.name("topic-owner-response").build();
    }

    @Bean
    public NewTopic setOwnerOfCatsToNullTopic() {
        return TopicBuilder.name("topic-set-owner-of-cats-to-null").build();
    }
}