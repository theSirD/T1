package ru.isaev.Mapper;

import org.springframework.stereotype.Component;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.CatDtos.CatDtoInput;
import ru.isaev.Cats.Cat;
import ru.isaev.OwnerDtos.OwnerDto;
import ru.isaev.Owners.Owner;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
        catDto.setBirthday( cat.getBirthday().toString()  );
        catDto.setColor( cat.getColor() );
        catDto.setBreed( cat.getBreed() );

        Owner owner = cat.getOwner();
        if (owner != null)
            catDto.setOwnerId(cat.getOwner().getId());

        List<Cat> listOfFriends = cat.getFriendsList();
        List<Long> idsOfFriends = new ArrayList<>();

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
        cat.setColor( catDtoInput.getColor() );
        cat.setBreed( catDtoInput.getBreed() );
        cat.setOwner(null);
        cat.setFriendsList(null);


        String birthday = catDtoInput.getBirthday();
        try {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(birthday, formatter);
            cat.setBirthday(date);
        }
        catch (DateTimeParseException exc) {
            System.out.printf("%s is not parsable!%n", birthday);
            throw exc;
        }

        return cat;
    }

    @Override
    public OwnerDto ownerToOwnerDto(Owner owner) {
        if ( owner == null ) {
            return null;
        }

        OwnerDto ownerDto = new OwnerDto();

        ownerDto.setId( owner.getId() );
        ownerDto.setBirthday( owner.getBirthday().toString()  );
        ownerDto.setFirst_name( owner.getFirst_name() );
        ownerDto.setLast_name( owner.getLast_name() );
        ownerDto.setId(owner.getId());

        List<Cat> listOfCats = owner.getCatsList();
        List<Long> idsOfcats = new ArrayList<>();

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

        Owner owner = new Owner();
        String birthday = ownerDto.getBirthday();
        try {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(birthday, formatter);
            owner.setBirthday(date);
        }
        catch (DateTimeParseException exc) {
            System.out.printf("%s is not parsable!%n", birthday);
            throw exc;
        }


        owner.setId( ownerDto.getId() );
        owner.setFirstName( ownerDto.getFirst_name() );
        owner.setLastName( ownerDto.getLast_name() );
        owner.setCatsList(null);
        owner.setPassword(ownerDto.getPassword());
        owner.setRole(ownerDto.getRole());

        return owner;
    }

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
