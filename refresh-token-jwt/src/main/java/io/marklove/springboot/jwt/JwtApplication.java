package io.marklove.springboot.jwt;

import io.marklove.springboot.jwt.validations.MessageInterpolatorImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.validation.Validation;

@SpringBootApplication
public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);

		Validation.byDefaultProvider().configure().messageInterpolator(
				new MessageInterpolatorImpl(
						Validation.byDefaultProvider().configure().getDefaultMessageInterpolator())
		);
	}
}
