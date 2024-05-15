package ru.isaev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isaev.Cats.Cat;
import ru.isaev.Cats.CatBreeds;
import ru.isaev.Cats.CatColors;
import ru.isaev.Utilities.Exceptions.CatNotFoundExceptions;


import java.util.List;

@Service
public class CatService {
    private final CatRepository catRepository;

    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    // TODO. Добавь логику сохранения кота (асинхронная, по гайду https://www.youtube.com/watch?v=mB91hPd2xmM&list=PLt91xr-Pp57T4kKLdavnQ0gV1X427frC2&index=15&t=1092s)
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

    public Cat updateCat(Cat cat) {
        return catRepository.saveAndFlush(cat);
    }

    public void removeCatById(Long id) {
        Cat cat = catRepository.findById(id).orElseThrow(
                () -> new CatNotFoundExceptions("No cat with id = " + id));

        catRepository.deleteById(id);
    }
}
