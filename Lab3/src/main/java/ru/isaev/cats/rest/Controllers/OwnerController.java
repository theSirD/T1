package ru.isaev.cats.rest.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaev.cats.rest.Entities.Mapper.IMyMapper;
import ru.isaev.cats.rest.Entities.OwnerDtos.OwnerDto;
import ru.isaev.cats.rest.Entities.Owners.Owner;
import ru.isaev.cats.rest.Service.OwnerService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;

    private final IMyMapper mapper;

    @Autowired
    public OwnerController(OwnerService ownerService, IMyMapper mapper) {
        this.ownerService = ownerService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(
                    mapper.ownerToOwnerDto(ownerService.getOwnerById(id).get()),
                    HttpStatus.OK
            );
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    new OwnerDto(),
                    HttpStatus.NO_CONTENT
            );
        }

    }


    @PostMapping("/add")
    public ResponseEntity<OwnerDto> addOwner(@RequestBody OwnerDto ownerDto) {
        Owner owner = mapper.ownerDtoToOwner(ownerDto);
        ownerService.addOwner(owner);

        return ResponseEntity.status(HttpStatus.CREATED).body(ownerDto);
    }

    @PostMapping("/edit")
    public ResponseEntity<OwnerDto> editOwner(@RequestBody OwnerDto ownerDto) {
        Owner owner = mapper.ownerDtoToOwner(ownerDto);
        ownerService.updateOwner(owner);

        return ResponseEntity.status(HttpStatus.OK).body(ownerDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOwnerById(@PathVariable Long id) {
        ownerService.removeOwnerById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

