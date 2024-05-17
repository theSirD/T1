package ru.isaev;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.OwnerDtos.OwnerDto;

public class GetOwnerOfCatDto {
    private CatDto catDto;

    public GetOwnerOfCatDto() {
    }

    public GetOwnerOfCatDto(CatDto catDto) {
        this.catDto = catDto;
    }

    public CatDto getCatDto() {
        return catDto;
    }

    public void setCatDto(CatDto catDto) {
        this.catDto = catDto;
    }

    @JsonCreator
    public static GetOwnerOfCatDto Create(String jsonString) throws JsonProcessingException {

        GetOwnerOfCatDto getOwnerOfCatDto = null;

        ObjectMapper mapper = new ObjectMapper();
        getOwnerOfCatDto = mapper.readValue(jsonString, GetOwnerOfCatDto.class);


        return getOwnerOfCatDto;
    }
}
