package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import service.BookService;
import service.BookServiceClient;
import service.IBookService;


public class ClientAppConfig {

    @Bean
    BookServiceClient bookServiceClient() {
        return new BookServiceClient();
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(IBookService.class);
        rmiProxyFactoryBean
                .setServiceUrl("rmi://localhost:1099/StudentService");
        return rmiProxyFactoryBean;
    }
}
