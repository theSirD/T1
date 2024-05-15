package ru.isaev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.CatRequestDtos.RequestByIdDto;
import ru.isaev.Requests.Request;
import ru.isaev.Requests.RequestStatus;
import ru.isaev.Responses.CatResponse;

import java.util.List;


@RestController
@RequestMapping("/cats")
public class CatController {
    private KafkaTemplate<String, Object> kafkaTemplate;

    private RequestResponseService requestResponseService;

    @Autowired
    public CatController(KafkaTemplate<String, Object> kafkaTemplate, RequestResponseService requestResponseService) {
        this.kafkaTemplate = kafkaTemplate;
        this.requestResponseService = requestResponseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable Long id) throws JsonProcessingException {
        Request request = requestResponseService.addRequest();
        RequestByIdDto requestByIdDto = new RequestByIdDto(request.getId(), id);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(requestByIdDto);

        kafkaTemplate.send("topic-get-cat-by-id", json);

        return new ResponseEntity<>(
                "Try to get cat here: /cats/request/" + request.getId(),
                HttpStatus.ACCEPTED
        );
    }

//    @GetMapping("/all")
////    @PostFilter("hasAuthority('ROLE_ADMIN') or authentication.name == filterObject.ownerId")
//    public ResponseEntity<List<CatDto>> getAll() throws JsonProcessingException {
//
//        // TODO. А тут что отправлять?
//        kafkaTemplate.send("topic-get-all-cats", "tmp");
//
//        ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(5));
//
//        List<CatDto> cats = null;
//        logger.info("Yo" + String.valueOf(records.count()));
//        for (ConsumerRecord<String, Object> record : records) {
//            String value = (String) record.value();
//            cats = objectMapper.readValue(value, new TypeReference<List<CatDto>>(){});
//        }
//
//        return new ResponseEntity<>(
//                cats,
//                HttpStatus.OK
//        );
//    }
//
//    @GetMapping("/color")
//    public ResponseEntity<List<CatDto>> getByColor(@RequestParam(name = "color", required = false) CatColors color) throws JsonProcessingException {
//        kafkaTemplate.send("topic-get-cats-by-color", color.ordinal());
//
//        consumer.seekToEnd(partitions);
//
//        ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(5));
//
//        List<CatDto> cats = null;
//        logger.info("Yo" + String.valueOf(records.count()));
//        for (ConsumerRecord<String, Object> record : records) {
//            String value = (String) record.value();
//            cats = objectMapper.readValue(value, new TypeReference<List<CatDto>>(){});
//        }
//
//        return new ResponseEntity<>(
//                cats,
//                HttpStatus.OK
//        );
//    }
//
//    @GetMapping("/breed")
//    public ResponseEntity<List<CatDto>> getByBreed(@RequestParam(name = "breed", required = false) CatBreeds breed) throws JsonProcessingException {
//        kafkaTemplate.send("topic-get-cats-by-color", breed.ordinal());
//
//        consumer.seekToEnd(partitions);
//
//        ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(5));
//
//        List<CatDto> cats = null;
//        logger.info("Yo" + String.valueOf(records.count()));
//        for (ConsumerRecord<String, Object> record : records) {
//            String value = (String) record.value();
//            cats = objectMapper.readValue(value, new TypeReference<List<CatDto>>(){});
//        }
//
//        return new ResponseEntity<>(
//                cats,
//                HttpStatus.OK
//        );
//    }
//
//    // TODO. Допиши
//    @PostMapping("/add")
//    public ResponseEntity<CatDtoInput> addCat(@RequestBody CatDtoInput catDtoInput) {
//        kafkaTemplate.send("topic-add-cat", catDtoInput);
//
//        // TODO. Тут тоже надо вернуть кота с id
//        return ResponseEntity.status(HttpStatus.CREATED).body(catDtoInput);
//    }
//
//    // TODO. Требуется доработка
//    @PutMapping("/edit")
//    public ResponseEntity<CatDto> updateCat(@RequestBody CatDtoInput catDtoInput) throws JsonProcessingException {
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = ow.writeValueAsString(catDtoInput);
//
//        kafkaTemplate.send("topic-update-cat", json);
//
//        consumer.seekToEnd(partitions);
//        ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(5));
//
//        CatDto cat = null;
//        logger.info("Yo" + String.valueOf(records.count()));
//        for (ConsumerRecord<String, Object> record : records) {
//            String jsonOutput = (String) record.value();
//            cat = objectMapper.readValue(jsonOutput, CatDto.class);
////            cat = (CatDto) record.value();
//            logger.info("Id of cat: ");
//            logger.info(cat.getId().toString());
//        }
//
//        // TODO. Тут тоже надо вернуть кота с id
//        return ResponseEntity.status(HttpStatus.OK).body(cat);
//    }

    // TODO. Допиши
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCatById(@PathVariable Long id) {
        kafkaTemplate.send("topic-delete-cat-by-id", id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    // TODO. ДОпиши
    @GetMapping("/request/{id}")
    public ResponseEntity<?> getByRequestId(@PathVariable Long id) {
        Request request = requestResponseService.getRequestById(id);


        if (request.getStatus() == RequestStatus.PENDING) {
            return new ResponseEntity<>(
                    "Try to get cat here: /cats/request/" + request.getId(),
                    HttpStatus.ACCEPTED
            );
        }

        CatResponse response = requestResponseService.getResponseById(id);
        List<CatDto> cats = response.getCatsList();

        return new ResponseEntity<>(
                cats,
                HttpStatus.OK
        );
    }
}

