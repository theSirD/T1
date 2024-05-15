package ru.isaev.Responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.OwnerDtos.OwnerDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "OwnersResponses")
public class OwnerResponse {
    @Id
    private Long requestId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "owners_for_request",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_id")

    )
    private List<OwnerDto> ownersList = new ArrayList<>();

    @JsonCreator
    public static OwnerResponse Create(String jsonString) throws JsonProcessingException {

        OwnerResponse response = null;

        ObjectMapper mapper = new ObjectMapper();
        response = mapper.readValue(jsonString, OwnerResponse.class);


        return response;
    }


    public OwnerResponse() {
    }

    public OwnerResponse(Long requestId, List<OwnerDto> ownersList) {
        this.requestId = requestId;
        this.ownersList = ownersList;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public List<OwnerDto> getOwnersList() {
        return ownersList;
    }

    public void setOwnersList(List<OwnerDto> ownersList) {
        this.ownersList = ownersList;
    }
}
