package at.reparierenstattwegwerfen.backoffice.application.internal.interfaces;

import lombok.Builder;
import lombok.Data;

/**
 * @author Fabian Feichter
 * @since 22.09.2026
 */
@Data
@Builder
public class SystemInfoDto {
	private String springBootVersion;
	private String springVersion;
	private String[] springActiveProfiles;
	private String javaVersion;
	private String javaVendor;
	private String javaRuntimeName;
	private String osName;
	private String osVersion;
	private String osArch;
}