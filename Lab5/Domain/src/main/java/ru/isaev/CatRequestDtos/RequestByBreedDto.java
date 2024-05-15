package ru.isaev.CatRequestDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.isaev.Cats.CatBreeds;

public class RequestByBreedDto {
    private Long id;

    private CatBreeds breeds;

    public RequestByBreedDto() {
    }

    public RequestByBreedDto(Long id, CatBreeds breeds) {
        this.id = id;
        this.breeds = breeds;
    }

    @JsonCreator
    public static RequestByBreedDto Create(String jsonString) throws JsonProcessingException {

        RequestByBreedDto requestDto = null;

        ObjectMapper mapper = new ObjectMapper();
        requestDto = mapper.readValue(jsonString, RequestByBreedDto.class);


        return requestDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatBreeds getBreeds() {
        return breeds;
    }

    public void setBreeds(CatBreeds breeds) {
        this.breeds = breeds;
    }
}
