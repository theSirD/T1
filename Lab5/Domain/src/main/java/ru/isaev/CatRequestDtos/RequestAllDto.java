package ru.isaev.CatRequestDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestAllDto {
    private Long id;

    public RequestAllDto() {
    }

    public RequestAllDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @JsonCreator
    public static RequestAllDto Create(String jsonString) throws JsonProcessingException {

        RequestAllDto requestDto = null;

        ObjectMapper mapper = new ObjectMapper();
        requestDto = mapper.readValue(jsonString, RequestAllDto.class);


        return requestDto;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
