package at.reparierenstattwegwerfen.backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.modulith.Modulith;

@Modulith(
	systemName = "Reparieren Statt Wegwerfen - Backoffice",
	sharedModules = {"shared"}
)
public class BackofficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackofficeApplication.class, args);
	}
}