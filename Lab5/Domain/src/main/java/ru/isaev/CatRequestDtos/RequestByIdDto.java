package ru.isaev.CatRequestDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.isaev.CatDtos.CatDtoInput;

public class RequestByIdDto {
    private Long id;

    private Long catId;

    public RequestByIdDto() {
    }

    public RequestByIdDto(Long id, Long catId) {
        this.id = id;
        this.catId = catId;
    }

    @JsonCreator
    public static RequestByIdDto Create(String jsonString) throws JsonProcessingException {

        RequestByIdDto requestDto = null;

        ObjectMapper mapper = new ObjectMapper();
        requestDto = mapper.readValue(jsonString, RequestByIdDto.class);


        return requestDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }
}
