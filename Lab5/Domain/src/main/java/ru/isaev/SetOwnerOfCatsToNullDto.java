package ru.isaev;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.isaev.OwnerDtos.OwnerDto;

public class SetOwnerOfCatsToNullDto {
    private OwnerDto ownerDto;

    public SetOwnerOfCatsToNullDto() {
    }

    public SetOwnerOfCatsToNullDto(OwnerDto ownerDto) {
        this.ownerDto = ownerDto;
    }

    public OwnerDto getOwnerDto() {
        return ownerDto;
    }

    public void setOwnerDto(OwnerDto ownerDto) {
        this.ownerDto = ownerDto;
    }

    @JsonCreator
    public static SetOwnerOfCatsToNullDto Create(String jsonString) throws JsonProcessingException {

        SetOwnerOfCatsToNullDto setOwnerOfCatsToNullDto = null;

        ObjectMapper mapper = new ObjectMapper();
        setOwnerOfCatsToNullDto = mapper.readValue(jsonString, SetOwnerOfCatsToNullDto.class);


        return setOwnerOfCatsToNullDto;
    }
}
