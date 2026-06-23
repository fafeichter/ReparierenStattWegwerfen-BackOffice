package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import com.samskivert.mustache.Mustache;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Downloads an ad and asks the AI model to extract the device's specs and reported
 * condition from its text content.
 *
 * @author Fabian Feichter
 */
@Component
@RequiredArgsConstructor
public class ModelMatchExtractor {

	private final ChatClient.Builder aiClientBuilder;
	private final AdDownloader adDownloader;

	@Value("classpath:templates/model/apple-model-extractor.mustache")
	private Resource mustacheTemplateResource;

	// Constructor injection...

	public ModelMatchResponse extractModelDetails(String adUrl) {
		// 1. Fetch and clean data
		String adText = adDownloader.downloadAd(adUrl).getText();

		// 2. Build prompt
		String formattedPrompt = compilePrompt(adText);

		// 3. Call AI service
		BeanOutputConverter<ModelMatchResponse> output = new BeanOutputConverter<>(ModelMatchResponse.class);
		OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
			.outputSchema(output.getJsonSchema())
			.build();

		return aiClientBuilder.build()
			.prompt(new Prompt(formattedPrompt, chatOptions))
			.call()
			.entity(ModelMatchResponse.class);
	}

	@SneakyThrows
	private String compilePrompt(String content) {
		PromptContext context = PromptContext.builder().adHtmlContent(content).build();
		String templateString = mustacheTemplateResource.getContentAsString(StandardCharsets.UTF_8);
		return Mustache.compiler().compile(templateString).execute(context);
	}
}
