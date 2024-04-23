package ru.isaev.cats.rest;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.isaev.DAO.ICatDAO;
import ru.isaev.DAO.IOwnerDAO;
import ru.isaev.Entities.Cats.Cat;
import ru.isaev.Entities.Cats.CatBreeds;
import ru.isaev.Entities.Cats.CatColors;
import ru.isaev.Entities.Owners.Owner;
import ru.isaev.Service.CatService;
import ru.isaev.Service.OwnerService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


//TODO тесты для контроллеров
public class Tests {
    @Mock
    private ICatDAO catDAO;

    @InjectMocks
    private CatService catService;

    @Mock
    private IOwnerDAO ownerDAO;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    public void testGetCat() {
        Cat cat = new Cat();
        cat.setBirthday("2004-10-12");
        cat.setBreed(CatBreeds.BREED3);
        cat.setColor(CatColors.Black);

        when(catDAO.findById(1L)).thenReturn(Optional.of(cat));

        Optional<Cat> catFromDb = catService.getCatById(1L);

        assertNotNull(catFromDb);
    }

    @Test
    public void testGetOwner() {
        Owner owner = new Owner();
        owner.setBirthday("2000-10-12");
        owner.setFirstName("Daniel");
        owner.setLastName("Isaev");

        when(ownerDAO.findById(1L)).thenReturn(Optional.of(owner));

        Optional<Owner> ownerFromDb = ownerService.getOwnerById(1L);

        assertNotNull(ownerFromDb);
    }
}
