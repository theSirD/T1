package ru.isaev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.CatDtos.CatDtoInput;
import ru.isaev.CatRequestDtos.*;
import ru.isaev.Cats.CatBreeds;
import ru.isaev.Cats.CatColors;
import ru.isaev.Requests.Request;
import ru.isaev.Requests.RequestStatus;
import ru.isaev.Responses.CatResponse;

import java.util.List;


@RestController
@RequestMapping("/cats")
public class CatController {
    private KafkaTemplate<String, Object> kafkaTemplate;

    private RequestResponseService requestResponseService;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public CatController(KafkaTemplate<String, Object> kafkaTemplate, RequestResponseService requestResponseService) {
        this.kafkaTemplate = kafkaTemplate;
        this.requestResponseService = requestResponseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable Long id) throws JsonProcessingException {
        Request request = requestResponseService.addRequest();
        RequestByIdDto requestByIdDto = new RequestByIdDto(request.getId(), id);

        String json = mapper.writeValueAsString(requestByIdDto);
        kafkaTemplate.send("topic-get-cat-by-id", json);

        return new ResponseEntity<>(
                "Try to get cat here: /cats/request/" + request.getId(),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/all")
//    @PostFilter("hasAuthority('ROLE_ADMIN') or authentication.name == filterObject.ownerId")
    public ResponseEntity<String> getAll() throws JsonProcessingException {
        Request request = requestResponseService.addRequest();
        RequestAllDto requestAllDto = new RequestAllDto(request.getId());

        String json = mapper.writeValueAsString(requestAllDto);
        kafkaTemplate.send("topic-get-all-cats", json);

        return new ResponseEntity<>(
                "Try to get cats here: /cats/request/" + request.getId(),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/color")
    public ResponseEntity<String> getByColor(@RequestParam(name = "color", required = false) CatColors color) throws JsonProcessingException {
        Request request = requestResponseService.addRequest();
        RequestByColorDto requestByColorDto = new RequestByColorDto(request.getId(), color);

        String json = mapper.writeValueAsString(requestByColorDto);
        kafkaTemplate.send("topic-get-cats-by-color", json);

        return new ResponseEntity<>(
                "Try to get cats here: /cats/request/" + request.getId(),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/breed")
    public ResponseEntity<String> getByBreed(@RequestParam(name = "breed", required = false) CatBreeds breed) throws JsonProcessingException {
        Request request = requestResponseService.addRequest();
        RequestByBreedDto requestByBreedDto = new RequestByBreedDto(request.getId(), breed);

        String json = mapper.writeValueAsString(requestByBreedDto);
        kafkaTemplate.send("topic-get-cats-by-breed", json);

        return new ResponseEntity<>(
                "Try to get cats here: /cats/request/" + request.getId(),
                HttpStatus.ACCEPTED
        );
    }
//
//    // TODO. Допиши
//    @PostMapping("/add")
//    public ResponseEntity<CatDtoInput> addCat(@RequestBody CatDtoInput catDtoInput) {
//        kafkaTemplate.send("topic-add-cat", catDtoInput);
//
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(catDtoInput);
//    }
//

    @PutMapping("/edit")
    public ResponseEntity<String> updateCat(@RequestBody CatDtoInput catDtoInput) throws JsonProcessingException {
        Request request = requestResponseService.addRequest();
        RequestWithInputDtoDto requestWithInputDtoDto = new RequestWithInputDtoDto(request.getId(), catDtoInput);

        String json = mapper.writeValueAsString(requestWithInputDtoDto);
        kafkaTemplate.send("topic-update-cat", json);

        return new ResponseEntity<>(
                "Try to get cats here: /cats/request/" + request.getId(),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCatById(@PathVariable Long id) throws JsonProcessingException {
        Request request = requestResponseService.addRequest();
        RequestByIdDto requestByIdDto = new RequestByIdDto(request.getId(), id);

        String json = mapper.writeValueAsString(requestByIdDto);
        kafkaTemplate.send("topic-delete-cat-by-id", json);

        return new ResponseEntity<>(
                "Try to get cat here: /cats/request/" + request.getId(),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<?> getByRequestId(@PathVariable Long id) {
        Request request = requestResponseService.getRequestById(id);


        if (request.getStatus() == RequestStatus.PENDING) {
            return new ResponseEntity<>(
                    "Try to get cat here: /cats/request/" + request.getId(),
                    HttpStatus.ACCEPTED
            );
        }

        CatResponse response = requestResponseService.getCatResponseById(id);
        List<CatDto> cats = response.getCatsList();

        return new ResponseEntity<>(
                cats,
                HttpStatus.OK
        );
    }
}

