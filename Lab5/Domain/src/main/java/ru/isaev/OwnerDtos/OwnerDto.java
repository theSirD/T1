package ru.isaev.OwnerDtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import ru.isaev.Owners.Roles;

import java.util.List;

@Entity
public class OwnerDto {
    @Id
    private Long id;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("first_name")
    private String first_name;

    @JsonProperty("last_name")
    private String last_name;

    @JsonProperty("idsOfCatsList")
    @ElementCollection
    private List<Long> idsOfCatsList;

    public OwnerDto() {
    }

    public OwnerDto(Long id, String birthday, String first_name, String last_name, List<Long> idsOfCatsList, String password, Roles role) {
        this.id = id;
        this.birthday = birthday;
        this.first_name = first_name;
        this.last_name = last_name;
        this.idsOfCatsList = idsOfCatsList;
        this.password = password;
        this.role = role;
    }

    private String password;

    private Roles role;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public List<Long> getIdsOfCatsList() {
        return idsOfCatsList;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setIdsOfCatsList(List<Long> idsOfCatsList) {
        this.idsOfCatsList = idsOfCatsList;
    }

    public void setCats(List<Long> cats) {
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