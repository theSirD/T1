package ru.isaev.CatRequestDtos;

import ru.isaev.CatDtos.CatDtoInput;

public class RequestWithInputDtoDto {
    private Long id;

    private CatDtoInput dto;

    public RequestWithInputDtoDto() {
    }

    public RequestWithInputDtoDto(Long id, CatDtoInput dto) {
        this.id = id;
        this.dto = dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatDtoInput getDto() {
        return dto;
    }

    public void setDto(CatDtoInput dto) {
        this.dto = dto;
    }
}
