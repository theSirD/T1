package ru.isaev.DAO;

import ru.isaev.Entities.Owners.Owner;

import java.util.List;

public interface IOwnerDAO {
    void add(Owner owner);

    List<Owner> getAll();

    Owner getById(Long id);

    void update(Owner owner);

    void remove(Owner owner);
}
