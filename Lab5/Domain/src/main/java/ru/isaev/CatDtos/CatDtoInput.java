package ru.isaev.CatDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.isaev.Cats.CatBreeds;
import ru.isaev.Cats.CatColors;

public class CatDtoInput {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("color")
    private CatColors color;

    @JsonProperty("breed")
    private CatBreeds breed;

    public CatDtoInput() {
    }

    public CatDtoInput(Long id, String birthday, CatColors color, CatBreeds breed) {
        this.id = id;
        this.birthday = birthday;
        this.color = color;
        this.breed = breed;
    }


    @JsonCreator
    public static CatDtoInput Create(String jsonString) throws JsonProcessingException {

        CatDtoInput cat = null;

        ObjectMapper mapper = new ObjectMapper();
        cat = mapper.readValue(jsonString, CatDtoInput.class);


        return cat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public CatColors getColor() {
        return color;
    }

    public void setColor(CatColors color) {
        this.color = color;
    }

    public CatBreeds getBreed() {
        return breed;
    }

    public void setBreed(CatBreeds breed) {
        this.breed = breed;
    }

}