package fr.fms;

import fr.fms.dao.CategoryRepository;
import fr.fms.dao.TrainingRepository;
import fr.fms.entities.AppRole;
import fr.fms.entities.AppUser;
import fr.fms.entities.Category;
import fr.fms.entities.Training;
import fr.fms.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ApiTrainingsApplication implements CommandLineRunner {
	@Autowired
	TrainingRepository trainingRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	AccountServiceImpl accountService;
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiTrainingsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	generateData();
	generateUserRoles();
	}

	private void generateData() {
		Category language = categoryRepository.save(new Category(null,"Langage de programmation",null));
		Category frameworks = categoryRepository.save(new Category(null,"Frameworks",null));
		Category cms = categoryRepository.save(new Category(null,"CMS",null));
		trainingRepository.save(new Training(null,"Java","Java Standard Edition 8 sur 10 jours",3500,1,"unknown.png",language));
		trainingRepository.save(new Training(null,"Php","Initiation au Dev/Web avec php 4 jours",1300,1,"unknown.png",language));
		trainingRepository.save(new Training(null,"Python","Formation Python 7 jours",1500,1,"unknown.png",language));
		trainingRepository.save(new Training(null,"C#","Formation C# 5 jours",1500,1,"unknown.png",language));
		trainingRepository.save(new Training(null,"Javascript","Formation Javascript 5 jours",2000,1,"unknown.png",language));

		trainingRepository.save(new Training(null,"Spring","Spring Boot/Mvc/Sec 10 jours",5000,1, "unknown.png",frameworks));
		trainingRepository.save(new Training(null,"Symfony","Symfony Mvc 5 jours",2500,1,"unknown.png",frameworks));
		trainingRepository.save(new Training(null,"Django","Django 5 jours",3000,1,"unknown.png",frameworks));
		trainingRepository.save(new Training(null,"DotNet","DotNet & entityframework en 5 jours",2750,1,"unknown.png",frameworks));
		trainingRepository.save(new Training(null,"NodeJs","Prise en main de NodeJs/Express 2 jours",1400,1,"unknown.png",frameworks));

		trainingRepository.save(new Training(null,"WordPress","Formation Worpress 2 jours",500,1,"unknown.png",cms));
		trainingRepository.save(new Training(null,"PrestaShop","Formation PrestaShop 3 jours",1000,1,"unknown.png",cms));
		trainingRepository.save(new Training(null,"Magento","Formation Magento 4 jours",1300,1,"unknown.png",cms));
	}
	private void generateUserRoles(){
		accountService.saveUser(new AppUser(null, "rory", "12345", new ArrayList<>()));
		accountService.saveUser(new AppUser(null, "ladyhead", "12345", new ArrayList<>()));
		accountService.saveRole(new AppRole(null, "ADMIN"));
		accountService.saveRole(new AppRole(null, "USER"));
		accountService.addRoleToUser("rory", "ADMIN");
		accountService.addRoleToUser("rory", "USER");
		accountService.addRoleToUser("ladyhead", "USER");
	}
}
