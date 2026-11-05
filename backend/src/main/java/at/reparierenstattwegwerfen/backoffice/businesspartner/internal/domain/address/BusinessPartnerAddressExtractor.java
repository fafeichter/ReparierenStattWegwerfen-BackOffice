package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

/**
 * @author Fabian Feichter
 * @since 10.07.2026
 */
@Component
@RequiredArgsConstructor
@RegisterReflectionForBinding(BusinessPartnerAddressExtractResponse.class)
public class BusinessPartnerAddressExtractor {

	@Qualifier("geminiChatClientBuilder")
	private final ChatClient.Builder geminiChatClientBuilder;

	private final GoogleGenAiChatModel geminiChatModel;

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

		BeanOutputConverter<BusinessPartnerAddressExtractResponse> output =
			new BeanOutputConverter<>(BusinessPartnerAddressExtractResponse.class);

		GoogleGenAiChatOptions chatOptions = geminiChatModel.getOptions().mutate()
			.outputSchema(output.getJsonSchema())
			.build();

		return geminiChatClientBuilder.build()
			.prompt(new Prompt(userMessage, chatOptions))
			.call()
			.entity(output);
	}
}