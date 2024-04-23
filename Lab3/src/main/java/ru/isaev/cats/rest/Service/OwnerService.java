package ru.isaev.cats.rest.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.isaev.cats.rest.Entities.Owners.Owner;
import ru.isaev.cats.rest.DAO.IOwnerDAO;

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

    public Optional<Owner> getOwnerById(Long id) {
        return ownerDAO.findById(id);
    }

    public void updateOwner(Owner owner) {
        ownerDAO.save(owner);
    }

    public void removeOwnerById(Long id) {
        ownerDAO.deleteById(id);
    }
}
