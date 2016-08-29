package com.brinkus.labs.cloud.service.component;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class EurekaHealthCheckHandler implements HealthCheckHandler, ApplicationContextAware, InitializingBean {

    private static final Map<Status, InstanceStatus> healthStatuses = new HashMap<>();

    static {
        healthStatuses.put(Status.UNKNOWN, InstanceStatus.UNKNOWN);
        healthStatuses.put(Status.OUT_OF_SERVICE, InstanceStatus.OUT_OF_SERVICE);
        healthStatuses.put(Status.UP, InstanceStatus.UP);
        healthStatuses.put(Status.DOWN, InstanceStatus.DOWN);
    }

    private final CompositeHealthIndicator healthIndicator;

    private ApplicationContext applicationContext;

    public EurekaHealthCheckHandler(HealthAggregator healthAggregator) {
        Assert.notNull(healthAggregator, "HealthAggregator must not be null");
        this.healthIndicator = new CompositeHealthIndicator(healthAggregator);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final Map<String, HealthIndicator> healthIndicators = applicationContext.getBeansOfType(HealthIndicator.class);
        for (Map.Entry<String, HealthIndicator> entry : healthIndicators.entrySet()) {
            healthIndicator.addHealthIndicator(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public InstanceStatus getStatus(InstanceStatus instanceStatus) {
        final Status status = healthIndicator.health().getStatus();
        return healthStatuses.containsKey(status) ? healthStatuses.get(status) : InstanceStatus.UNKNOWN;
    }

}