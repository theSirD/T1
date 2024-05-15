package ru.isaev;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isaev.Responses.CatResponse;
import ru.isaev.Responses.OwnerResponse;

public interface OwnerResponseRepository extends JpaRepository<OwnerResponse, Long>  {
}
