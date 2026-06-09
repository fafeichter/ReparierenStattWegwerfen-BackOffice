package at.reparierenstattwegwerfen.backoffice.application.internal;

import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.DatabaseStartupValidator;

import javax.sql.DataSource;
import java.util.stream.Stream;

/**
 * @author Fabian Feichter
 */
@Configuration
public class DatabaseConfig {

    private final static String VALIDATOR_BEAN_NAME = "databaseStartupValidator";

    @Bean(name = VALIDATOR_BEAN_NAME)
    public DatabaseStartupValidator databaseStartupValidator(DataSource dataSource) {
        DatabaseStartupValidator validator = new DatabaseStartupValidator();
        validator.setDataSource(dataSource);
        validator.setTimeout(Integer.MAX_VALUE);
        return validator;
    }

    @Bean
    public static BeanFactoryPostProcessor databaseDependencyPostProcessor() {
        return beanFactory -> {
            // Force Liquibase and JPA to initialize only after the database is started
            Class<?>[] targetBeanTypes = {
                    EntityManagerFactory.class,
                    SpringLiquibase.class
            };

            for (Class<?> type : targetBeanTypes) {
                Stream.of(beanFactory.getBeanNamesForType(type))
                        .map(beanFactory::getBeanDefinition)
                        .forEach(beanDefinition -> beanDefinition.setDependsOn(VALIDATOR_BEAN_NAME));
            }
        };
    }
}
