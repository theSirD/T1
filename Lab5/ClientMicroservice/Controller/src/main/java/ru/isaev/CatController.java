package ru.isaev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.*;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.CatDtos.CatDtoInput;
import ru.isaev.Cats.CatBreeds;
import ru.isaev.Cats.CatColors;
import java.util.List;


@RestController
@RequestMapping("/cats")
public class CatController {
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public CatController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> getById(@PathVariable Long id) {
        kafkaTemplate.send("topic-get-cat-by-id", id);

        // TODO надо как-то отправить пользователю кота. И в других эндпоинтах похожая история
        return new ResponseEntity<>(
//                mapper.catToCatDto(catService.getCatById(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
//    @PostFilter("hasAuthority('ROLE_ADMIN') or authentication.name == filterObject.ownerId")
    public ResponseEntity<List<CatDto>> getAll() {

        // TODO. А тут что отправлять?
        kafkaTemplate.send("topic-get-all-cats", null);

        return new ResponseEntity<>(
//                mapper.mapListOfCatsToListOfDtos(catService.getAllCats()),
                HttpStatus.OK
        );
    }

    @GetMapping("/color")
    public ResponseEntity<List<CatDto>> getByColor(@RequestParam(name = "color", required = false) CatColors color) {
        kafkaTemplate.send("topic-get-cats-by-color", color);

        return new ResponseEntity<>(
//                mapper.mapListOfCatsToListOfDtos(catService.getCatsByColor(color)),
                HttpStatus.OK
        );
    }

    @GetMapping("/breed")
    public ResponseEntity<List<CatDto>> getByBreed(@RequestParam(name = "breed", required = false) CatBreeds breed) {
        kafkaTemplate.send("topic-get-cats-by-color", breed);

        return new ResponseEntity<>(
//                mapper.mapListOfCatsToListOfDtos(catService.getCatsByBreed(breed)),
                HttpStatus.OK
        );
    }

    @PostMapping("/add")
    public ResponseEntity<CatDtoInput> addCat(@RequestBody CatDtoInput catDtoInput) {
        kafkaTemplate.send("topic-add-cat", catDtoInput);

        // TODO. Тут тоже надо вернуть кота с id
        return ResponseEntity.status(HttpStatus.CREATED).body(catDtoInput);
    }

    @PutMapping("/edit")
    public ResponseEntity<CatDtoInput> updateCat(@RequestBody CatDtoInput catDtoInput) {
        kafkaTemplate.send("topic-update-cat", catDtoInput);

        // TODO. Тут тоже надо вернуть кота с id
        return ResponseEntity.status(HttpStatus.OK).body(catDtoInput);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCatById(@PathVariable Long id) {
        kafkaTemplate.send("topic-delete-cat-by-id", id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

