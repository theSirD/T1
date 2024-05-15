package ru.isaev.CatRequestDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.isaev.Cats.CatColors;

public class RequestByColorDto {
    private Long id;

    private CatColors color;

    public RequestByColorDto() {
    }

    public RequestByColorDto(Long id, CatColors color) {
        this.id = id;
        this.color = color;
    }

    @JsonCreator
    public static RequestByColorDto Create(String jsonString) throws JsonProcessingException {

        RequestByColorDto requestDto = null;

        ObjectMapper mapper = new ObjectMapper();
        requestDto = mapper.readValue(jsonString, RequestByColorDto.class);


        return requestDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatColors getColor() {
        return color;
    }

    public void setColor(CatColors color) {
        this.color = color;
    }
}
