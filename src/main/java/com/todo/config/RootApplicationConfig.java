package com.todo.config;

import org.springframework.context.annotation.*;

@Configuration
@Import({MvcApplicationConfig.class,
        DataApplicationConfig.class,
        SecurityApplicationConfig.class,
        ThymeleafApplicationConfig.class
})
@ComponentScan("com.todo.*")
@PropertySource(value = {"classpath:application.properties"})
public class RootApplicationConfig {
}
