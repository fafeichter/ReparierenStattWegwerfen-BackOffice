package at.reparierenstattwegwerfen.backoffice.application.internal;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabian Feichter
 * @since 22.09.2026
 */
@Configuration("applicationOpenApiConfig")
public class OpenApiConfig {

	@Bean
	public GroupedOpenApi applicationOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("application")
			.packagesToScan("at.reparierenstattwegwerfen.backoffice.application")
			.build();
	}
}