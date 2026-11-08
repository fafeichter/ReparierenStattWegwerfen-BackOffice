package at.reparierenstattwegwerfen.backoffice.application.internal.infrastructure;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Fabian Feichter
 * @since 05.11.2026
 */
@Configuration
public class ChatClientsConfig {

	@Bean
	@Primary
	ChatClient.Builder openaiChatClientBuilder(OpenAiChatModel openaiChatModel) {
		return ChatClient.builder(openaiChatModel);
	}

	@Bean
	ChatClient.Builder geminiChatClientBuilder(GoogleGenAiChatModel geminiChatModel) {
		return ChatClient.builder(geminiChatModel);
	}
}