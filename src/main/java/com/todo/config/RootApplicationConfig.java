package com.todo.config;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@Import({MvcApplicationConfig.class,
        DataApplicationConfig.class,
        SecurityApplicationConfig.class,
        ThymeleafApplicationConfig.class
})
@ComponentScan("com.todo.*")
@EnableAsync
@PropertySource(value = {"classpath:application.properties"})
public class RootApplicationConfig {
}
