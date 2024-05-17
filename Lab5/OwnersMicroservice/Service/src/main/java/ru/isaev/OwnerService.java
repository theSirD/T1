package ru.isaev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.Cats.Cat;
import ru.isaev.Mapper.IMyMapper;
import ru.isaev.OwnerDtos.OwnerDto;
import ru.isaev.Owners.Owner;
import ru.isaev.Owners.Roles;
import ru.isaev.Utilities.Exceptions.NotYourProfileException;
import ru.isaev.Utilities.Exceptions.OwnerNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


@Service
public class OwnerService {

    private final OwnerRepository ownerDAO;
    private PasswordEncoder passwordEncoder;

    private final IMyMapper mapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public OwnerService(OwnerRepository ownerDAO, PasswordEncoder passwordEncoder, IMyMapper mapper, KafkaTemplate<String, Object> kafkaTemplate) {
        this.ownerDAO = ownerDAO;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Owner addOwner(Owner owner) {
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        return ownerDAO.saveAndFlush(owner);
    }

    public Owner getOwnerById(Long id) {

        Owner owner = ownerDAO.findById(id).orElseThrow(
                () -> new OwnerNotFoundException("Not found owner with id = " + id));

        return owner;
    }

    public Owner updateOwner(Owner owner) {
//        if (!Objects.equals(currentOwner.getId(), owner.getId()) && currentOwner.getRole() != Roles.ROLE_ADMIN)
//            throw new NotYourProfileException("Not your profile with id = " + owner.getId());

        Owner originalOwner = ownerDAO.findById(owner.getId()).orElseThrow(
                () -> new OwnerNotFoundException("No cat with id = " + owner.getId()));

        owner.setCatsList(originalOwner.getCatsList());
        if (owner.getBirthday() == null)
            owner.setBirthday(originalOwner.getBirthday());
        if (owner.getFirst_name() == null)
            owner.setFirstName(originalOwner.getFirst_name());
        if (owner.getLast_name() == null)
            owner.setLastName(originalOwner.getLast_name());
        if (owner.getPassword() == null)
            owner.setPassword(originalOwner.getPassword());
        if (owner.getRole() == null)
            owner.setRole(originalOwner.getRole());

        return ownerDAO.saveAndFlush(owner);
    }

    public void removeOwnerById(Long id) throws JsonProcessingException {
        logger.info("trying to delete owner");
        Owner owner = ownerDAO.findById(id).orElseThrow(
                () -> new OwnerNotFoundException("No owner with id = " + id));

//        if (!Objects.equals(currentOwner.getId(), id)  && currentOwner.getRole() != Roles.ROLE_ADMIN)
//            throw new NotYourProfileException("Not your profile with id = " + id);



        OwnerDto ownerDto = mapper.ownerToOwnerDto(owner);
        SetOwnerOfCatsToNullDto setOwnerOfCatsToNullDto = new SetOwnerOfCatsToNullDto(ownerDto);
        String json = objectMapper.writeValueAsString(setOwnerOfCatsToNullDto);

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.
                send("topic-set-owner-of-cats-to-null", json);

        future.join();

        ownerDAO.deleteById(id);
    }
}
