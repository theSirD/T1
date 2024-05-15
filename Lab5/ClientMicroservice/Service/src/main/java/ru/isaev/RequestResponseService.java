package ru.isaev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.OwnerDtos.OwnerDto;
import ru.isaev.Requests.Request;
import ru.isaev.Requests.RequestStatus;
import ru.isaev.Responses.CatResponse;
import ru.isaev.Responses.OwnerResponse;
import ru.isaev.Utilities.Exceptions.RequestNotFoundExceptions;
import ru.isaev.Utilities.Exceptions.ResponseNotFoundException;

import java.util.List;

@Service
public class RequestResponseService {
    private RequestRepository requestRepository;

    private CatResponseRepository catResponseRepository;

    private OwnerResponseRepository ownerResponseRepository;

    private CatDtoRepository catDtoRepository;

    private OwnerDtoRepository ownerDtoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RequestResponseService(RequestRepository requestRepository, CatResponseRepository catResponseRepository, OwnerResponseRepository ownerResponseRepository, CatDtoRepository catDtoRepository, OwnerDtoRepository ownerDtoRepository) {
        this.requestRepository = requestRepository;
        this.catResponseRepository = catResponseRepository;
        this.ownerResponseRepository = ownerResponseRepository;
        this.catDtoRepository = catDtoRepository;
        this.ownerDtoRepository = ownerDtoRepository;
    }

    public Request addRequest() {
        Request request = new Request(RequestStatus.PENDING);
        return requestRepository.saveAndFlush(request);
    }

    public Request getRequestById(Long id) {
        return requestRepository.findById(id).orElseThrow( () -> new RequestNotFoundExceptions("Not found request with id = " + id));
    }

    public void updateRequest(Request request) {
        requestRepository.save(request);
    }

    public CatResponse getCatResponseById(Long id) {
        return catResponseRepository.findById(id).orElseThrow(() -> new ResponseNotFoundException("Not found response with id = " + id));
    }

    public OwnerResponse getOwnerResponseById(Long id) {
        return ownerResponseRepository.findById(id).orElseThrow(() -> new ResponseNotFoundException("Not found response with id = " + id));
    }

    // TODO. Без groupId этот метод не работает. Почему?
    @KafkaListener(topics = "topic-cat-response",
                    groupId = "group-id")
    void getCatResponse(String catResponseJson) throws JsonProcessingException {
        logger.info(catResponseJson);
        CatResponse catResponse = objectMapper.readValue(catResponseJson, CatResponse.class);
        logger.info("Service: Trying to get cat response");

        Request request = new Request(catResponse.getRequestId(), RequestStatus.COMPLETED);
        updateRequest(request);

        List<CatDto> requestedCatsList = catResponse.getCatsList();
        for (CatDto cat :
                requestedCatsList) {
            catDtoRepository.save(cat);
        }

        catResponseRepository.save(catResponse);
    }

    // TODO. Без groupId этот метод не работает. Почему?
    @KafkaListener(topics = "topic-owner-response",
            groupId = "group-id")
    void getOwnerResponse(String ownerResponseJson) throws JsonProcessingException {
        logger.info(ownerResponseJson);
        OwnerResponse ownerResponse = objectMapper.readValue(ownerResponseJson, OwnerResponse.class);
        logger.info("Service: Trying to get owner response");

        Request request = new Request(ownerResponse.getRequestId(), RequestStatus.COMPLETED);
        updateRequest(request);

        List<OwnerDto> requestedOwnersList = ownerResponse.getOwnersList();
        for (OwnerDto ownerDto :
                requestedOwnersList) {
            ownerDtoRepository.save(ownerDto);
        }

        ownerResponseRepository.save(ownerResponse);
    }
}
