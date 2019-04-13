package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.BookFileRepository;
import repository.*;

@Configuration
public class AppConfig {

    @Bean
    BookRepository bookRepository() { return new BookRepositoryImpl();}

}
