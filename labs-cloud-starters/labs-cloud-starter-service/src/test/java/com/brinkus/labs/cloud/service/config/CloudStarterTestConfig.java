package com.brinkus.labs.cloud.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = { "com.brinkus.labs" })
@Configuration
@EnableAutoConfiguration
public class CloudStarterTestConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloudStarterTestConfig.class);

    @Autowired
    ApplicationContext context;

    public CloudStarterTestConfig() {
        LOGGER.info("Initialize a new configuration instance {}", getClass().getSimpleName());
    }

}
