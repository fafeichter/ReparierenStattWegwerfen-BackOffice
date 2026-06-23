package at.reparierenstattwegwerfen.backoffice.model.internal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * @author Fabian Feichter
 */
@Configuration
public class AdDownloaderConfig {

	@Bean
	public RestClient adDownloaderRestClient() {
		return RestClient.create();
	}
}