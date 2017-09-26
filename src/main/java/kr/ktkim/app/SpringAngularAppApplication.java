package kr.ktkim.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Keumtae Kim
 */
@SpringBootApplication
public class SpringAngularAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAngularAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
