package ru.isaev.OwnerRequestDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.isaev.OwnerDtos.OwnerDto;
import ru.isaev.OwnerDtos.OwnerDtoInput;

public class RequestOwnerWithDtoDto {
    private Long id;

    private OwnerDto dto;

    public RequestOwnerWithDtoDto() {
    }

    public RequestOwnerWithDtoDto(Long id, OwnerDto dto) {
        this.id = id;
        this.dto = dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OwnerDto getDto() {
        return dto;
    }

    public void setDto(OwnerDto dto) {
        this.dto = dto;
    }

    @JsonCreator
    public static RequestOwnerWithDtoDto Create(String jsonString) throws JsonProcessingException {

        RequestOwnerWithDtoDto requestDto = null;

        ObjectMapper mapper = new ObjectMapper();
        requestDto = mapper.readValue(jsonString, RequestOwnerWithDtoDto.class);


        return requestDto;
    }
}
