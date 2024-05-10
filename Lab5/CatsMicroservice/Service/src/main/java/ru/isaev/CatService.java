package ru.isaev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isaev.Cats.Cat;
import ru.isaev.Cats.CatBreeds;
import ru.isaev.Cats.CatColors;
import ru.isaev.Owners.Owner;
import ru.isaev.Owners.Roles;
import ru.isaev.Utilities.Exceptions.CatNotFoundExceptions;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CatService {
    private final CatRepository catRepository;

    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    // TODO. Добавь логику сохранения кота
    public void addCat(Cat cat) {
//        List<Cat> friendsOfNewCat = new ArrayList<>();
//        cat.setFriendsList(friendsOfNewCat);
//        catRepository.save(cat);
//        List<Cat> catsOfOwner = currentOwner.getCatsList();
//        for (Cat catOfOwner :
//                catsOfOwner) {
//                cat.addFriend(catOfOwner, true);
//                updateCat(catOfOwner);
//        }
//        cat.setFriendsList(friendsOfNewCat);
//        cat.setOwner(currentOwner);
//
//        catsOfOwner.add(cat);
//        currentOwner.setCatsList(catsOfOwner);
//
//        catRepository.save(cat);
//        ownerDAO.save(currentOwner);
    }

    public Cat getCatById(Long id) {
        Cat cat = catRepository.findById(id).orElseThrow(
                () -> new CatNotFoundExceptions("Not found cat with id = " + id));

        return cat;
    }

    public List<Cat> getCatsByColor(CatColors color) {
        return catRepository.findByColor(color);
    }

    public List<Cat> getAllCats() {
        return catRepository.findAll();
    }

    public List<Cat> getCatsByBreed(CatBreeds breed) {
        return catRepository.findByBreed(breed);
    }

    public void updateCat(Cat cat) {
        catRepository.save(cat);
    }

    public void removeCatById(Long id) {
        Cat cat = catRepository.findById(id).orElseThrow(
                () -> new CatNotFoundExceptions("No cat with id = " + id));

        catRepository.deleteById(id);
    }
}
