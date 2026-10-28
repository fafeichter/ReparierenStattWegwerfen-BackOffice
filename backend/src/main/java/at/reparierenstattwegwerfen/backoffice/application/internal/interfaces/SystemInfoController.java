package at.reparierenstattwegwerfen.backoffice.application.internal.interfaces;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author Fabian Feichter
 * @since 22.09.2026
 */
@RestController
@RequestMapping("/api/system-info")
@RequiredArgsConstructor
public class SystemInfoController {

	private final Environment environment;

	@GetMapping("/")
	public SystemInfoDto getSystemInfo() {
		return SystemInfoDto.builder()
			.springBootVersion(SpringBootVersion.getVersion())
			.springVersion(SpringVersion.getVersion())
			.springActiveProfiles(environment.getActiveProfiles())
			.javaVersion(System.getProperty("java.version"))
			.javaVendor(System.getProperty("java.vendor"))
			.javaRuntimeName(System.getProperty("java.runtime.name"))
			.osName(System.getProperty("os.name"))
			.osVersion(System.getProperty("os.version"))
			.osArch(System.getProperty("os.arch"))
			.build();
	}

	@GetMapping("/is-running-on-prod-db-and-data")
	public Boolean isRunningOnProdDbAndData() {
		boolean isRunningInProductionSystem = Arrays.asList(environment.getActiveProfiles()).contains("prod");
		boolean isRunningWithTestData = Arrays.asList(environment.getActiveProfiles()).contains("test-data");
		return isRunningInProductionSystem && !isRunningWithTestData;
	}
}