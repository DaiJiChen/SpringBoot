package com.sit.jichen.config;

import com.sit.jichen.bean.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration: indicates that current class is a cong=figuration class, to replace configuration file un spring.
 * in original spring configuration file, we use <bean></bean> to add components
 * in there, we use @Bean
 */
@Configuration
public class MyAppConfig {

    // add return value of this method to container. The default id of this component  is method name
    @Bean
    public HelloService helloService() {
        System.out.println("Succeed to add component to container.");
        return new HelloService();
    }
}
