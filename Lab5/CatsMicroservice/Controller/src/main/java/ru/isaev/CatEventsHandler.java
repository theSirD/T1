package ru.isaev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.CatDtos.CatDtoInput;
import ru.isaev.Cats.Cat;
import ru.isaev.Cats.CatBreeds;
import ru.isaev.Cats.CatColors;
import ru.isaev.Mapper.IMyMapper;

import java.util.List;

@Component
public class CatEventsHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private KafkaTemplate<String, Object> kafkaTemplate;

    private final CatService catService;

    private final IMyMapper mapper;

    @Autowired
    public CatEventsHandler(KafkaTemplate<String, Object> kafkaTemplate, CatService catService, IMyMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.catService = catService;
        this.mapper = mapper;
    }

    // TODO. Добавь логику сохранения кота
//    @KafkaListener(topics = "topic-add-cat")
//    void addCatHandler(CatDtoInput catDto) {
//        logger.info("Trying to add cat with birthday: {}", catDto.getBirthday());
//
//        Cat cat = mapper.catDtoInputToCat(catDto);
//        cat = catService.addCat(cat);
//
//        // TODO. Верни id
//    }

    @KafkaListener(topics = "topic-get-cat-by-id")
    void getCatByIdHandler(Long id) throws JsonProcessingException {
        logger.info("Trying to get cat with id: {}", id);

        CatDto cat = mapper.catToCatDto(catService.getCatById(id));
//        ObjectMapper objectMapper = new ObjectMapper();

//        String json = objectMapper.writeValueAsString(cat);

        kafkaTemplate.send("topic-cat-response", cat);
    }

    @KafkaListener(topics = "topic-get-cats-by-color")
    void getCatsByColorHandler(int index) throws JsonProcessingException {
        logger.info("Trying to get cat with colors: {}", CatColors.values()[(int)  index]);

        List<CatDto> listOfDtos = mapper.mapListOfCatsToListOfDtos(catService.getCatsByColor(CatColors.values()[(int) (long) index]));
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonArray = objectMapper.writeValueAsString(listOfDtos);

        kafkaTemplate.send("topic-list-of-cats-response", jsonArray);
    }

    @KafkaListener(topics = "topic-get-all-cats")
    void getAllCatsHandler() {
        logger.info("Trying to get all cats");

        List<CatDto> listOfDtos = mapper.mapListOfCatsToListOfDtos(catService.getAllCats());

        kafkaTemplate.send("topic-list-of-cats-response", listOfDtos);
    }

    @KafkaListener(topics = "topic-get-cats-by-breed")
    void getCatsByBreedHandler(CatBreeds catBreed) {
        logger.info("Trying to get cat with breed: {}", catBreed);

        List<CatDto> listOfDtos = mapper.mapListOfCatsToListOfDtos(catService.getCatsByBreed(catBreed));

        kafkaTemplate.send("topic-list-of-cats-response", listOfDtos);
    }

    @KafkaListener(topics = "topic-update-cat")
    void updateCatHandler(CatDtoInput catDto) {
        logger.info("Trying to update cat with birthday: {}", catDto.getBirthday());

        Cat cat = mapper.catDtoInputToCat(catDto);
        cat = catService.updateCat(cat);

        kafkaTemplate.send("topic-cat-id-response", cat.getId());
    }

    @KafkaListener(topics = "topic-delete-cat-by-id")
    void deleteCatByIdHandler(Long id) {
        logger.info("Trying to get cat with id: {}", id);

        catService.removeCatById(id);
    }
}
