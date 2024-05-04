package ru.isaev.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.Entities.Owners.Owner;

@Repository
public interface IOwnerDAO extends JpaRepository<Owner, Long> {
}
