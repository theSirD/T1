package ru.isaev;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.Requests.Request;

public interface CatDtoRepository extends JpaRepository<CatDto, Long> {
}
