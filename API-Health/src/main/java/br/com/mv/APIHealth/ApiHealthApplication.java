package br.com.mv.APIHealth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API Health CUBO Project",
				version = "1.0.0",
				description = "This is the completion project from CUBE trainees group for Global Health"
		),
		servers = {@Server(url = "localhost:5000/")}
)
public class ApiHealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiHealthApplication.class, args);
	}

}
