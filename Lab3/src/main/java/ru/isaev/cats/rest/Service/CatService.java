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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

        Set<Cat> friendsOfNewCat = new HashSet<>();
        cat.setFriendsList(friendsOfNewCat);
        catDAO.save(cat);
        Set<Cat> catsOfOwner = currentOwner.getCatsList();
        for (Cat catOfOwner :
                catsOfOwner) {
                cat.addFriend(catOfOwner, true);
                updateCat(catOfOwner);
//            Set<Cat> friendsOfCatOfOwner = catOfOwner.getFriendsList();
//            friendsOfCatOfOwner.add(cat);
//            updateCat(catOfOwner);
//            friendsOfNewCat.add(catOfOwner);
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

        Cat cat = catDAO.findById(id).orElseThrow(
                () -> new CatNotFoundExceptions("Not found cat with id = " + id));


        return cat;
    }

    public List<Cat> getCatsByColor(CatColors color) {
        return catDAO.findByColor(color);
    }

    public List<Cat> getCatsByBreed(CatBreeds breed) {
        return catDAO.findByBreed(breed);
    }

    public void updateCat(Cat cat) {
        catDAO.save(cat);
    }

    public void removeCat(Cat cat) {
        catDAO.delete(cat);
    }

    public void removeCatById(Long id) {
        catDAO.deleteById(id);
    }
}
