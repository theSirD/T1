package ru.isaev.Kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicAdmin {
    @Bean
    public NewTopic addCatTopic() {
        return TopicBuilder.name("topic-add-cat").build();
    }

    @Bean
    public NewTopic getCatByIdTopic() {
        return TopicBuilder.name("topic-get-cat-by-id").build();
    }

    @Bean
    public NewTopic getCatsByColorTopic() {
        return TopicBuilder.name("topic-get-cats-by-color").build();
    }

    @Bean
    public NewTopic getAllCatsTopic() {
        return TopicBuilder.name("topic-get-all-cats").build();
    }

    @Bean
    public NewTopic getCatsByBreedTopic() {
        return TopicBuilder.name("topic-get-cats-by-breed").build();
    }

    @Bean
    public NewTopic updateCatTopic() {
        return TopicBuilder.name("topic-update-cat").build();
    }

    @Bean
    public NewTopic deleteCatByIdTopic() {
        return TopicBuilder.name("topic-delete-cat-by-id").build();
    }





    @Bean
    public NewTopic ownersTopic() {
        return TopicBuilder.name("topic-owners").build();
    }
}