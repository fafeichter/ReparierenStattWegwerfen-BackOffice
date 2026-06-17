package at.reparierenstattwegwerfen.backoffice;

import org.junit.jupiter.api.Test;
import org.springframework.ai.model.openai.autoconfigure.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.core.ApplicationModules;

@Import({
        TestcontainersConfiguration.class,
        MockedOauth2ResourceServerConfig.class,
        MockedSpringAiTestConfig.class
})
@SpringBootTest
@EnableAutoConfiguration(exclude = {
        OpenAiModerationAutoConfiguration.class,
        OpenAiImageAutoConfiguration.class,
        OpenAiEmbeddingAutoConfiguration.class,
        OpenAiChatAutoConfiguration.class,
        OpenAiAudioTranscriptionAutoConfiguration.class,
        OpenAiAudioSpeechAutoConfiguration.class
})
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