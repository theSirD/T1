package ru.isaev.Mapper;

import ru.isaev.CatDtos.CatDto;
import ru.isaev.CatDtos.CatDtoInput;
import ru.isaev.Cats.Cat;
import ru.isaev.OwnerDtos.OwnerDto;
import ru.isaev.Owners.Owner;

import java.util.List;

public interface IMyMapper {
    CatDto catToCatDto(Cat cat);

    List<CatDto> mapListOfCatsToListOfDtos(List<Cat> listOfCats);

    Cat catDtoInputToCat(CatDtoInput catDtoInput);

    OwnerDto ownerToOwnerDto(Owner owner);

    List<OwnerDto> mapListOfOwnersToListOfDtos(List<Owner> listOfOwners);

    Owner ownerDtoToOwner(OwnerDto ownerDto);
}