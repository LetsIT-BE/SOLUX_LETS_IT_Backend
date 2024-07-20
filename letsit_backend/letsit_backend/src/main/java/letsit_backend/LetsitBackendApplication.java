package letsit_backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class LetsitBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LetsitBackendApplication.class, args);
		log.info("Application started successfully.");
	}

}
