package ru.isaev.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.*;
import ru.isaev.Entities.CatDtos.CatDto;
import ru.isaev.Entities.CatDtos.CatDtoInput;
import ru.isaev.Service.CatService;
import ru.isaev.Mapper.IMyMapper;
import ru.isaev.Entities.Cats.Cat;
import ru.isaev.Entities.Cats.CatBreeds;
import ru.isaev.Entities.Cats.CatColors;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatController {
    private final CatService catService;

    private final IMyMapper mapper;

    @Autowired
    public CatController(CatService catService, IMyMapper mapper) {
        this.catService = catService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
                mapper.catToCatDto(catService.getCatById(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    @PostFilter("hasAuthority('ROLE_ADMIN') or authentication.name == filterObject.ownerId")
    public ResponseEntity<List<CatDto>> getAll() {
        return new ResponseEntity<>(
                mapper.mapListOfCatsToListOfDtos(catService.getAllCats()),
                HttpStatus.OK
        );
    }

    @GetMapping("/color")
    public ResponseEntity<List<CatDto>> getByColor(@RequestParam(name = "color", required = false) CatColors color) {
        return new ResponseEntity<>(
                mapper.mapListOfCatsToListOfDtos(catService.getCatsByColor(color)),
                HttpStatus.OK
        );
    }

    @GetMapping("/breed")
    public ResponseEntity<List<CatDto>> getByBreed(@RequestParam(name = "breed", required = false) CatBreeds breed) {
        return new ResponseEntity<>(
                mapper.mapListOfCatsToListOfDtos(catService.getCatsByBreed(breed)),
                HttpStatus.OK
        );
    }

    @PostMapping("/add")
    public ResponseEntity<CatDtoInput> addCat(@RequestBody CatDtoInput catDtoInput) {
        Cat cat = mapper.catDtoInputToCat(catDtoInput);
        catService.addCat(cat);

        //TODO 20.4.24. не пиши такие сообщения в body. DONE
        return ResponseEntity.status(HttpStatus.CREATED).body(catDtoInput);
    }

    @PutMapping("/edit")
    public ResponseEntity<CatDtoInput> editCat(@RequestBody CatDtoInput catDtoInput) {
        Cat cat = mapper.catDtoInputToCat(catDtoInput);
        catService.updateCat(cat);
        //TODO то же самое. DONE
        return ResponseEntity.status(HttpStatus.OK).body(catDtoInput);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCatById(@PathVariable Long id) {
        catService.removeCatById(id);
        //TODO то же самое. DONE
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

