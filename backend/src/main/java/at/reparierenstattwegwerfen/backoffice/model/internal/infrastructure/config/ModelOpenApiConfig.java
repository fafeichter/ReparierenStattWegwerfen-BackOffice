package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabian Feichter
 * @since 19.06.2026
 */
@Configuration
public class ModelOpenApiConfig {

	@Bean
	public GroupedOpenApi modelOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("model")
			.packagesToScan("at.reparierenstattwegwerfen.backoffice.model.internal.interfaces")
			.build();
	}
}