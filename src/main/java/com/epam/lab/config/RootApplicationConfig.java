package com.epam.lab.config;

import org.springframework.context.annotation.*;

@Configuration
@Import({MvcApplicationConfig.class,
        DataApplicationConfig.class,
        SecurityApplicationConfig.class,
        ThymeleafApplicationConfig.class
})
@ComponentScan("com.epam.lab.*")
@PropertySource(value = {"classpath:application.properties"})
public class RootApplicationConfig {
}
