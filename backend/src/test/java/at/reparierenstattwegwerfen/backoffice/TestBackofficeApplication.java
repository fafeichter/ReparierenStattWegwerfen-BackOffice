package at.reparierenstattwegwerfen.backoffice;

import org.springframework.boot.SpringApplication;

/**
 * @author Fabian Feichter
 * @since 20.05.2026
 */
public class TestBackofficeApplication {

	static void main(String[] args) {
		SpringApplication.from(BackofficeApplication::main).with(TestcontainersConfiguration.class).run(args);
	}
}