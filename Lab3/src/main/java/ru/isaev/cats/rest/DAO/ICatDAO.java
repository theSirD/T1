package ru.isaev.cats.rest.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isaev.cats.rest.Entities.Cats.Cat;
import ru.isaev.cats.rest.Entities.Cats.CatBreeds;
import ru.isaev.cats.rest.Entities.Cats.CatColors;

import java.util.List;

public interface ICatDAO extends JpaRepository<Cat, Long> {
    List<Cat> findByColor(CatColors color);

    List<Cat> findByBreed(CatBreeds breed);
}
