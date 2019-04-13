package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.BookFileRepository;
import repository.BookRepository;
import repository.BookRepositoryImpl;

@Configuration
public class AppConfig {

    @Bean
    BookRepository bookRepository() { return new BookRepositoryImpl();}

}
