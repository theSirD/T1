package ru.isaev.cats.rest.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isaev.cats.rest.DAO.ICatDAO;
import ru.isaev.cats.rest.Entities.Cats.Cat;
import ru.isaev.cats.rest.Entities.Cats.CatBreeds;
import ru.isaev.cats.rest.Entities.Cats.CatColors;
import ru.isaev.cats.rest.Utilities.Exceptions.CatNotFoundExceptions;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {
    private final ICatDAO catDAO;

    @Autowired
    public CatService(ICatDAO catDAO) {
        this.catDAO = catDAO;
    }

    public void addCat(Cat cat) {
        catDAO.save(cat);
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
