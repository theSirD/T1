package ru.isaev;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicAdmin {
    @Bean
    public NewTopic getCatIdResponseTopic() {
        return TopicBuilder.name("topic-cat-id-response").build();
    }

    @Bean
    public NewTopic getCatResponseTopic() {
        return TopicBuilder.name("topic-cat-response").build();
    }

    @Bean
    public NewTopic getListOfCatsResponseTopic() {
        return TopicBuilder.name("topic-list-of-cats-response").build();
    }
}