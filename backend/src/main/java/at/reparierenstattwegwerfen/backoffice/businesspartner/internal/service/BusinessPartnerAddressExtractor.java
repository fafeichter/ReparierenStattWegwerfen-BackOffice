package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

/**
 * Downloads an ad and asks the AI model to extract the device's specs and reported
 * condition from its text content.
 *
 * @author Fabian Feichter
 */
@Component
@RequiredArgsConstructor
@RegisterReflectionForBinding(BusinessPartnerAddressExtractResponse.class)
public class BusinessPartnerAddressExtractor {

	private final ChatClient.Builder aiClientBuilder;

	@Value("classpath:templates/businesspartner/address-extractor.mustache")
	private Resource mustacheTemplateResource;

	@Value("classpath:templates/businesspartner/country_codes.csv")
	private Resource countryCodesCsvResource;

	public BusinessPartnerAddressExtractResponse extractAddress(Resource shippingLabel) {
		UserMessage userMessage = UserMessage.builder()
			.text(mustacheTemplateResource)
			.media(new Media(new MimeType("text", "csv"), countryCodesCsvResource))
			.media(new Media(MimeTypeUtils.IMAGE_JPEG, shippingLabel))
			.build();

		BeanOutputConverter<BusinessPartnerAddressExtractResponse> output = new BeanOutputConverter<>(BusinessPartnerAddressExtractResponse.class);
		OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
			.outputSchema(output.getJsonSchema())
			.build();

		return aiClientBuilder.build()
			.prompt(new Prompt(userMessage, chatOptions))
			.call()
			.entity(BusinessPartnerAddressExtractResponse.class);
	}
}