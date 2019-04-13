package config;

import domain.Book;
import domain.Client;
import domain.validators.BookValidator;
import domain.validators.Validator;
import org.springframework.context.annotation.Bean;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
    @Bean
    JdbcOperations jdbcOperations() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        jdbcTemplate.setDataSource(dataSource());

        return jdbcTemplate;
    }

    @Bean
    DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();

        //TODO use env props (or property files)
        basicDataSource.setUrl("jdbc:postgresql://localhost:5432/LibraryApp");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("98blionlion");
        basicDataSource.setInitialSize(2);

        return basicDataSource;
    }

    @Bean
    Validator<Book> bookValidator() {
        return new BookValidator();
    }
}
