package at.reparierenstattwegwerfen.backoffice.config;

import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.model.google.genai.autoconfigure.chat.GoogleGenAiChatAutoConfiguration;
import org.springframework.ai.model.google.genai.autoconfigure.embedding.GoogleGenAiEmbeddingConnectionAutoConfiguration;
import org.springframework.ai.model.google.genai.autoconfigure.embedding.GoogleGenAiTextEmbeddingAutoConfiguration;
import org.springframework.ai.model.google.genai.autoconfigure.image.GoogleGenAiImageAutoConfiguration;
import org.springframework.ai.model.google.genai.autoconfigure.image.GoogleGenAiImageConnectionAutoConfiguration;
import org.springframework.ai.model.openai.autoconfigure.*;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBeans;

import java.lang.annotation.*;

/**
 * @author Fabian Feichter
 * @since 07.11.2026
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
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
@MockitoBeans({
	@MockitoBean(name = "openAiChatModel", types = OpenAiChatModel.class),
	@MockitoBean(name = "googleGenAiChatModel", types = GoogleGenAiChatModel.class)
})
public @interface MockAiModels {
}