package ru.isaev.DAO;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.Entities.Cats.Cat;
import ru.isaev.Entities.Cats.CatBreeds;
import ru.isaev.Entities.Cats.CatColors;

import java.util.List;

@Repository
public interface ICatDAO extends JpaRepository<Cat, Long> {
    List<Cat> findByColor(CatColors color);

    List<Cat> findByBreed(CatBreeds breed);
}
