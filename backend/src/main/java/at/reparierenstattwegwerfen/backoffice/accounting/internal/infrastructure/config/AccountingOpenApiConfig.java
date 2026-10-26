package at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabian Feichter
 * @since 19.06.2026
 */
@Configuration
public class AccountingOpenApiConfig {

	@Bean
	public GroupedOpenApi accountingOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("accounting")
			.packagesToScan("at.reparierenstattwegwerfen.backoffice.accounting.internal.interfaces")
			.build();
	}
}