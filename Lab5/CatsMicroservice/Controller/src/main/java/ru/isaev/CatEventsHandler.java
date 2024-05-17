package ru.isaev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.CatDtos.CatDtoInput;
import ru.isaev.CatRequestDtos.*;
import ru.isaev.Cats.Cat;
import ru.isaev.Cats.CatBreeds;
import ru.isaev.Cats.CatColors;
import ru.isaev.Mapper.IMyMapper;
import ru.isaev.Responses.CatResponse;

import java.util.ArrayList;
import java.util.List;

@Component
public class CatEventsHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private KafkaTemplate<String, Object> kafkaTemplate;

    private final CatService catService;

    private final IMyMapper mapper;

    private ObjectMapper objectMapper = new ObjectMapper();

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
    void getCatByIdHandler(String requestByIdJson) throws JsonProcessingException {
        RequestByIdDto requestById = objectMapper.readValue(requestByIdJson, RequestByIdDto.class);
        logger.info("Trying to get cat with id: {}", requestById.getCatId());

        CatDto cat = mapper.catToCatDto(catService.getCatById(requestById.getCatId()));

        List<CatDto> listOfCats = new ArrayList<>();
        listOfCats.add(cat);

        CatResponse catResponse = new CatResponse(requestById.getId(), listOfCats);
        String catResponseJson = objectMapper.writeValueAsString(catResponse);
        kafkaTemplate.send("topic-cat-response", catResponseJson);
    }

    // TODO. Требуется доарботка
    @KafkaListener(topics = "topic-get-all-cats")
    void getAllCatsHandler(String requestAllJson) throws JsonProcessingException {
        RequestAllDto requestAll = objectMapper.readValue(requestAllJson, RequestAllDto.class);
        logger.info("Trying to get all cats");

        List<CatDto> listOfCats= mapper.mapListOfCatsToListOfDtos(catService.getAllCats());

        CatResponse catResponse = new CatResponse(requestAll.getId(), listOfCats);
        String catResponseJson = objectMapper.writeValueAsString(catResponse);

        kafkaTemplate.send("topic-cat-response", catResponseJson);
    }

    @KafkaListener(topics = "topic-get-cats-by-color")
    void getCatsByColorHandler(String requestByColorJson) throws JsonProcessingException {
        RequestByColorDto requestByColor = objectMapper.readValue(requestByColorJson, RequestByColorDto.class);
        logger.info("Trying to get cats by color");

        List<CatDto> listOfCats= mapper.mapListOfCatsToListOfDtos(catService.getCatsByColor(requestByColor.getColor()));

        CatResponse catResponse = new CatResponse(requestByColor.getId(), listOfCats);
        String catResponseJson = objectMapper.writeValueAsString(catResponse);

        kafkaTemplate.send("topic-cat-response", catResponseJson);
    }

    @KafkaListener(topics = "topic-get-cats-by-breed")
    void getCatsByBreedHandler(String requestByBreedJson) throws JsonProcessingException {
        RequestByBreedDto requestByBreedDto = objectMapper.readValue(requestByBreedJson, RequestByBreedDto.class);
        logger.info("Trying to get cats by color");

        List<CatDto> listOfCats= mapper.mapListOfCatsToListOfDtos(catService.getCatsByBreed(requestByBreedDto.getBreeds()));

        CatResponse catResponse = new CatResponse(requestByBreedDto.getId(), listOfCats);
        String catResponseJson = objectMapper.writeValueAsString(catResponse);

        kafkaTemplate.send("topic-cat-response", catResponseJson);
    }

    // TODO. Требуется доработка (после обновления часть полей становится null)
    @KafkaListener(topics = "topic-update-cat")
    void updateCatHandler(String requestWithInputDtoDtoJson) throws JsonProcessingException {
        RequestWithInputDtoDto requestWithInputDtoDto = objectMapper.readValue(requestWithInputDtoDtoJson, RequestWithInputDtoDto.class);
        logger.info("Trying to update cat");

        Cat cat = mapper.catDtoInputToCat(requestWithInputDtoDto.getDto());
        cat = catService.updateCat(cat);

        CatDto catDto = mapper.catToCatDto(cat);
        List<CatDto> listOfCats = new ArrayList<>();
        listOfCats.add(catDto);

        CatResponse catResponse = new CatResponse(requestWithInputDtoDto.getId(), listOfCats);
        String catResponseJson = objectMapper.writeValueAsString(catResponse);

        kafkaTemplate.send("topic-cat-response", catResponseJson);
    }

    @KafkaListener(topics = "topic-delete-cat-by-id")
    void deleteCatByIdHandler(String requestByIdJson) throws JsonProcessingException {
        logger.info(requestByIdJson);
        RequestByIdDto requestById = objectMapper.readValue(requestByIdJson, RequestByIdDto.class);
        logger.info("Trying to delete cat with id: {}", requestById.getCatId());

        CatDto cat = mapper.catToCatDto(catService.getCatById(requestById.getCatId()));
        List<CatDto> listOfCats = new ArrayList<>();
        listOfCats.add(cat);

        catService.removeCatById(requestById.getCatId());

        CatResponse catResponse = new CatResponse(requestById.getId(), listOfCats);
        String catResponseJson = objectMapper.writeValueAsString(catResponse);
        kafkaTemplate.send("topic-cat-response", catResponseJson);
    }
}
