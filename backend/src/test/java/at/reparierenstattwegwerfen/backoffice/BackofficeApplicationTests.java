package at.reparierenstattwegwerfen.backoffice;

import at.reparierenstattwegwerfen.backoffice.config.MockAiModels;
import at.reparierenstattwegwerfen.backoffice.config.MockedOauth2ResourceServerConfig;
import at.reparierenstattwegwerfen.backoffice.config.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.core.ApplicationModules;

/**
 * @author Fabian Feichter
 * @since 20.05.2026
 */
@Import({
	TestcontainersConfiguration.class,
	MockedOauth2ResourceServerConfig.class
})
@SpringBootTest
@MockAiModels
class BackofficeApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void createApplicationModuleModel() {
		ApplicationModules modules = ApplicationModules.of(BackofficeApplication.class);
		modules.forEach(System.out::println);
	}

	@Test
	void verifiesModularStructure() {
		ApplicationModules modules = ApplicationModules.of(BackofficeApplication.class);
		modules.verify();
	}
}