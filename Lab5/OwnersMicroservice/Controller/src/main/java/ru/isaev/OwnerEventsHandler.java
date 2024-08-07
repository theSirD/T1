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
import ru.isaev.CatRequestDtos.RequestByIdDto;
import ru.isaev.Cats.Cat;
import ru.isaev.Mapper.IMyMapper;
import ru.isaev.OwnerDtos.OwnerDto;
import ru.isaev.OwnerRequestDtos.RequestOwnerByIdDto;
import ru.isaev.OwnerRequestDtos.RequestOwnerWithDtoDto;
import ru.isaev.Owners.Owner;
import ru.isaev.Responses.CatResponse;
import ru.isaev.Responses.OwnerResponse;

import java.util.ArrayList;
import java.util.List;

@Component
public class OwnerEventsHandler {
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OwnerService ownerService;

    private final IMyMapper mapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public OwnerEventsHandler(KafkaTemplate<String, Object> kafkaTemplate, OwnerService ownerService, IMyMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.ownerService = ownerService;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "topic-get-owner-by-id", groupId = "group-id")
    void getOwnerByIdHandler(String requestByIdJson) throws JsonProcessingException {
        RequestOwnerByIdDto requestById = objectMapper.readValue(requestByIdJson, RequestOwnerByIdDto.class);

        OwnerDto owner = mapper.ownerToOwnerDto(ownerService.getOwnerById(requestById.getOwnerId()));

        List<OwnerDto> listOfOwners = new ArrayList<>();
        listOfOwners.add(owner);

        OwnerResponse ownerResponse = new OwnerResponse(requestById.getId(), listOfOwners);
        String ownerResponseJson = objectMapper.writeValueAsString(ownerResponse);
        kafkaTemplate.send("topic-owner-response", ownerResponseJson);
    }

    @KafkaListener(topics = "topic-add-owner", groupId = "group-id")
    void addOwnerHandler(String requestWithDtoDtoJson) throws JsonProcessingException {
        RequestOwnerWithDtoDto requestWithDtoDto = objectMapper.readValue(requestWithDtoDtoJson, RequestOwnerWithDtoDto.class);

        Owner owner = mapper.ownerDtoToOwner(requestWithDtoDto.getDto());
        owner = ownerService.addOwner(owner);
        List<Cat> emptyList = new ArrayList<>();
        owner.setCatsList(emptyList);
        OwnerDto ownerDto = mapper.ownerToOwnerDto(owner);
        List<OwnerDto> listOfOwners = new ArrayList<>();
        listOfOwners.add(ownerDto);

        OwnerResponse ownerResponse = new OwnerResponse(requestWithDtoDto.getId(), listOfOwners);
        String ownerResponseJson = objectMapper.writeValueAsString(ownerResponse);

        kafkaTemplate.send("topic-owner-response", ownerResponseJson);
    }

    @KafkaListener(topics = "topic-update-owner", groupId = "group-id")
    void updateOwnerHandler(String requestWithDtoDtoJson) throws JsonProcessingException {
        RequestOwnerWithDtoDto requestWithDtoDto = objectMapper.readValue(requestWithDtoDtoJson, RequestOwnerWithDtoDto.class);

        Owner owner = mapper.ownerDtoToOwner(requestWithDtoDto.getDto());
        owner = ownerService.updateOwner(owner);
        List<Cat> emptyList = new ArrayList<>();
        owner.setCatsList(emptyList);

        OwnerDto ownerDto = mapper.ownerToOwnerDto(owner);
        List<OwnerDto> listOfOwners = new ArrayList<>();
        listOfOwners.add(ownerDto);

        OwnerResponse ownerResponse = new OwnerResponse(requestWithDtoDto.getId(), listOfOwners);
        String ownerResponseJson = objectMapper.writeValueAsString(ownerResponse);

        kafkaTemplate.send("topic-owner-response", ownerResponseJson);
    }

    @KafkaListener(topics = "topic-delete-owner-by-id", groupId = "group-id")
    void deleteOwnerByIdHandler(String requestByIdJson) throws JsonProcessingException {
        RequestOwnerByIdDto requestById = objectMapper.readValue(requestByIdJson, RequestOwnerByIdDto.class);

        OwnerDto owner = mapper.ownerToOwnerDto(ownerService.getOwnerById(requestById.getOwnerId()));
        List<OwnerDto> listOfOwners = new ArrayList<>();
        listOfOwners.add(owner);

        ownerService.removeOwnerById(requestById.getOwnerId());

        OwnerResponse ownerResponse = new OwnerResponse(requestById.getId(), listOfOwners);
        String ownerResponseJson = objectMapper.writeValueAsString(ownerResponse);
        kafkaTemplate.send("topic-owner-response", ownerResponseJson);
    }

    @KafkaListener(topics = "topic-delete-cat-from-list-of-pets-by-id", groupId = "group-id")
    void deleteCatFromPetsByIdHandler(String deleteCatFromPetsByIdDtoJson) throws JsonProcessingException {
        DeleteCatFromPetsByIdDto deleteCatFromPetsByIdDto = objectMapper.readValue(deleteCatFromPetsByIdDtoJson, DeleteCatFromPetsByIdDto.class);

        Owner owner = ownerService.getOwnerById(deleteCatFromPetsByIdDto.getOwnerDto().getId());
        List<Cat> pets = owner.getCatsList();
        Cat catToBeDeleted = null;
        for (Cat cat :
                pets) {
            if (cat.getId() == deleteCatFromPetsByIdDto.getCatDto().getId()) {
                catToBeDeleted = cat;
                break;
            };
        }

        pets.remove(catToBeDeleted);
        owner.setCatsList(pets);
        ownerService.updateOwner(owner);

        logger.info("Operation complete");
    }
}
