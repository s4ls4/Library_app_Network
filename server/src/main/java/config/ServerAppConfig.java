package config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import service.BookServiceImpl;
import service.IBookService;

@Configuration
public class ServerAppConfig {
    @Bean
    RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("IBookService");
        exporter.setServiceInterface(IBookService.class);
        exporter.setService(bookService());
        //exporter.setRegistryPort(1706);
        System.out.println(exporter);
        return exporter;
    }

    @Bean
    IBookService bookService() {
        IBookService service = new BookServiceImpl();
        return service;
    }

    @Bean
    ExecutorService executorService(){
        return Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
    }
}
