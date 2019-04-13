package config;

import domain.Book;
import domain.Client;
import domain.validators.BookValidator;
import domain.validators.Validator;

import javax.sql.DataSource;

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
        basicDataSource.setUrl("jdbc:postgresql://localhost:5432/Library_app_Network");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("mpp1234");
        basicDataSource.setInitialSize(2);

        return basicDataSource;
    }

    @Bean
    Validator<Book> bookValidator() {
        return new BookValidator();
    }
}
