package at.reparierenstattwegwerfen.backoffice.application.internal;

import lombok.RequiredArgsConstructor;
import org.hibernate.Version;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Feichter
 * @since 22.09.2026
 */
@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class AppInfoController {

	@GetMapping("/spring-boot-version")
	public AppInfoDto getAppInfo() {
		return AppInfoDto.builder()
			.springBootVersion(SpringBootVersion.getVersion())
			.springVersion(SpringVersion.getVersion())
			.javaVersion(System.getProperty("java.version"))
			.javaVendor(System.getProperty("java.vendor"))
			.javaRuntimeName(System.getProperty("java.runtime.name"))
			.hibernateVersion(Version.getVersionString())
			.osName(System.getProperty("os.name"))
			.osVersion(System.getProperty("os.version"))
			.osArch(System.getProperty("os.arch"))
			.build();
	}
}
