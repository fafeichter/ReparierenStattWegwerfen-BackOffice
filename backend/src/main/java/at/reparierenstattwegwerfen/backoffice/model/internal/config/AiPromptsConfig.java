package at.reparierenstattwegwerfen.backoffice.model.internal.config;

import at.reparierenstattwegwerfen.backoffice.BackofficeApplication;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.context.annotation.Profile;
import org.springframework.modulith.core.ApplicationModule;
import org.springframework.modulith.core.ApplicationModuleIdentifier;
import org.springframework.modulith.core.ApplicationModules;

/**
 * @author Fabian Feichter
 */
@Configuration("modelAiPromptsConfig")
@ImportRuntimeHints(AiPromptsConfig.MustacheHintsRegistrar.class)
@Profile("prod")
public class AiPromptsConfig {

    public static class MustacheHintsRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            ApplicationModuleIdentifier applicationModuleIdentifier = ApplicationModules.of(BackofficeApplication.class)
                    .getModuleForPackage(this.getClass().getPackageName())
                    .map(ApplicationModule::getIdentifier)
                    .orElseThrow();

            hints.resources().registerPattern("prompts/" + applicationModuleIdentifier + "/*.mustache");
        }
    }
}
