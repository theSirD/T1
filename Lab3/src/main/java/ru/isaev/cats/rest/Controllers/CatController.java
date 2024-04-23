package ru.isaev.cats.rest.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaev.cats.rest.Entities.CatDtos.CatDto;
import ru.isaev.cats.rest.Entities.CatDtos.CatDtoInput;
import ru.isaev.cats.rest.Entities.Cats.Cat;
import ru.isaev.cats.rest.Entities.Cats.CatBreeds;
import ru.isaev.cats.rest.Entities.Cats.CatColors;
import ru.isaev.cats.rest.Entities.Mapper.IMyMapper;
import ru.isaev.cats.rest.Service.CatService;

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
                mapper.catToCatDto(catService.getCatById(id).get()),
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
    public ResponseEntity<String> addCat(@RequestBody CatDtoInput catDtoInput) {
        Cat cat = mapper.catDtoInputToCat(catDtoInput);
        catService.addCat(cat);

        //TODO 20.4.24. не пиши такие сообщения в body
        return ResponseEntity.status(HttpStatus.CREATED).body("Cat was added");
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editCat(@RequestBody CatDtoInput catDtoInput) {
        Cat cat = mapper.catDtoInputToCat(catDtoInput);
        catService.updateCat(cat);
        //TODO то же самое
        return ResponseEntity.status(HttpStatus.OK).body("Cat was edited");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCatById(@PathVariable Long id) {
        catService.removeCatById(id);
        //TODO то же самое
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cat was deleted");
    }
}

