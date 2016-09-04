/*
 * Labs Cloud Starter Service
 * Copyright (C) 2016  Balazs Brinkus
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

/**
 * Health check handler that can inform the Eureka if the service has health problem.
 */
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

    /**
     * Create a new instance of {@link EurekaHealthCheckHandler}.
     *
     * @param healthAggregator
     *         the components health aggregator
     */
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