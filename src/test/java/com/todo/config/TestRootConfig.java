package com.todo.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;


@Configuration
@Import({MvcApplicationConfig.class,
        DataApplicationConfig.class,
//        SecurityApplicationConfig.class,
        ThymeleafApplicationConfig.class
})
//Scans within the base package of the application for @Component classes to configure as beans
@ComponentScan("com.epam.lab.*")
//@ImportResource("WEB-INF/appconfig-security.xml")
@PropertySource(value = {"classpath:testApplication.properties"})
public class TestRootConfig {
}
