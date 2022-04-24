package io.marklove.carinsurance;

import io.marklove.carinsurance.repository.ExtendedRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.mintpot"})
@EnableJpaRepositories(repositoryBaseClass = ExtendedRepositoryImpl.class)
@EnableScheduling
public class CarInsuranceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarInsuranceBackendApplication.class, args);
	}

}
