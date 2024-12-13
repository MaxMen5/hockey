package ru.eltech.hockey;

import lombok.extern.log4j.Log4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.eltech.hockey.gui.MainFrame;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource("classpath:/config/postgres.properties")
@Log4j
public class Application {

    @Value("#{ environment['jdbc.url'] }")
    private String databaseUrl;

    @Value("#{ environment['jdbc.username'] }")
    private String databaseUserName = "";

    @Value("#{ environment['jdbc.password'] }")
    private String databasePassword = "";

    @Value("#{ environment['database.driverClassName'] }")
    private String driverClassName;

    @Value("#{ environment['jdbc.validation.query'] }")
    private String databaseValidationQuery;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUserName);
        dataSource.setPassword(databasePassword);
        dataSource.setValidationQuery(databaseValidationQuery);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
        ctx.getBean(MainFrame.class);
    }
}
