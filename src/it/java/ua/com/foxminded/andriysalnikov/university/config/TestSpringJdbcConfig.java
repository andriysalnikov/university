package ua.com.foxminded.andriysalnikov.university.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ua.com.foxminded.andriysalnikov.university")
@PropertySource("classpath:application-test.properties")
public class TestSpringJdbcConfig {

    private final Environment environment;

    @Autowired
    public TestSpringJdbcConfig(Environment environment) {
        this.environment = environment;
    }

    private DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.test.driver"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.test.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.test.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.test.password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

}
