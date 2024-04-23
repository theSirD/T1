package ru.isaev.cats.rest.Entities.OwnerDtos;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class OwnerDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("first_name")
    private String first_name;

    @JsonProperty("last_name")
    private String last_name;

    @JsonProperty("idsOfCatsList")
    private Set<Long> idsOfCatsList;

    public Set<Long> getIdsOfCatsList() {
        return idsOfCatsList;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setIdsOfCatsList(Set<Long> idsOfCatsList) {
        this.idsOfCatsList = idsOfCatsList;
    }

    public void setCats(Set<Long> cats) {
        this.idsOfCatsList = cats;
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


    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}