package at.reparierenstattwegwerfen.backoffice;

import org.junit.jupiter.api.Test;
import org.springframework.ai.model.openai.autoconfigure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.core.ApplicationModule;
import org.springframework.modulith.core.ApplicationModuleIdentifier;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.springdoc.core.utils.Constants.DEFAULT_API_DOCS_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Fabian Feichter
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import({
	TestcontainersConfiguration.class,
	MockedOauth2ResourceServerConfig.class,
	MockedSpringAiTestConfig.class
})
@EnableAutoConfiguration(exclude = {
	OpenAiModerationAutoConfiguration.class,
	OpenAiImageAutoConfiguration.class,
	OpenAiEmbeddingAutoConfiguration.class,
	OpenAiChatAutoConfiguration.class,
	OpenAiAudioTranscriptionAutoConfiguration.class,
	OpenAiAudioSpeechAutoConfiguration.class
})
public class OpenApiSpecsGenerator {
	private static final List<String> IGNORE_MODULES = List.of(
		"application",
		"shared"
	);

	@Autowired
	private MockMvc mockMvc;

	@Test
	void generateOpenApiSpecs() throws Exception {
		List<String> modules = ApplicationModules.of(BackofficeApplication.class)
			.stream()
			.map(ApplicationModule::getIdentifier)
			.map(ApplicationModuleIdentifier::toString)
			.filter(module -> !IGNORE_MODULES.contains(module))
			.toList();

		Path directory = Paths.get("build/openapi");
		Files.createDirectories(directory);

		for (String module : modules) {
			mockMvc.perform(get(DEFAULT_API_DOCS_URL + "/" + module))
				.andExpect(status().isOk())
				.andDo(result -> {
					Path openApiFile = directory.resolve(module + ".json");
					Files.write(openApiFile, result.getResponse().getContentAsByteArray());
				});
		}
	}
}