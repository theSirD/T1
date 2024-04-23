package ru.isaev.cats.rest.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isaev.cats.rest.Entities.Owners.Owner;
import ru.isaev.cats.rest.DAO.IOwnerDAO;
import ru.isaev.cats.rest.Utilities.Exceptions.CatNotFoundExceptions;
import ru.isaev.cats.rest.Utilities.Exceptions.OwnerNotFoundException;

import java.util.Optional;

@Service
public class OwnerService {

    private final IOwnerDAO ownerDAO;
    @Autowired
    public OwnerService(IOwnerDAO ownerDAO) {
        this.ownerDAO = ownerDAO;
    }

    public void addOwner(Owner owner) {
        ownerDAO.save(owner);
    }

    public Owner getOwnerById(Long id) {

        Owner owner = ownerDAO.findById(id).orElseThrow(
                () -> new OwnerNotFoundException("Not found owner with id = " + id));

        return owner;
    }

    public void updateOwner(Owner owner) {
        ownerDAO.save(owner);
    }

    public void removeOwnerById(Long id) {
        ownerDAO.deleteById(id);
    }
}
