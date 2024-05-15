package ru.isaev.Responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import ru.isaev.CatDtos.CatDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CatResponses")
public class CatResponse {
    @Id
    private Long requestId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cats_for_request",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "cat_id")

    )
    private List<CatDto> catsList = new ArrayList<>();

    public CatResponse() {
    }

    public CatResponse(Long requestId, List<CatDto> catsList) {
        this.requestId = requestId;
        this.catsList = catsList;
    }

    @JsonCreator
    public static CatResponse Create(String jsonString) throws JsonProcessingException {

        CatResponse response = null;

        ObjectMapper mapper = new ObjectMapper();
        response = mapper.readValue(jsonString, CatResponse.class);


        return response;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public List<CatDto> getCatsList() {
        return catsList;
    }

    public void setCatsList(List<CatDto> catsList) {
        this.catsList = catsList;
    }
}
