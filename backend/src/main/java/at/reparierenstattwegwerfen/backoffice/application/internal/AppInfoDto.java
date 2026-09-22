package at.reparierenstattwegwerfen.backoffice.application.internal;

import lombok.Builder;
import lombok.Data;

/**
 * @author Fabian Feichter
 * @since 22.09.2026
 */
@Data
@Builder
public class AppInfoDto {
	private String springBootVersion;
	private String springVersion;
	private String javaVersion;
	private String javaVendor;
	private String javaRuntimeName;
	private String hibernateVersion;
	private String osName;
	private String osVersion;
	private String osArch;
}