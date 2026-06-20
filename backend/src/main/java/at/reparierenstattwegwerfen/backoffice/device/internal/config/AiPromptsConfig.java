package at.reparierenstattwegwerfen.backoffice.device.internal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.modulith.core.ApplicationModule;
import org.springframework.modulith.core.ApplicationModuleIdentifier;
import org.springframework.modulith.runtime.ApplicationModulesRuntime;

/**
 * @author Fabian Feichter
 */
@Configuration("deviceAiPromptsConfig")
@RequiredArgsConstructor
@Profile("prod")
public class AiPromptsConfig {

	private final ApplicationModulesRuntime runtime;

	@Bean("deviceMustacheHintsProcessor")
	public BeanFactoryInitializationAotProcessor mustacheHintsProcessor() {
		return _ -> (generationContext, beanFactoryInitializationCode) -> {
			ApplicationModuleIdentifier moduleIdentifier = runtime.get()
				.getModuleForPackage(this.getClass().getPackageName())
				.map(ApplicationModule::getIdentifier)
				.orElseThrow();

			generationContext.getRuntimeHints().resources()
				.registerPattern("prompts/" + moduleIdentifier + "/*.mustache");
		};
	}
}