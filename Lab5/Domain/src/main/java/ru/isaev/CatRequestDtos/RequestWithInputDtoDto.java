package ru.isaev.CatRequestDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.isaev.CatDtos.CatDtoInput;

public class RequestWithInputDtoDto {
    private Long id;

    private CatDtoInput dto;

    public RequestWithInputDtoDto() {
    }

    public RequestWithInputDtoDto(Long id, CatDtoInput dto) {
        this.id = id;
        this.dto = dto;
    }

    @JsonCreator
    public static RequestWithInputDtoDto Create(String jsonString) throws JsonProcessingException {

        RequestWithInputDtoDto requestDto = null;

        ObjectMapper mapper = new ObjectMapper();
        requestDto = mapper.readValue(jsonString, RequestWithInputDtoDto.class);


        return requestDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatDtoInput getDto() {
        return dto;
    }

    public void setDto(CatDtoInput dto) {
        this.dto = dto;
    }
}
