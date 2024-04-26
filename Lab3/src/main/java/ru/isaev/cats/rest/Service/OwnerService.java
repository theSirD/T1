package ru.isaev.cats.rest.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.isaev.cats.rest.Entities.Owners.Owner;
import ru.isaev.cats.rest.DAO.IOwnerDAO;
import ru.isaev.cats.rest.Security.MyUserDetails;
import ru.isaev.cats.rest.Utilities.Exceptions.CatNotFoundExceptions;
import ru.isaev.cats.rest.Utilities.Exceptions.OwnerNotFoundException;

import java.util.Objects;
import java.util.Optional;


@Service
public class OwnerService {

    private final IOwnerDAO ownerDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public OwnerService(IOwnerDAO ownerDAO, PasswordEncoder passwordEncoder) {
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
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

        if (!Objects.equals(currentOwner.getId(), owner.getId()))
            return;

        ownerDAO.save(owner);
    }

    public void removeOwnerById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner currentOwner = currentPrincipal.getOwner();

        if (!Objects.equals(currentOwner.getId(), id))
            return;

        ownerDAO.deleteById(id);
    }
}
