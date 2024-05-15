package ru.isaev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.CatRequestDtos.RequestByIdDto;
import ru.isaev.Mapper.IMyMapper;
import ru.isaev.OwnerDtos.OwnerDto;
import ru.isaev.OwnerRequestDtos.RequestOwnerByIdDto;
import ru.isaev.OwnerRequestDtos.RequestOwnerWithDtoDto;
import ru.isaev.Owners.Owner;
import ru.isaev.Responses.CatResponse;
import ru.isaev.Responses.OwnerResponse;

import java.util.ArrayList;
import java.util.List;

// TODO. Почему без groupId над всеми методами приложение не запускается?
@Component
public class OwnerEventsHandler {
    private KafkaTemplate<String, Object> kafkaTemplate;

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
    void getCatByIdHandler(String requestByIdJson) throws JsonProcessingException {
        RequestOwnerByIdDto requestById = objectMapper.readValue(requestByIdJson, RequestOwnerByIdDto.class);

        OwnerDto owner = mapper.ownerToOwnerDto(ownerService.getOwnerById(requestById.getOwnerId()));

        List<OwnerDto> listOfOwners = new ArrayList<>();
        listOfOwners.add(owner);

        OwnerResponse ownerResponse = new OwnerResponse(requestById.getId(), listOfOwners);
        String ownerResponseJson = objectMapper.writeValueAsString(ownerResponse);
        kafkaTemplate.send("topic-owner-response", ownerResponseJson);
    }

    // TODO. Требуется доработка (после обновления часть полей становится null)
    @KafkaListener(topics = "topic-update-owner", groupId = "group-id")
    void updateCatHandler(String requestWithInputDtoDtoJson) throws JsonProcessingException {
        RequestOwnerWithDtoDto requestWithDtoDto = objectMapper.readValue(requestWithInputDtoDtoJson, RequestOwnerWithDtoDto.class);

        Owner owner = mapper.ownerDtoToOwner(requestWithDtoDto.getDto());
        owner = ownerService.updateOwner(owner);

        OwnerDto ownerDto = mapper.ownerToOwnerDto(owner);
        List<OwnerDto> listOfOwners = new ArrayList<>();
        listOfOwners.add(ownerDto);

        OwnerResponse ownerResponse = new OwnerResponse(requestWithDtoDto.getId(), listOfOwners);
        String ownerResponseJson = objectMapper.writeValueAsString(ownerResponse);

        kafkaTemplate.send("topic-owner-response", ownerResponseJson);
    }

    // TODO. Допиши
    @KafkaListener(topics = "topic-delete-owner-by-id", groupId = "group-id")
    void deleteCatByIdHandler(String requestByIdJson) throws JsonProcessingException {
        RequestOwnerByIdDto requestById = objectMapper.readValue(requestByIdJson, RequestOwnerByIdDto.class);

        OwnerDto owner = mapper.ownerToOwnerDto(ownerService.getOwnerById(requestById.getOwnerId()));
        List<OwnerDto> listOfOwners = new ArrayList<>();
        listOfOwners.add(owner);

        ownerService.removeOwnerById(requestById.getOwnerId());

        OwnerResponse ownerResponse = new OwnerResponse(requestById.getId(), listOfOwners);
        String ownerResponseJson = objectMapper.writeValueAsString(ownerResponse);
        kafkaTemplate.send("topic-owner-response", ownerResponseJson);
    }
}
