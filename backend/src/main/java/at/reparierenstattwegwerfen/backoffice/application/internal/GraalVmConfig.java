package at.reparierenstattwegwerfen.backoffice.application.internal;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.context.annotation.Profile;

/**
 * @author Fabian Feichter
 */
@Configuration
@ImportRuntimeHints(GraalVmConfig.class)
@Profile("prod")
public class GraalVmConfig implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        // This includes all .mustache files inside the prompts directory
        hints.resources().registerPattern("prompts/**/*.mustache");
    }
}
