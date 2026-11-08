package at.reparierenstattwegwerfen.backoffice.application.internal.infrastructure;

import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * @author Fabian Feichter
 * @since 07.06.2026
 */
@Configuration
@Profile("prod")
public class AngularConfig implements WebMvcConfigurer {

	public static final String FRONTEND_LOCATION = "/static";
	public static final String STATIC_INDEX_HTML = FRONTEND_LOCATION + "/index.html";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/")
			.addResourceLocations("classpath:" + STATIC_INDEX_HTML);

		registry.addResourceHandler("/**")
			.addResourceLocations("classpath:" + FRONTEND_LOCATION + "/")
			.resourceChain(true)
			.addResolver(new PathResourceResolver() {

				@Override
				protected Resource getResource(@NonNull String resourcePath, @NonNull Resource location) throws IOException {
					Resource requestedResource = location.createRelative(resourcePath);
					return requestedResource.exists() && requestedResource.isReadable() ? requestedResource :
						new ClassPathResource(STATIC_INDEX_HTML);
				}
			});
	}
}