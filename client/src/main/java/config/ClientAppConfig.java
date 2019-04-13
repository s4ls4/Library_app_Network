package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import ui.Console;


public class ClientAppConfig {

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(BookService.class);
        rmiProxyFactoryBean
                .setServiceUrl("rmi://localhost:1099/ClientService");
        return rmiProxyFactoryBean;
    }

    @Bean
    Console Console() {
        return new Console();
    }
}
