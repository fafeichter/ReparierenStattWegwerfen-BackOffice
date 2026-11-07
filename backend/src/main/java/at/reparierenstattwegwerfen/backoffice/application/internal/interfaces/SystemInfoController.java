package at.reparierenstattwegwerfen.backoffice.application.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto;
import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto.*;
import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto.AiModelsInfo.GeminiModelInfo;
import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto.AiModelsInfo.OpenAiModelInfo;
import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto.DatabaseInfo.ConnectionPoolInfo;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
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

	@Qualifier("openAiChatModel")
	private final OpenAiChatModel openAiChatModel;

	@Qualifier("googleGenAiChatModel")
	private final GoogleGenAiChatModel geminiChatModel;

	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping("/")
	public SystemInfoDto getSystemInfo() {
		return SystemInfoDto.builder()
			.spring(getSpringInfo())
			.javaInfo(getJavaInfo())
			.os(getOsInfo())
			.database(getDatabaseInfo())
			.aiModels(getAiModelsInfo())
			.build();
	}

	private SpringInfo getSpringInfo() {
		return SpringInfo.builder()
			.bootVersion(SpringBootVersion.getVersion())
			.frameworkVersion(SpringVersion.getVersion())
			.activeProfiles(environment.getActiveProfiles())
			.build();
	}

	private JavaInfo getJavaInfo() {
		return JavaInfo.builder()
			.version(System.getProperty("java.version"))
			.vendor(System.getProperty("java.vendor"))
			.runtimeName(System.getProperty("java.runtime.name"))
			.build();
	}

	private OsInfo getOsInfo() {
		return OsInfo.builder()
			.name(System.getProperty("os.name"))
			.version(System.getProperty("os.version"))
			.arch(System.getProperty("os.arch"))
			.build();
	}

	private DatabaseInfo getDatabaseInfo() {
		ConnectionPoolInfo connectionPoolInfo = getConnectionPoolInfo();
		String databaseVersion = entityManager.createNativeQuery("SELECT version()")
			.getSingleResult()
			.toString();
		return DatabaseInfo.builder()
			.version(databaseVersion)
			.connectionPool(connectionPoolInfo)
			.build();
	}

	private AiModelsInfo getAiModelsInfo() {
		OpenAiModelInfo openAiModelInfo = getOpenAiModelInfo(openAiChatModel.getOptions());
		GeminiModelInfo geminiModelInfo = getGeminiModelInfo(geminiChatModel.getOptions());

		return AiModelsInfo.builder()
			.openAiModel(openAiModelInfo)
			.geminiModel(geminiModelInfo)
			.build();
	}

	private OpenAiModelInfo getOpenAiModelInfo(OpenAiChatOptions openAiModelOptions) {
		return OpenAiModelInfo.builder()
			.model(openAiModelOptions.getModel())
			.reasoningEffort(openAiModelOptions.getReasoningEffort())
			.temperature(openAiModelOptions.getTemperature())
			.build();
	}

	private GeminiModelInfo getGeminiModelInfo(GoogleGenAiChatOptions geminiModelOptions) {
		return GeminiModelInfo.builder()
			.model(geminiModelOptions.getModel())
			.thinkingLevel(geminiModelOptions.getThinkingLevel() != null ? geminiModelOptions.getThinkingLevel().name() : null)
			.serviceTier(geminiModelOptions.getServiceTier() != null ? geminiModelOptions.getServiceTier().getValue() : null)
			.temperature(geminiModelOptions.getTemperature())
			.build();
	}

	private ConnectionPoolInfo getConnectionPoolInfo() {
		if (!(dataSource instanceof HikariDataSource hikari)) {
			return null;
		}

		ConnectionPoolInfo.ConnectionPoolInfoBuilder builder = ConnectionPoolInfo.builder()
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