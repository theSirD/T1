package ru.isaev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.Requests.Request;
import ru.isaev.Requests.RequestStatus;
import ru.isaev.Responses.CatResponse;
import ru.isaev.Utilities.Exceptions.RequestNotFoundExceptions;
import ru.isaev.Utilities.Exceptions.ResponseNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestResponseService {
    private RequestRepository requestRepository;

    private ResponseRepository responseRepository;

    private CatDtoRepository catDtoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RequestResponseService(RequestRepository requestRepository, ResponseRepository responseRepository, CatDtoRepository catDtoRepository) {
        this.requestRepository = requestRepository;
        this.responseRepository = responseRepository;
        this.catDtoRepository = catDtoRepository;
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

    public CatResponse getResponseById(Long id) {
        return responseRepository.findById(id).orElseThrow(() -> new ResponseNotFoundException("Not found response with id = " + id));
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

        responseRepository.save(catResponse);
    }
}
