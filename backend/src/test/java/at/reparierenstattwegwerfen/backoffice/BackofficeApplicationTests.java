package at.reparierenstattwegwerfen.backoffice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.core.ApplicationModules;

@Import({TestcontainersConfiguration.class, MockedOauth2ResourceServerConfig.class})
@SpringBootTest
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