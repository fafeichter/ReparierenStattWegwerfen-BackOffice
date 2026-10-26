package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabian Feichter
 * @since 19.06.2026
 */
@Configuration
public class DeviceOpenApiConfig {

	@Bean
	public GroupedOpenApi deviceOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("device")
			.packagesToScan("at.reparierenstattwegwerfen.backoffice.device.internal.interfaces")
			.build();
	}
}