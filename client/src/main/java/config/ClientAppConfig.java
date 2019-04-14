package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import service.IBookService;
import ui.Console;


@Configuration
public class ClientAppConfig {

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(IBookService.class);
        rmiProxyFactoryBean
                .setServiceUrl("rmi://localhost:1099/IBooksService");

        return rmiProxyFactoryBean;
    }



    @Bean
    Console Console() {
        return new Console();
    }
}
