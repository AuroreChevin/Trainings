package fr.fms;

import fr.fms.dao.TrainingRepository;
import fr.fms.entities.AppRole;
import fr.fms.entities.AppUser;
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
		trainingRepository.save(new Training(null, "Java", "Java SE 8 sur 5 jours", 3500, 1));
		trainingRepository.save(new Training(null, "DotNet", "DotNet & entity framework sur 5 jours", 2750, 1));
		trainingRepository.save(new Training(null, "PowerBi", "Business Intelligence sur 5 jours", 3000, 1));
		trainingRepository.save(new Training(null, "NodeJs", "Prise en main de NodeJs/Express sur 2 jours", 1400, 1));
		trainingRepository.save(new Training(null, "Php", "Initiation au Dév/Web avec Php sur 4 jours", 1300, 1));
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
