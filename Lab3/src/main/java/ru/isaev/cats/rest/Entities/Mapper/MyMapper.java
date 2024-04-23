package ru.isaev.cats.rest.Entities.Mapper;

import org.springframework.stereotype.Component;
import ru.isaev.cats.rest.Entities.CatDtos.CatDto;
import ru.isaev.cats.rest.Entities.CatDtos.CatDtoInput;
import ru.isaev.cats.rest.Entities.Cats.Cat;
import ru.isaev.cats.rest.Entities.OwnerDtos.OwnerDto;
import ru.isaev.cats.rest.Entities.Owners.Owner;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class MyMapper implements IMyMapper {

    private final DatatypeFactory datatypeFactory;

    public MyMapper() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public CatDto catToCatDto(Cat cat) {
        if ( cat == null ) {
            return null;
        }

        CatDto catDto = new CatDto();

        catDto.setId( cat.getId() );
        catDto.setBirthday( cat.getBirthday()  );
        catDto.setColor( cat.getColor() );
        catDto.setBreed( cat.getBreed() );
        catDto.setOwnerId(cat.getOwner().getId());

        Set<Cat> listOfFriends = cat.getFriendsList();
        Set<Long> idsOfFriends = new HashSet<>();

        for (Cat friend:
             listOfFriends) {
            idsOfFriends.add(friend.getId());
        }

        catDto.setFriends(idsOfFriends);


        return catDto;
    }

    @Override
    public List<CatDto> mapListOfCatsToListOfDtos(List<Cat> listOfCats) {
        List<CatDto> listOfCatDtos = new ArrayList<>();

        for (Cat cat :
                listOfCats) {
            listOfCatDtos.add(catToCatDto(cat));
        }

        return listOfCatDtos;
    }

    @Override
    public Cat catDtoInputToCat(CatDtoInput catDtoInput) {
        if ( catDtoInput == null ) {
            return null;
        }

        Cat cat = new Cat();

        cat.setId( catDtoInput.getId() );
        cat.setBirthday( catDtoInput.getBirthday() );
        cat.setColor( catDtoInput.getColor() );
        cat.setBreed( catDtoInput.getBreed() );
        cat.setOwner(null);
        cat.setFriendsList(null);

        return cat;
    }

    @Override
    public OwnerDto ownerToOwnerDto(Owner owner) {
        if ( owner == null ) {
            return null;
        }

        OwnerDto ownerDto = new OwnerDto();

        ownerDto.setId( owner.getId() );
        ownerDto.setBirthday( owner.getBirthday()  );
        ownerDto.setFirst_name( owner.getFirst_name() );
        ownerDto.setLast_name( owner.getLast_name() );
        ownerDto.setId(owner.getId());

        Set<Cat> listOfCats = owner.getCatsList();
        Set<Long> idsOfcats = new HashSet<>();

        for (Cat cat:
                listOfCats) {
            idsOfcats.add(cat.getId());
        }

        ownerDto.setIdsOfCatsList(idsOfcats);

        return ownerDto;
    }

    @Override
    public List<OwnerDto> mapListOfOwnersToListOfDtos(List<Owner> listOfOwners) {
        List<OwnerDto> listOfOwnerDtos = new ArrayList<>();

        for (Owner owner :
                listOfOwners) {
            listOfOwnerDtos.add(ownerToOwnerDto(owner));
        }

        return listOfOwnerDtos;
    }

    @Override
    public Owner ownerDtoToOwner(OwnerDto ownerDto) {
        if ( ownerDto == null ) {
            return null;
        }

        Owner owner = new Owner(
                ownerDto.getId(),
                ownerDto.getFirst_name(),
                ownerDto.getLast_name(),
                ownerDto.getBirthday(),
                null
        );

        return owner;
    }


//    @Override
//    public OwnerDto ownerToOwnerDto(Owner owner) {
//        if ( owner == null ) {
//            return null;
//        }
//
//        OwnerDto ownerDto = new OwnerDto();
//
//        ownerDto.setId(owner.getId());
//        ownerDto.setFirst_name(owner.getFirst_name());
//        ownerDto.setLast_name(owner.getLast_name());
//        ownerDto.setBirthday( xmlGregorianCalendarToString( dateToXmlGregorianCalendar( owner.getBirthday() ), null ) );
//
////        ownerDto.setCats(owner.getCatsList());
//
//        Set<Cat> catsList = owner.getCatsList();
//        Set<Long> idsOfCats = new HashSet<>();
//
//        for (Cat cat:
//                catsList) {
//            idsOfCats.add(cat.getId());
//        }
//
//        ownerDto.setCats(idsOfCats);
//
//        return ownerDto;
//    }

    private String xmlGregorianCalendarToString(XMLGregorianCalendar xcal, String dateFormat ) {
        if ( xcal == null ) {
            return null;
        }

        if (dateFormat == null ) {
            return xcal.toString();
        }
        else {
            Date d = xcal.toGregorianCalendar().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat( dateFormat );
            return sdf.format( d );
        }
    }

    private XMLGregorianCalendar dateToXmlGregorianCalendar( Date date ) {
        if ( date == null ) {
            return null;
        }

        GregorianCalendar c = new GregorianCalendar();
        c.setTime( date );
        return datatypeFactory.newXMLGregorianCalendar( c );
    }
}
