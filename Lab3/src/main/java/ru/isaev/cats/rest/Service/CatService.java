package ru.isaev.cats.rest.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.isaev.cats.rest.DAO.ICatDAO;
import ru.isaev.cats.rest.DAO.IOwnerDAO;
import ru.isaev.cats.rest.Entities.Cats.Cat;
import ru.isaev.cats.rest.Entities.Cats.CatBreeds;
import ru.isaev.cats.rest.Entities.Cats.CatColors;
import ru.isaev.cats.rest.Entities.Owners.Owner;
import ru.isaev.cats.rest.Security.MyUserDetails;
import ru.isaev.cats.rest.Utilities.Exceptions.CatNotFoundExceptions;

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

    //TODO 20.4.24. advicecontroller. DONE
    public Cat getCatById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

        Cat cat = catDAO.findById(id).orElseThrow(
                () -> new CatNotFoundExceptions("Not found cat with id = " + id));

        if (!Objects.equals(cat.getOwner().getId(), currentOwner.getId()))
            cat = null;


        return cat;
    }

    public List<Cat> getCatsByColor(CatColors color) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

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

        return currentOwner.getCatsList();
    }

    public List<Cat> getCatsByBreed(CatBreeds breed) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

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

        if (!Objects.equals(cat.getOwner().getId(), currentOwner.getId()))
            return;

        catDAO.save(cat);
    }

    public void removeCatById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

        Cat cat = catDAO.findById(id).orElseThrow(
                () -> new CatNotFoundExceptions("No cat with id = " + id));

        if (!Objects.equals(cat.getOwner().getId(), currentOwner.getId()))
            return;
        catDAO.deleteById(id);
    }
}
