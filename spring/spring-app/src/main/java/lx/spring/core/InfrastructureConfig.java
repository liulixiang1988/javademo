package lx.spring.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by liulixiang on 16/8/10.
 */
@Configuration
public class InfrastructureConfig {
    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource();
    }
}
