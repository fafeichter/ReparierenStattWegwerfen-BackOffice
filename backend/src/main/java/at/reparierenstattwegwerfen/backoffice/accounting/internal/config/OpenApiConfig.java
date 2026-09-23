package at.reparierenstattwegwerfen.backoffice.accounting.internal.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabian Feichter
 * @since 19.06.2026
 */
@Configuration("accountingOpenApiConfig")
public class OpenApiConfig {

	@Bean
	public GroupedOpenApi accountingOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("accounting")
			.packagesToScan("at.reparierenstattwegwerfen.backoffice.accounting")
			.build();
	}
}