package com.konteye.sidy.patientsmvc;

import com.konteye.sidy.patientsmvc.entities.Patient;
import com.konteye.sidy.patientsmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class PatientsMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientsMvcApplication.class, args);
	}
//	@Bean
	CommandLineRunner commandLineRunner(PatientRepository patientRepository){
		return args -> {
			patientRepository.save(
					new Patient(null,"Mouhammed",new Date(),false,131));
			patientRepository.save(
					new Patient(null,"Moussa",new Date(),true,145));
			patientRepository.save(
					new Patient(null,"Mor",new Date(),false,123));
			patientRepository.save(
					new Patient(null,"Djibril",new Date(),true,148));
			List<Patient> patients = patientRepository.findAll();
			patients.forEach(
					patient -> System.out.println(patient.getName())
			);
		};
	}

}
