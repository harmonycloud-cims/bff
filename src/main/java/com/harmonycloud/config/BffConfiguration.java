package com.harmonycloud.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BffConfiguration {
    @Bean
    @ConditionalOnMissingBean(BffConfigurationProperties.class)
    public BffConfigurationProperties configProperties() {
        return new BffConfigurationProperties();
    }
}