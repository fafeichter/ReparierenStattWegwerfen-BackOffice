package at.reparierenstattwegwerfen.backoffice;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

/**
 * @author Fabian Feichter
 */
public class DocumentationTests {

	ApplicationModules modules = ApplicationModules.of(BackofficeApplication.class);

	@Test
	public void writeDocumentationSnippets() {
		new Documenter(modules)
			.writeModulesAsPlantUml();
	}
}