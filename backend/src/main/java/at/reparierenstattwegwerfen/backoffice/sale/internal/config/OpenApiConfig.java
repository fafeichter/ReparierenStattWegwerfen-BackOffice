package at.reparierenstattwegwerfen.backoffice.sale.internal.config;

import com.tngtech.archunit.core.importer.ModuleImportPlugin;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabian Feichter
 */
@Configuration("saleOpenApiConfig")
@RegisterReflectionForBinding(ModuleImportPlugin.class)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi saleOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("sale")
                .packagesToScan("at.reparierenstattwegwerfen.backoffice.sale")
                .build();
    }
}