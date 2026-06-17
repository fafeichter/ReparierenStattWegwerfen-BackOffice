package at.reparierenstattwegwerfen.backoffice;

import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Fabian Feichter
 */
@TestConfiguration
public class MockedSpringAiTestConfig {

    @Bean
    @Primary // Ensures this mock overrides any real auto-configured builder
    public ChatClient.Builder chatClientBuilder() {
        // 1. Create mocks for the entire ChatClient fluent chain
        ChatClient.Builder mockBuilder = Mockito.mock(ChatClient.Builder.class);
        ChatClient mockChatClient = Mockito.mock(ChatClient.class);
        ChatClient.ChatClientRequestSpec mockRequestSpec = Mockito.mock(ChatClient.ChatClientRequestSpec.class);
        ChatClient.CallResponseSpec mockResponseSpec = Mockito.mock(ChatClient.CallResponseSpec.class);

        // 2. Wire up the builder methods to return themselves or the next mock in line
        when(mockBuilder.build()).thenReturn(mockChatClient);

        // Handles both .prompt() and .prompt("string")
        when(mockChatClient.prompt()).thenReturn(mockRequestSpec);
        when(mockChatClient.prompt(anyString())).thenReturn(mockRequestSpec);

        // Stub out the common fluent API methods so they don't return null (and cause NullPointerExceptions)
        when(mockRequestSpec.system(anyString())).thenReturn(mockRequestSpec);
        when(mockRequestSpec.user(anyString())).thenReturn(mockRequestSpec);
        when(mockRequestSpec.options(any())).thenReturn(mockRequestSpec);

        // Wire up the final execution step
        when(mockRequestSpec.call()).thenReturn(mockResponseSpec);

        // Define a safe, default fallback response string
        when(mockResponseSpec.content()).thenReturn("MOCK_MODEL_NUMBER_123");

        return mockBuilder;
    }
}
