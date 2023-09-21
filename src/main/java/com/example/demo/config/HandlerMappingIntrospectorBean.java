package com.example.demo.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class HandlerMappingIntrospectorBean {
    private final ApplicationContext applicationContext;

    public HandlerMappingIntrospectorBean(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public HandlerMappingIntrospector handlerMappingIntrospector() {
        HandlerMappingIntrospector handlerMappingIntrospector = new HandlerMappingIntrospector();

        handlerMappingIntrospector.setApplicationContext(applicationContext);
        return handlerMappingIntrospector;
    }
}
