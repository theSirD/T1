package ru.isaev.CatDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Basic;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import ru.isaev.Cats.CatBreeds;
import ru.isaev.Cats.CatColors;

import java.util.List;

@Entity
public class CatDto {
    @Id
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
    @ElementCollection
    private List<Long> idsOfFriendsList;

    public CatDto() {
    }

    public CatDto(Long id, Long ownerId, String birthday, CatColors color, CatBreeds breed, List<Long> idsOfFriendsList) {
        this.id = id;
        this.ownerId = ownerId;
        this.birthday = birthday;
        this.color = color;
        this.breed = breed;
        this.idsOfFriendsList = idsOfFriendsList;
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
        return idsOfFriendsList;
    }

    public void setFriends(List<Long> idsOfFriendsList) {
        this.idsOfFriendsList = idsOfFriendsList;
    }
}