package ru.isaev.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.Domain.Cats.Cat;
import ru.isaev.Domain.Cats.CatBreeds;
import ru.isaev.Domain.Cats.CatColors;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findByColor(CatColors color);

    List<Cat> findByBreed(CatBreeds breed);
}
