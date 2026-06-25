package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import com.samskivert.mustache.Mustache;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

	@Value("classpath:templates/model/reference_data_macbook.json")
	private Resource macbookReferenceData;

	@Value("classpath:templates/model/reference_data_ipad.json")
	private Resource ipadReferenceData;

	@Value("classpath:templates/model/reference_data_combined.json")
	private Resource combinedReferenceData;

	public ModelMatchResponse extractModelDetails(String adUrl) {
		// 1. Fetch and clean data
		String adText = adDownloader.downloadAd(adUrl).getText();

		// 2. Build instructions prompt
		String formattedPrompt = compilePrompt(adText);

		// 3. Determine the correct reference data resource
		Resource referenceData = resolveReferenceResource(adUrl);

		// 4. Create the message contents (Prompt text + File attachment)
		UserMessage userMessage = UserMessage.builder()
			.text(formattedPrompt)
			.media(new Media(MimeTypeUtils.APPLICATION_JSON, referenceData))
			.build();

		// 5. Call AI service using the UserMessage
		BeanOutputConverter<ModelMatchResponse> output = new BeanOutputConverter<>(ModelMatchResponse.class);
		OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
			.outputSchema(output.getJsonSchema())
			.build();

		return aiClientBuilder.build()
			.prompt(new Prompt(userMessage, chatOptions))
			.call()
			.entity(ModelMatchResponse.class);
	}

	private Resource resolveReferenceResource(String adUrl) {
		String urlLower = adUrl.toLowerCase();

		if (urlLower.contains("macbook")) {
			return macbookReferenceData;
		} else if (urlLower.contains("ipad")) {
			return ipadReferenceData;
		}

		// Fallback
		return combinedReferenceData;
	}

	@SneakyThrows
	private String compilePrompt(String content) {
		PromptContext context = PromptContext.builder().adHtmlContent(content).build();
		InputStreamReader input = new InputStreamReader(mustacheTemplateResource.getInputStream(), StandardCharsets.UTF_8);
		return Mustache.compiler().compile(input).execute(context);
	}
}