package ru.isaev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@SpringBootApplication
@SpringBootApplication(scanBasePackages = {"ru.isaev.Controllers", "ru.isaev.Mapper", "ru.isaev.Service", "ru.isaev.DAO", "ru.isaev.Entities"})
public class CatsRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatsRestApplication.class, args);
	}

}
