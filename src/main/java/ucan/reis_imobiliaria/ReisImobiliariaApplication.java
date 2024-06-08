package ucan.reis_imobiliaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Enable Spring's scheduled task execution
public class ReisImobiliariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReisImobiliariaApplication.class, args);
	}
}
 