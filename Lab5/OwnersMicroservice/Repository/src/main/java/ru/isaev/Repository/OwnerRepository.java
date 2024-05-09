package ru.isaev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.Domain.Owners.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
