package at.reparierenstattwegwerfen.backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.modulith.Modulith;

/**
 * @author Fabian Feichter
 * @since 20.05.2026
 */
@Modulith(
	systemName = "Reparieren Statt Wegwerfen - Backoffice",
	sharedModules = {"shared"}
)
public class BackofficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackofficeApplication.class, args);
	}
}