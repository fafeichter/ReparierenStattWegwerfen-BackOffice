package at.reparierenstattwegwerfen.backoffice;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
public class ResourceReader {

	public static String readFileToString(String fileLocation) throws IOException {
		return FileUtils.readFileToString(ResourceUtils.getFile(fileLocation), StandardCharsets.UTF_8);
	}
}