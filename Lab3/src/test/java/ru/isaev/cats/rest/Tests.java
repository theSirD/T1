package ru.isaev.cats.rest;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import ru.isaev.cats.rest.Controllers.CatController;
import ru.isaev.cats.rest.Controllers.OwnerController;
import ru.isaev.cats.rest.DAO.ICatDAO;
import ru.isaev.cats.rest.DAO.IOwnerDAO;
import ru.isaev.cats.rest.Entities.CatDtos.CatDto;
import ru.isaev.cats.rest.Entities.Cats.Cat;
import ru.isaev.cats.rest.Entities.Cats.CatBreeds;
import ru.isaev.cats.rest.Entities.Cats.CatColors;
import ru.isaev.cats.rest.Entities.Mapper.IMyMapper;
import ru.isaev.cats.rest.Entities.Mapper.MyMapper;
import ru.isaev.cats.rest.Entities.OwnerDtos.OwnerDto;
import ru.isaev.cats.rest.Entities.Owners.Owner;
import ru.isaev.cats.rest.Security.MyUserDetails;
import ru.isaev.cats.rest.Security.Roles;
import ru.isaev.cats.rest.Service.CatService;
import ru.isaev.cats.rest.Service.OwnerService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


//TODO тесты для контроллеров. DONE
@SpringBootTest(classes = Test.class)
public class Tests {
    private MockMvc mockMvc;
    @Mock
    private ICatDAO catDAO;

    @Mock
    private CatService mockCatService;

    @Mock
    private IOwnerDAO ownerDAO;

    @Mock
    private OwnerService mockOwnerService;

    @Test
    public void testCatService() {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setFirstName("Daniel");
        owner.setLastName("Isaev");
        owner.setRole(Roles.ROLE_USER);
        owner.setPassword("123");

        String birthday = "2000-10-12";

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

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new MyUserDetails(owner), null,
                AuthorityUtils.createAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        CatService catService = new CatService(catDAO, ownerDAO);
        Cat cat = new Cat();
        cat.setId(1L);
        cat.setOwner(owner);
        birthday = "2004-10-12";

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

        cat.setBreed(CatBreeds.BREED3);
        cat.setColor(CatColors.BLACK);

        List<Cat> catsOfOwner = new ArrayList<>();
        catsOfOwner.add(cat);
        owner.setCatsList(catsOfOwner);

        when(catDAO.findById(1L)).thenReturn(Optional.of(cat));

        Cat catFromDb = catService.getCatById(1L);

        assertNotNull(catFromDb);
    }


    @Test
    public void testOwnerService() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        OwnerService ownerService = new OwnerService(ownerDAO, encoder);
        Owner owner = new Owner();
        owner.setFirstName("Daniel");
        owner.setLastName("Isaev");

        String birthday = "2000-10-12";

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

        when(ownerDAO.findById(1L)).thenReturn(Optional.of(owner));

        Owner ownerFromDb = ownerService.getOwnerById(1L);

        assertNotNull(ownerFromDb);
    }

    @Test
    public void testCatController(){
        IMyMapper mapper = new MyMapper();
        CatController catController = new CatController(mockCatService, mapper);

        Cat cat = new Cat();
        String birthday = "2004-10-12";
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
        cat.setBreed(CatBreeds.BREED3);
        cat.setColor(CatColors.BLACK);
        cat.setId(1L);

        when(catDAO.findById(1L)).thenReturn(Optional.of(cat));
        doReturn(cat).when(mockCatService).getCatById(1L);

        ResponseEntity<CatDto> response = catController.getById(1L);

        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    public void testOwnerController(){
        IMyMapper mapper = new MyMapper();
        OwnerController ownerController = new OwnerController(mockOwnerService, mapper);

        Owner owner = new Owner();
        owner.setId(1L);
        owner.setFirstName("Daniel");
        owner.setLastName("Isaev");

        String birthday = "2000-10-12";

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

        when(ownerDAO.findById(1L)).thenReturn(Optional.of(owner));
        doReturn(owner).when(mockOwnerService).getOwnerById(1L);

        ResponseEntity<OwnerDto> response = ownerController.getById(1L);

        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
    }
}
