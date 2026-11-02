package at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Fabian Feichter
 * @since 22.09.2026
 */
@Data
@Builder
public class SystemInfoDto {
	private SpringInfo spring;
	@JsonProperty("java")
	private JavaInfo javaInfo; // can not be named "java" because of Lombok
	private OsInfo os;
	private DatabaseInfo database;

	@Data
	@Builder
	public static class SpringInfo {
		private String bootVersion;
		private String frameworkVersion;
		private String[] activeProfiles;
	}

	@Data
	@Builder
	public static class JavaInfo {
		private String version;
		private String vendor;
		private String runtimeName;
	}

	@Data
	@Builder
	public static class OsInfo {
		private String name;
		private String version;
		private String arch;
	}

	@Data
	@Builder
	public static class DatabaseInfo {
		private String version;
		private ConnectionPoolInfo connectionPool;
	}

	@Data
	@Builder
	public static class ConnectionPoolInfo {
		private Integer maximumPoolSize;
		private Integer minimumIdle;
		private Long idleTimeout;
		private Long maxLifetime;
		private Long connectionTimeout;
		private Long leakDetectionThreshold;

		private Integer activeConnections;
		private Integer idleConnections;
		private Integer totalConnections;
	}
}