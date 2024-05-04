package ru.isaev.Mapper;

import org.springframework.stereotype.Component;
import ru.isaev.Entities.CatDtos.CatDto;
import ru.isaev.Entities.CatDtos.*;
import ru.isaev.Entities.Cats.Cat;
import ru.isaev.Entities.OwnerDtos.OwnerDto;
import ru.isaev.Entities.Owners.Owner;

import java.util.List;

@Component
public interface IMyMapper {
    CatDto catToCatDto(Cat cat);

    List<CatDto> mapListOfCatsToListOfDtos(List<Cat> listOfCats);

    Cat catDtoInputToCat(CatDtoInput catDtoInput);

    OwnerDto ownerToOwnerDto(Owner owner);

    List<OwnerDto> mapListOfOwnersToListOfDtos(List<Owner> listOfOwners);

    Owner ownerDtoToOwner(OwnerDto ownerDto);
}