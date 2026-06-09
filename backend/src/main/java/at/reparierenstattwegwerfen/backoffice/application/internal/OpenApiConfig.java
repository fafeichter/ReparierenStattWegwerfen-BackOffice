package at.reparierenstattwegwerfen.backoffice.application.internal;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabian Feichter
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi modelApi() {
        return GroupedOpenApi
                .builder()
                .group("model")
                .packagesToScan("at.reparierenstattwegwerfen.backoffice.model.internal.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi businessPartnerApi() {
        return GroupedOpenApi
                .builder()
                .group("businesspartner")
                .packagesToScan("at.reparierenstattwegwerfen.backoffice.businesspartner.internal")
                .build();
    }

    @Bean
    public GroupedOpenApi deviceApi() {
        return GroupedOpenApi
                .builder()
                .group("device")
                .packagesToScan("at.reparierenstattwegwerfen.backoffice.device.internal")
                .build();
    }

    @Bean
    public GroupedOpenApi invoiceApi() {
        return GroupedOpenApi
                .builder()
                .group("invoice")
                .packagesToScan("at.reparierenstattwegwerfen.backoffice.invoice.internal")
                .build();
    }
}