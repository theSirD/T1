package ru.isaev.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.isaev.DAO.ICatDAO;
import ru.isaev.DAO.IOwnerDAO;
import ru.isaev.Entities.Cats.CatBreeds;
import ru.isaev.Entities.Cats.CatColors;
import ru.isaev.Service.Utilities.Exceptions.CatNotFoundExceptions;
import ru.isaev.Service.Security.MyUserDetails;
import ru.isaev.Entities.Owners.Roles;
import ru.isaev.Entities.Cats.Cat;
import ru.isaev.Entities.Owners.Owner;
import ru.isaev.Service.Utilities.Exceptions.NotYourCatException;

import java.util.*;

@Service
public class CatService {
    private final ICatDAO catDAO;
    private final IOwnerDAO ownerDAO;

    @Autowired
    public CatService(ICatDAO catDAO, IOwnerDAO ownerDAO) {
        this.catDAO = catDAO;
        this.ownerDAO = ownerDAO;
    }

    public void addCat(Cat cat) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

        List<Cat> friendsOfNewCat = new ArrayList<>();
        cat.setFriendsList(friendsOfNewCat);
        catDAO.save(cat);
        List<Cat> catsOfOwner = currentOwner.getCatsList();
        for (Cat catOfOwner :
                catsOfOwner) {
                cat.addFriend(catOfOwner, true);
                updateCat(catOfOwner);
        }
        cat.setFriendsList(friendsOfNewCat);
        cat.setOwner(currentOwner);

        catsOfOwner.add(cat);
        currentOwner.setCatsList(catsOfOwner);

        catDAO.save(cat);
        ownerDAO.save(currentOwner);
    }

    public Cat getCatById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

        Cat cat = catDAO.findById(id).orElseThrow(
                () -> new CatNotFoundExceptions("Not found cat with id = " + id));

        if (!Objects.equals(cat.getOwner().getId(), currentOwner.getId())  && currentOwner.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourCatException("Not your cat with id = " + id);


        return cat;
    }

    public List<Cat> getCatsByColor(CatColors color) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

        if ( currentOwner.getRole() == Roles.ROLE_ADMIN) {
            return catDAO.findByColor(color);
        }

        List<Cat> catsOfCertainColor = new ArrayList<>();
        for (Cat cat :
                currentOwner.getCatsList()) {
            if (cat.getColor() == color)
                catsOfCertainColor.add(cat);
        }

        return catsOfCertainColor;
    }

    public List<Cat> getAllCats() {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

        if ( currentOwner.getRole() == Roles.ROLE_ADMIN) {
            return catDAO.findAll();
        }

        return currentOwner.getCatsList();
    }

    public List<Cat> getCatsByBreed(CatBreeds breed) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

        if ( currentOwner.getRole() == Roles.ROLE_ADMIN) {
            return catDAO.findByBreed(breed);
        }

        List<Cat> catsOfCertainBreed = new ArrayList<>();
        for (Cat cat :
                currentOwner.getCatsList()) {
            if (cat.getBreed() == breed)
                catsOfCertainBreed.add(cat);
        }

        return catsOfCertainBreed;
    }

    public void updateCat(Cat cat) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

        if (!Objects.equals(cat.getOwner().getId(), currentOwner.getId()) && currentOwner.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourCatException("Not your cat with id = " + cat.getId());

        catDAO.save(cat);
    }

    public void removeCatById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

        Cat cat = catDAO.findById(id).orElseThrow(
                () -> new CatNotFoundExceptions("No cat with id = " + id));

        if (!Objects.equals(cat.getOwner().getId(), currentOwner.getId()) && currentOwner.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourCatException("Not your cat with id = " + cat.getId());

        catDAO.deleteById(id);
    }
}
