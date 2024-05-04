package ru.isaev.Controllers;

import ru.isaev.Mapper.IMyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.isaev.Entities.OwnerDtos.OwnerDto;
import ru.isaev.Entities.Owners.Owner;
import ru.isaev.Service.OwnerService;

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
        return new ResponseEntity<>(
                mapper.ownerToOwnerDto(ownerService.getOwnerById(id)),
                HttpStatus.OK
        );

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

