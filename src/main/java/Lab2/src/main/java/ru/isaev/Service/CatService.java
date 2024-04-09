package ru.isaev.Service;

import ru.isaev.DAO.ICatDAO;
import ru.isaev.Entities.Cats.Cat;

import java.util.List;

public class CatService {
    private final ICatDAO catDAO;

    public CatService(ICatDAO catDAO) {
        this.catDAO = catDAO;
    }

    public void addCat(Cat cat) {
        catDAO.add(cat);
    }

    public List<Cat> getAllCats() {
        return catDAO.getAll();
    }

    public Cat getCatById(Long id) {
        return catDAO.getById(id);
    }

    public void updateCat(Cat cat) {
        catDAO.update(cat);
    }

    public void removeCat(Cat cat) {
        catDAO.remove(cat);
    }
}
