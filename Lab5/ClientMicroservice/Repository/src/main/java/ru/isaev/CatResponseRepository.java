package ru.isaev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.Responses.CatResponse;

@Repository
public interface CatResponseRepository extends JpaRepository<CatResponse, Long> {
}
