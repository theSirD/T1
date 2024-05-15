package ru.isaev.CatRequestDtos;

import ru.isaev.Cats.CatBreeds;

public class RequestByBreedDto {
    private Long id;

    private CatBreeds breeds;

    public RequestByBreedDto() {
    }

    public RequestByBreedDto(Long id, CatBreeds breeds) {
        this.id = id;
        this.breeds = breeds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatBreeds getBreeds() {
        return breeds;
    }

    public void setBreeds(CatBreeds breeds) {
        this.breeds = breeds;
    }
}
