package pe.financieraoh.mscspfconcil.infrastucture.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackages = "pe.financieraoh.mscspfconcil.infrastucture.adapter",
        entityManagerFactoryRef = "destinationFohct17EntityManagerFactory",
        transactionManagerRef = "destinationFohct17TransactionManager"
)
@EnableTransactionManagement
public class DataSourceDestinationConfig {

    @Primary
    @Bean("destinationFohct17Properties")
    @ConfigurationProperties(prefix = "spring.datasource.destination-fohct17")
    public DataSourceProperties destinationFohct17Properties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("destinationFohct17Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.destination-fohct17")
    public HikariDataSource destinationFohct17Datasource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean("destinationFohct17EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean destinationFohct17EntityManagerFactory(
            @Qualifier("destinationFohct17Datasource") DataSource dataSource,
            EntityManagerFactoryBuilder builder){

        Map<String, String> properties = new HashMap<>();

        return builder
                .dataSource(dataSource)
                .persistenceUnit("destination-fohct17")
                .properties(properties)
                .packages("pe.financieraoh.mscspfconcil.infrastucture.entity")
                .build();

    }

    @Primary
    @Bean("destinationFohct17TransactionManager")
    public JpaTransactionManager destinationFohct17TransactionManager(@Qualifier("destinationFohct17EntityManagerFactory") LocalContainerEntityManagerFactoryBean destinationFohct17EntityManagerFactory ){
        return new JpaTransactionManager(Objects.requireNonNull(destinationFohct17EntityManagerFactory.getObject()));
    }

    @Bean(name = "destinationFohct17JdbcTemplate")
    public JdbcTemplate destinationFohct17JdbcTemplate(@Qualifier("destinationFohct17Datasource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}