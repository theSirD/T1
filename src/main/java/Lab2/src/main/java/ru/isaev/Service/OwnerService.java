package ru.isaev.Service;

import ru.isaev.DAO.IOwnerDAO;
import ru.isaev.Entities.Owners.Owner;

import java.util.List;

public class OwnerService {
    private final IOwnerDAO ownerDAO;

    public OwnerService(IOwnerDAO ownerDAO) {
        this.ownerDAO = ownerDAO;
    }

    public void addOwner(Owner owner) {
        ownerDAO.add(owner);
    }

    public List<Owner> getAllOwners() {
        return ownerDAO.getAll();
    }

    public Owner getOwnerById(Long id) {
        return ownerDAO.getById(id);
    }

    public void updateOwner(Owner owner) {
        ownerDAO.update(owner);
    }

    public void removeOwner(Owner owner) {
        ownerDAO.remove(owner);
    }
}
