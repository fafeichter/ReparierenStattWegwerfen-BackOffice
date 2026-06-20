package at.reparierenstattwegwerfen.backoffice.device.internal.config;

import com.tngtech.archunit.core.importer.ModuleImportPlugin;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabian Feichter
 */
@Configuration("deviceOpenApiConfig")
@RegisterReflectionForBinding(ModuleImportPlugin.class)
public class OpenApiConfig {

	@Bean
	public GroupedOpenApi deviceOpenApi() {
		return GroupedOpenApi
			.builder()
			.group("device")
			.packagesToScan("at.reparierenstattwegwerfen.backoffice.device")
			.build();
	}
}