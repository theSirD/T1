package ru.isaev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.isaev.CatDtos.CatDtoInput;
import ru.isaev.Cats.Cat;
import ru.isaev.Cats.CatBreeds;
import ru.isaev.Cats.CatColors;
import ru.isaev.Mapper.IMyMapper;

@Component
public class CatEventsHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CatService catService;

    private final IMyMapper mapper;

    @Autowired
    public CatEventsHandler(CatService catService, IMyMapper mapper) {
        this.catService = catService;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "topic-add-cat")
    void addCatHandler(CatDtoInput catDto) {
        logger.info("Trying to add cat with birthday: {}", catDto.getBirthday());

        Cat cat = mapper.catDtoInputToCat(catDto);
        catService.addCat(cat);

        // TODO. Верни id
    }

    @KafkaListener(topics = "topic-get-cat-by-id")
    void getCatByIdHandler(Long id) {
        logger.info("Trying to get cat with id: {}", id);

        mapper.catToCatDto(catService.getCatById(id));

        // TODO. Верни кота
    }

    @KafkaListener(topics = "topic-get-cats-by-color")
    void getCatsByColorHandler(CatColors catColor) {
        logger.info("Trying to get cat with colors: {}", catColor);

        mapper.mapListOfCatsToListOfDtos(catService.getCatsByColor(catColor));

        // TODO. Верни котов
    }

    @KafkaListener(topics = "topic-get-all-cats")
    void getAllCatsHandler() {
        logger.info("Trying to get all cats");

        mapper.mapListOfCatsToListOfDtos(catService.getAllCats());

        // TODO. Верни котов
    }

    @KafkaListener(topics = "topic-get-cats-by-breed")
    void getCatsByBreedHandler(CatBreeds catBreed) {
        logger.info("Trying to get cat with breed: {}", catBreed);

        mapper.mapListOfCatsToListOfDtos(catService.getCatsByBreed(catBreed));
    }

    @KafkaListener(topics = "topic-update-cat")
    void updateCatHandler(CatDtoInput catDto) {
        logger.info("Trying to update cat with birthday: {}", catDto.getBirthday());

        Cat cat = mapper.catDtoInputToCat(catDto);
        catService.updateCat(cat);

        // TODO. Верни id кота
    }

    @KafkaListener(topics = "topic-delete-cat-by-id")
    void deleteCatByIdHandler(Long id) {
        logger.info("Trying to get cat with id: {}", id);

        catService.removeCatById(id);
    }
}
