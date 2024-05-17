package ru.isaev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.Cats.Cat;
import ru.isaev.Cats.CatBreeds;
import ru.isaev.Cats.CatColors;
import ru.isaev.Mapper.IMyMapper;
import ru.isaev.OwnerDtos.OwnerDto;
import ru.isaev.Utilities.Exceptions.CatNotFoundExceptions;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CatService {
    private final CatRepository catRepository;

    private KafkaTemplate<String, Object> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final IMyMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CatService(CatRepository catRepository, KafkaTemplate<String, Object> kafkaTemplate, IMyMapper mapper) {
        this.catRepository = catRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    // TODO. Добавь логику сохранения кота (асинхронная, по гайду https://www.youtube.com/watch?v=mB91hPd2xmM&list=PLt91xr-Pp57T4kKLdavnQ0gV1X427frC2&index=15&t=1092s)
    public void addCat(Cat cat) {
//        List<Cat> friendsOfNewCat = new ArrayList<>();
//        cat.setFriendsList(friendsOfNewCat);
//        catRepository.save(cat);
//        List<Cat> catsOfOwner = currentOwner.getCatsList();
//        for (Cat catOfOwner :
//                catsOfOwner) {
//                cat.addFriend(catOfOwner, true);
//                updateCat(catOfOwner);
//        }
//        cat.setFriendsList(friendsOfNewCat);
//        cat.setOwner(currentOwner);
//
//        catsOfOwner.add(cat);
//        currentOwner.setCatsList(catsOfOwner);
//
//        catRepository.save(cat);
//        ownerDAO.save(currentOwner);
    }

    public Cat getCatById(Long id) {
        Cat cat = catRepository.findById(id).orElseThrow(
                () -> new CatNotFoundExceptions("Not found cat with id = " + id));

        return cat;
    }

    public List<Cat> getCatsByColor(CatColors color) {
        return catRepository.findByColor(color);
    }

    public List<Cat> getAllCats() {
        return catRepository.findAll();
    }

    public List<Cat> getCatsByBreed(CatBreeds breed) {
        return catRepository.findByBreed(breed);
    }

    public Cat updateCat(Cat cat) {
        catRepository.findById(cat.getId()).orElseThrow(
                () -> new CatNotFoundExceptions("No cat with id = " + cat.getId()));

        return catRepository.saveAndFlush(cat);
    }

    public void removeCatById(Long id) throws JsonProcessingException {
        Cat cat = catRepository.findById(id).orElseThrow(
                () -> new CatNotFoundExceptions("No cat with id = " + id));



        CatDto catDto = mapper.catToCatDto(cat);
        OwnerDto ownerDto = mapper.ownerToOwnerDto(cat.getOwner());
        DeleteCatFromPetsByIdDto deleteCatFromPetsByIdDto = new DeleteCatFromPetsByIdDto(ownerDto, catDto);
        String json = objectMapper.writeValueAsString(deleteCatFromPetsByIdDto);

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.
                send("topic-delete-cat-from-list-of-pets-by-id", json);

        future.join();

        logger.info("trying to delete cat");
        List<Cat> friends = cat.getFriendsList();

        for (Cat friend :
                friends) {
            List<Cat> currentList = friend.getFriendsList();
            currentList.remove(cat);
            friend.setFriendsList(currentList);
            catRepository.save(friend);
        }



        logger.info("deleting cat");

        catRepository.deleteById(id);
    }
}
