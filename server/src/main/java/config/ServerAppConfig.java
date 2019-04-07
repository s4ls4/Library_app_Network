package config;

import service.BookService;
import service.IBookService;

public class ServerAppConfig {
    @Bean
    RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("BookService");
        exporter.setServiceInterface(IBookService.class);
        exporter.setService(bookService());
        return exporter;
    }

    @Bean
    BookService studentService() {
        BookService service = new BookServiceImpl();
        return service;
    }
}
