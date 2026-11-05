package at.reparierenstattwegwerfen.backoffice;

import org.junit.jupiter.api.Test;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.model.google.genai.autoconfigure.chat.GoogleGenAiChatAutoConfiguration;
import org.springframework.ai.model.google.genai.autoconfigure.embedding.GoogleGenAiEmbeddingConnectionAutoConfiguration;
import org.springframework.ai.model.google.genai.autoconfigure.embedding.GoogleGenAiTextEmbeddingAutoConfiguration;
import org.springframework.ai.model.google.genai.autoconfigure.image.GoogleGenAiImageAutoConfiguration;
import org.springframework.ai.model.google.genai.autoconfigure.image.GoogleGenAiImageConnectionAutoConfiguration;
import org.springframework.ai.model.openai.autoconfigure.*;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 * @author Fabian Feichter
 * @since 20.05.2026
 */
@Import({
	TestcontainersConfiguration.class,
	MockedOauth2ResourceServerConfig.class
})
@SpringBootTest
@EnableAutoConfiguration(exclude = {
	OpenAiModerationAutoConfiguration.class,
	OpenAiImageAutoConfiguration.class,
	OpenAiEmbeddingAutoConfiguration.class,
	OpenAiChatAutoConfiguration.class,
	OpenAiAudioTranscriptionAutoConfiguration.class,
	OpenAiAudioSpeechAutoConfiguration.class,
	GoogleGenAiChatAutoConfiguration.class,
	GoogleGenAiImageAutoConfiguration.class,
	GoogleGenAiEmbeddingConnectionAutoConfiguration.class,
	GoogleGenAiTextEmbeddingAutoConfiguration.class,
	GoogleGenAiImageConnectionAutoConfiguration.class
})
@MockitoBean(types = {OpenAiChatModel.class, GoogleGenAiChatModel.class})
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