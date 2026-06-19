package at.reparierenstattwegwerfen.backoffice.sale.internal.config;

import at.reparierenstattwegwerfen.backoffice.BackofficeApplication;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.modulith.core.ApplicationModule;
import org.springframework.modulith.core.ApplicationModules;

/**
 * @author Fabian Feichter
 */
@Configuration("saleOpenApiConfig")
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi saleApi() {
        ApplicationModule module = ApplicationModules.of(BackofficeApplication.class)
                .getModuleForPackage(this.getClass().getPackageName())
                .orElseThrow();

        return GroupedOpenApi
                .builder()
                .group(module.getIdentifier().toString())
                .packagesToScan(module.getBasePackage().getPackageName().toString())
                .build();
    }
}