package at.reparierenstattwegwerfen.backoffice.sparepart.internal.infrastructure.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabian Feichter
 * @since 19.06.2026
 */
@Configuration
public class SparePartOpenApiConfig {

	@Bean
	public GroupedOpenApi sparePartOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("sparepart")
			.packagesToScan("at.reparierenstattwegwerfen.backoffice.sparepart")
			.build();
	}
}