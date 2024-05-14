package ru.isaev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.security.access.prepost.PostFilter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.bind.annotation.*;
import ru.isaev.CatDtos.CatDto;
import ru.isaev.CatDtos.CatDtoInput;
import ru.isaev.Cats.CatBreeds;
import ru.isaev.Cats.CatColors;

import java.time.Duration;
import java.util.*;


@RestController
@RequestMapping("/cats")
public class CatController {
    private KafkaTemplate<String, Object> kafkaTemplate;

    private KafkaConsumer<String, Object> consumer;

//    private  List<TopicPartition> partitions = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CatController(KafkaTemplate<String, Object> kafkaTemplate, KafkaConsumer<String, Object> kafkaConsumer) {
        this.kafkaTemplate = kafkaTemplate;
        this.consumer = kafkaConsumer;

//        partitions.add(new TopicPartition("topic-cat-response", 0));
//        partitions.add(new TopicPartition("topic-list-of-cats-response", 0));
//        partitions.add( new TopicPartition("topic-cat-id-response", 0));
//        consumer.assign(partitions);

        consumer.subscribe(Arrays.asList("topic-cat-response", "topic-list-of-cats-response", "topic-cat-id-response"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDto> getById(@PathVariable Long id) throws JsonProcessingException {

        kafkaTemplate.send("topic-get-cat-by-id", id);
//
//        Properties consumerProperties = new Properties();
//        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        consumerProperties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
//        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
//        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(consumerProperties);

//        TopicPartition partition = new TopicPartition("topic-cat-response", 0);
//        List<TopicPartition> partitions = new ArrayList<>();
//        partitions.add(partition);
//        consumer.assign(partitions);

//        TopicPartition partition = partitions.get(0);
//        int messagesToRetrieve = 1;
//        consumer.seekToEnd(partitions);
//        long startIndex = consumer.position(partition) - messagesToRetrieve;
//        consumer.seek(partition, startIndex);
//        ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(5));
//
//        CatDto cat = null;
//        logger.info("Yo" + String.valueOf(records.count()));
//        for (ConsumerRecord<String, Object> record : records) {
//            cat = (CatDto) record.value();
//            logger.info("Id of cat: ");
//            logger.info(cat.getId().toString());
//        }


        ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(2));

        List<ConsumerRecord<String, Object>> listOfRecords = new ArrayList<>();
        for (ConsumerRecord<String, Object> record : records) {
            listOfRecords.add(record);
        }

        ConsumerRecord<String, Object> lastRecord = listOfRecords.get(listOfRecords.size() - 1);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String tmp = (String) lastRecord.value();
//        CatDto cat = objectMapper.reader()
//                .forType(CatDto.class)
//                .readValue(tmp);

//        CatDto cat = objectMapper.readValue((String) lastRecord.value(), CatDto.class);
//        Link link = objectMapper.readValue(jsonString, type)
        CatDto cat = (CatDto) lastRecord.value();
        logger.info("Yo " + cat.getId().toString());

        // TODO надо отправить пользователю кота. И в других эндпоинтах похожая история
        return new ResponseEntity<>(
                cat,
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
    public ResponseEntity<List<CatDto>> getByColor(@RequestParam(name = "color", required = false) CatColors color) throws JsonProcessingException {
        kafkaTemplate.send("topic-get-cats-by-color", color.ordinal());

//        TopicPartition partition = partitions.get(1);
//        int messagesToRetrieve = 1;
//        consumer.seekToEnd(partitions);
//        long startIndex = consumer.position(partition) - messagesToRetrieve;
//        consumer.seek(partition, startIndex);
//        ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(5));
//
//        List<CatDto> cats = new ArrayList<>();
//        logger.info("Yo" + String.valueOf(records.count()));
//        for (ConsumerRecord<String, Object> record : records) {
//            cats = (List<CatDto>) record.value();
//            logger.info("Many id of cats: ");
//
//            for (CatDto cat :
//                    cats) {
//                logger.info(cat.getId().toString());
//            }
//        }

        ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(2));

        List<ConsumerRecord<String, Object>> listOfRecords = new ArrayList<>();
        for (ConsumerRecord<String, Object> record : records) {
            listOfRecords.add(record);
        }



        ConsumerRecord<String, Object> lastRecord = listOfRecords.get(listOfRecords.size() - 1);
        String tmp = (String) lastRecord.value();

        ObjectMapper objectMapper = new ObjectMapper();
        List<CatDto> cats = objectMapper.readValue(tmp, new TypeReference<List<CatDto>>(){});
//        List<CatDto> cats = new ArrayList<>();
//        for (Object obj :
//                tmp) {
//            CatDto cat = (CatDto) obj;
//            cats.add(cat);
//        }


        return new ResponseEntity<>(
                cats,
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

