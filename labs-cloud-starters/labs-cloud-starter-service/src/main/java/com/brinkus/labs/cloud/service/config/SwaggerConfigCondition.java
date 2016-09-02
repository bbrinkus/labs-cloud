package com.brinkus.labs.cloud.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

/**
 * Condition to control the swagger configuration loading.
 */
@Component
public class SwaggerConfigCondition implements Condition {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfigCondition.class);

    @Override
    public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
        String property = context.getEnvironment().getProperty("labs.swagger.enabled");
        boolean isEnabled = Boolean.parseBoolean(property);
        LOGGER.info("Swagger configuration loading is enabled: {}", isEnabled);
        return isEnabled;
    }
}
