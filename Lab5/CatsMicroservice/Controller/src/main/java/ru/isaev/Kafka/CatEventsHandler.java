package ru.isaev.Kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.isaev.CatDtos.CatDtoInput;
import ru.isaev.Cats.CatBreeds;
import ru.isaev.Cats.CatColors;

@Component
public class CatEventsHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = "topic-add-cat")
    void addCatHandler(CatDtoInput catDto) {
        logger.info("Trying to add cat with birthday: {}", catDto.getBirthday());
    }

    @KafkaListener(topics = "topic-get-cat-by-id")
    void getCatByIdHandler(Long id) {
        logger.info("Trying to get cat with id: {}", id);
    }

    @KafkaListener(topics = "topic-get-cats-by-color")
    void getCatsColorHandler(CatColors catColor) {
        logger.info("Trying to get cat with colors: {}", catColor);
    }

    @KafkaListener(topics = "topic-get-all-cats")
    void getAllCatsHandler() {
        logger.info("Trying to get all cats");
    }

    @KafkaListener(topics = "topic-get-cats-by-breed")
    void getCatsBreedHandler(CatBreeds catBreed) {
        logger.info("Trying to get cat with breed: {}", catBreed);
    }

    @KafkaListener(topics = "topic-update-cat")
    void getCatsColorHandler(CatDtoInput catDto) {
        logger.info("Trying to update cat with birthday: {}", catDto.getBirthday());
    }

    @KafkaListener(topics = "topic-delete-cat-by-id")
    void deleteCatByIdHandler(Long id) {
        logger.info("Trying to get cat with id: {}", id);
    }
}
