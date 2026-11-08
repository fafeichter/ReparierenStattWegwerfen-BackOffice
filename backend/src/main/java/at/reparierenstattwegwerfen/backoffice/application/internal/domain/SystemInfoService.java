package at.reparierenstattwegwerfen.backoffice.application.internal.domain;

import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto;
import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto.*;
import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto.AiModelsInfo.GeminiModelInfo;
import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto.AiModelsInfo.OpenAiModelInfo;
import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto.DatabaseInfo.ConnectionPoolInfo;
import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto.DatabaseInfo.ConnectionPoolInfo.ConnectionPoolInfoBuilder;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author Fabian Feichter
 * @since 07.11.2026
 */
@Service
@RequiredArgsConstructor
public class SystemInfoService {

	private final Environment environment;
	private final DataSource dataSource;

	@Qualifier("openAiChatModel")
	private final OpenAiChatModel openAiChatModel;

	@Qualifier("googleGenAiChatModel")
	private final GoogleGenAiChatModel geminiChatModel;

	private String cachedDatabaseName;
	private String cachedDatabaseVersion;

	@PostConstruct
	void init() throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();

			this.cachedDatabaseName = metaData.getDatabaseProductName();
			this.cachedDatabaseVersion = metaData.getDatabaseProductVersion();
		}
	}

	public SystemInfoDto getSystemInfo() {
		return SystemInfoDto.builder()
			.spring(getSpringInfo())
			.javaInfo(getJavaInfo())
			.os(getOsInfo())
			.database(getDatabaseInfo())
			.aiModels(getAiModelsInfo())
			.build();
	}

	public Boolean isRunningOnProdDbAndData() {
		boolean isRunningInProductionSystem = environment.matchesProfiles("prod");
		boolean isRunningWithTestData = environment.matchesProfiles("test-data");
		return isRunningInProductionSystem && !isRunningWithTestData;
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
		return DatabaseInfo.builder()
			.name(this.cachedDatabaseName)
			.version(this.cachedDatabaseVersion)
			.connectionPool(connectionPoolInfo)
			.build();
	}

	private AiModelsInfo getAiModelsInfo() {
		OpenAiModelInfo openAiModelInfo = getOpenAiModelInfo();
		GeminiModelInfo geminiModelInfo = getGeminiModelInfo();

		return AiModelsInfo.builder()
			.openAiModel(openAiModelInfo)
			.geminiModel(geminiModelInfo)
			.build();
	}

	private OpenAiModelInfo getOpenAiModelInfo() {
		OpenAiChatOptions openAiModelOptions = openAiChatModel.getOptions();

		return OpenAiModelInfo.builder()
			.model(openAiModelOptions.getModel())
			.reasoningEffort(openAiModelOptions.getReasoningEffort())
			.temperature(openAiModelOptions.getTemperature())
			.build();
	}

	private GeminiModelInfo getGeminiModelInfo() {
		GoogleGenAiChatOptions geminiModelOptions = geminiChatModel.getOptions();

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

		HikariPoolMXBean pool = hikari.getHikariPoolMXBean();
		ConnectionPoolInfoBuilder builder = ConnectionPoolInfo.builder()
			.maximumPoolSize(hikari.getMaximumPoolSize())
			.minimumIdle(hikari.getMinimumIdle())
			.idleTimeout(hikari.getIdleTimeout())
			.maxLifetime(hikari.getMaxLifetime())
			.connectionTimeout(hikari.getConnectionTimeout())
			.leakDetectionThreshold(hikari.getLeakDetectionThreshold())
			.activeConnections(pool.getActiveConnections())
			.idleConnections(pool.getIdleConnections())
			.totalConnections(pool.getTotalConnections());

		return builder.build();
	}
}