package at.reparierenstattwegwerfen.backoffice.model.internal.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabian Feichter
 */
@Configuration("modelOpenApiConfig")
public class OpenApiConfig {

	@Bean
	public GroupedOpenApi modelOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("model")
			.packagesToScan("at.reparierenstattwegwerfen.backoffice.model")
			.build();
	}
}