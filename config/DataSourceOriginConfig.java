package pe.financieraoh.mscspfconcil.infrastucture.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceOriginConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.origin-fulxt01")
    public HikariDataSource originFulxt01DataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate originFulxt01JdbcTemplate(@Qualifier("originFulxt01DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}

