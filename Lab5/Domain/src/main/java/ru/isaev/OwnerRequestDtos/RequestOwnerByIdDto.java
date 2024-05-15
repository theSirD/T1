package ru.isaev.OwnerRequestDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestOwnerByIdDto {
    private Long id;

    private Long ownerId;

    public RequestOwnerByIdDto() {
    }

    public RequestOwnerByIdDto(Long id, Long ownerId) {
        this.id = id;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }


    @JsonCreator
    public static RequestOwnerByIdDto Create(String jsonString) throws JsonProcessingException {

        RequestOwnerByIdDto requestDto = null;

        ObjectMapper mapper = new ObjectMapper();
        requestDto = mapper.readValue(jsonString, RequestOwnerByIdDto.class);

        return requestDto;
    }

}




