package ru.isaev.cats.rest.Entities.CatDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.isaev.cats.rest.Entities.Cats.CatBreeds;
import ru.isaev.cats.rest.Entities.Cats.CatColors;

import java.util.List;
import java.util.Set;

public class CatDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("ownerId")
    private Long ownerId;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("color")
    private CatColors color;

    @JsonProperty("breed")
    private CatBreeds breed;

    @JsonProperty("idsOfFriendsList")
    private List<Long> friends;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long owner_id) {
        this.ownerId = owner_id;
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

    public List<Long> getFriends() {
        return friends;
    }

    public void setFriends(List<Long> friends) {
        this.friends = friends;
    }
}