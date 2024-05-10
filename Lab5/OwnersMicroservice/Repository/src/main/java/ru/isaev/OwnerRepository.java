package ru.isaev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.Owners.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
