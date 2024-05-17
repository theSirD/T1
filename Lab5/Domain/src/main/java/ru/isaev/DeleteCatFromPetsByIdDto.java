package ru.isaev;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.CatRequestDtos.RequestAllDto;
import ru.isaev.OwnerDtos.OwnerDto;

public class DeleteCatFromPetsByIdDto {
    private OwnerDto ownerDto;

    private CatDto catDto;

    public DeleteCatFromPetsByIdDto() {
    }

    public DeleteCatFromPetsByIdDto(OwnerDto ownerDto, CatDto catDto) {
        this.ownerDto = ownerDto;
        this.catDto = catDto;
    }

    public OwnerDto getOwnerDto() {
        return ownerDto;
    }

    public void setOwnerDto(OwnerDto ownerDto) {
        this.ownerDto = ownerDto;
    }

    public CatDto getCatDto() {
        return catDto;
    }

    public void setCatDto(CatDto catDto) {
        this.catDto = catDto;
    }

    @JsonCreator
    public static DeleteCatFromPetsByIdDto Create(String jsonString) throws JsonProcessingException {

        DeleteCatFromPetsByIdDto deleteCatFromPetsByIdDto = null;

        ObjectMapper mapper = new ObjectMapper();
        deleteCatFromPetsByIdDto = mapper.readValue(jsonString, DeleteCatFromPetsByIdDto.class);


        return deleteCatFromPetsByIdDto;
    }
}
