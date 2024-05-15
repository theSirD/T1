package ru.isaev.CatRequestDtos;

import ru.isaev.Cats.CatColors;

public class RequestByColorDto {
    private Long id;

    private CatColors color;

    public RequestByColorDto() {
    }

    public RequestByColorDto(Long id, CatColors color) {
        this.id = id;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatColors getColor() {
        return color;
    }

    public void setColor(CatColors color) {
        this.color = color;
    }
}
