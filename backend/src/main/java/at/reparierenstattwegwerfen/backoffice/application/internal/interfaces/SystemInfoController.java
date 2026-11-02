package at.reparierenstattwegwerfen.backoffice.application.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
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
	private final DataSource dataSource;
	private final EntityManagerFactory entityManagerFactory;

	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping("/")
	public SystemInfoDto getSystemInfo() {
		return SystemInfoDto.builder()
			.spring(getSpringInfo())
			.javaInfo(getJavaInfo())
			.os(getOsInfo())
			.database(getDatabaseInfo())
			.build();
	}

	private SystemInfoDto.SpringInfo getSpringInfo() {
		return SystemInfoDto.SpringInfo.builder()
			.bootVersion(SpringBootVersion.getVersion())
			.frameworkVersion(SpringVersion.getVersion())
			.activeProfiles(environment.getActiveProfiles())
			.build();
	}

	private SystemInfoDto.JavaInfo getJavaInfo() {
		return SystemInfoDto.JavaInfo.builder()
			.version(System.getProperty("java.version"))
			.vendor(System.getProperty("java.vendor"))
			.runtimeName(System.getProperty("java.runtime.name"))
			.build();
	}

	private SystemInfoDto.OsInfo getOsInfo() {
		return SystemInfoDto.OsInfo.builder()
			.name(System.getProperty("os.name"))
			.version(System.getProperty("os.version"))
			.arch(System.getProperty("os.arch"))
			.build();
	}

	private SystemInfoDto.DatabaseInfo getDatabaseInfo() {
		SystemInfoDto.ConnectionPoolInfo connectionPoolInfo = getConnectionPoolInfo();
		String databaseVersion = entityManager.createNativeQuery("SELECT version()")
			.getSingleResult()
			.toString();
		return SystemInfoDto.DatabaseInfo.builder()
			.version(databaseVersion)
			.connectionPool(connectionPoolInfo)
			.build();
	}

	private SystemInfoDto.ConnectionPoolInfo getConnectionPoolInfo() {
		if (!(dataSource instanceof HikariDataSource hikari)) {
			return null;
		}

		SystemInfoDto.ConnectionPoolInfo.ConnectionPoolInfoBuilder builder = SystemInfoDto.ConnectionPoolInfo.builder()
			.maximumPoolSize(hikari.getMaximumPoolSize())
			.minimumIdle(hikari.getMinimumIdle())
			.idleTimeout(hikari.getIdleTimeout())
			.maxLifetime(hikari.getMaxLifetime())
			.connectionTimeout(hikari.getConnectionTimeout())
			.leakDetectionThreshold(hikari.getLeakDetectionThreshold());

		HikariPoolMXBean pool = hikari.getHikariPoolMXBean();
		builder.activeConnections(pool.getActiveConnections())
			.idleConnections(pool.getIdleConnections())
			.totalConnections(pool.getTotalConnections());

		return builder.build();
	}

	@GetMapping("/is-running-on-prod-db-and-data")
	public Boolean isRunningOnProdDbAndData() {
		boolean isRunningInProductionSystem = Arrays.asList(environment.getActiveProfiles()).contains("prod");
		boolean isRunningWithTestData = Arrays.asList(environment.getActiveProfiles()).contains("test-data");
		return isRunningInProductionSystem && !isRunningWithTestData;
	}
}