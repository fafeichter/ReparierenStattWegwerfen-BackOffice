package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabian Feichter
 * @since 19.06.2026
 */
@Configuration("businessPartnerOpenApiConfig")
public class OpenApiConfig {

	@Bean
	public GroupedOpenApi businessPartnerOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("businesspartner")
			.packagesToScan("at.reparierenstattwegwerfen.backoffice.businesspartner")
			.build();
	}
}