package ru.isaev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.isaev.Owners.Owner;
import ru.isaev.Owners.Roles;
import ru.isaev.Utilities.Exceptions.NotYourProfileException;
import ru.isaev.Utilities.Exceptions.OwnerNotFoundException;

import java.util.Objects;


@Service
public class OwnerService {

    private final OwnerRepository ownerDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public OwnerService(OwnerRepository ownerDAO, PasswordEncoder passwordEncoder) {
        this.ownerDAO = ownerDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public void addOwner(Owner owner) {
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        ownerDAO.save(owner);
    }

    public Owner getOwnerById(Long id) {

        Owner owner = ownerDAO.findById(id).orElseThrow(
                () -> new OwnerNotFoundException("Not found owner with id = " + id));

        return owner;
    }

    public void updateOwner(Owner owner) {
//        if (!Objects.equals(currentOwner.getId(), owner.getId()) && currentOwner.getRole() != Roles.ROLE_ADMIN)
//            throw new NotYourProfileException("Not your profile with id = " + owner.getId());

        ownerDAO.save(owner);
    }

    public void removeOwnerById(Long id) {
        Owner owner = ownerDAO.findById(id).orElseThrow(
                () -> new OwnerNotFoundException("No owner with id = " + id));

//        if (!Objects.equals(currentOwner.getId(), id)  && currentOwner.getRole() != Roles.ROLE_ADMIN)
//            throw new NotYourProfileException("Not your profile with id = " + id);

        ownerDAO.deleteById(id);
    }
}
