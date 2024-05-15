package ru.isaev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.isaev.OwnerDtos.OwnerDto;
import ru.isaev.OwnerDtos.OwnerDtoInput;
import ru.isaev.OwnerRequestDtos.RequestOwnerByIdDto;
import ru.isaev.OwnerRequestDtos.RequestOwnerWithDtoDto;
import ru.isaev.Requests.Request;
import ru.isaev.Requests.RequestStatus;
import ru.isaev.Responses.OwnerResponse;

import java.util.List;


@RestController
@RequestMapping("/owners")
public class OwnerController {
    private KafkaTemplate<String, Object> kafkaTemplate;

    private RequestResponseService requestResponseService;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public OwnerController(KafkaTemplate<String, Object> kafkaTemplate, RequestResponseService requestResponseService) {
        this.kafkaTemplate = kafkaTemplate;
        this.requestResponseService = requestResponseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable Long id) throws JsonProcessingException {
        Request request = requestResponseService.addRequest();
        RequestOwnerByIdDto requestByIdDto = new RequestOwnerByIdDto(request.getId(), id);

        String json = mapper.writeValueAsString(requestByIdDto);
        kafkaTemplate.send("topic-get-owner-by-id", json);


        return new ResponseEntity<>(
                "Try to get owner here: /owners/request/" + request.getId(),
                HttpStatus.ACCEPTED
        );
    }


//    @PostMapping("/add")
//    public ResponseEntity<OwnerDto> addOwner(@RequestBody OwnerDto ownerDto) {
//        Owner owner = mapper.ownerDtoToOwner(ownerDto);
//        ownerService.addOwner(owner);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(ownerDto);
//    }

    @PostMapping("/edit")
    public ResponseEntity<String> editOwner(@RequestBody OwnerDto ownerDto) throws JsonProcessingException {
        Request request = requestResponseService.addRequest();
        RequestOwnerWithDtoDto requestWithInputDtoDto = new RequestOwnerWithDtoDto(request.getId(), ownerDto);

        String json = mapper.writeValueAsString(requestWithInputDtoDto);
        kafkaTemplate.send("topic-update-owner", json);

        return new ResponseEntity<>(
                "Try to get owner here: /owner/request/" + request.getId(),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOwnerById(@PathVariable Long id) throws JsonProcessingException {
        Request request = requestResponseService.addRequest();
        RequestOwnerByIdDto requestByIdDto = new RequestOwnerByIdDto(request.getId(), id);

        String json = mapper.writeValueAsString(requestByIdDto);
        kafkaTemplate.send("topic-delete-owner-by-id", json);

        return new ResponseEntity<>(
                "Try to get owner here: /owners/request/" + request.getId(),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<?> getByRequestId(@PathVariable Long id) {
        Request request = requestResponseService.getRequestById(id);


        if (request.getStatus() == RequestStatus.PENDING) {
            return new ResponseEntity<>(
                    "Try to get owner here: /owners/request/" + request.getId(),
                    HttpStatus.ACCEPTED
            );
        }

        OwnerResponse response = requestResponseService.getOwnerResponseById(id);
        List<OwnerDto> owners = response.getOwnersList();

        return new ResponseEntity<>(
                owners,
                HttpStatus.OK
        );
    }
}

