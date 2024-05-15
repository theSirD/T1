package ru.isaev;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isaev.OwnerDtos.OwnerDto;

public interface OwnerDtoRepository extends JpaRepository<OwnerDto, Long> {
}