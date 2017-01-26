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

package com.brinkus.labs.cloud.service.config;

import com.brinkus.labs.cloud.service.component.EurekaHealthCheckHandler;
import com.netflix.appinfo.AmazonInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableEurekaClient
@Conditional(EurekaClientConfigCondition.class)
public class EurekaClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaClientConfig.class);

    @Autowired(required = false)
    private HealthAggregator healthAggregator = new OrderedHealthAggregator();

    public EurekaClientConfig() {
        LOGGER.info("Initialize a new configuration instance {}", getClass().getName());
    }

    @LoadBalanced
    @Bean(name = "loadBalancedRestOperations")
    public RestOperations loadBalancedRestOperations() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    public EurekaHealthCheckHandler eurekaHealthCheckHandler() {
        return new EurekaHealthCheckHandler(healthAggregator);
    }

    @Bean
    @Autowired
    @Profile({ "aws" })
    public EurekaInstanceConfigBean eurekaInstanceConfigBean(
            final InetUtils inetUtils,
            @Value("${spring.application.name}") final String applicationName,
            @Value("${server.port}") final int port
    ) {
        LOGGER.info("Overriding Eureka instance with AWS settings for instance {}", applicationName);

        AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
        String instanceId = info.get(AmazonInfo.MetaDataKey.instanceId);
        String ipAddress = info.get(AmazonInfo.MetaDataKey.localIpv4);
        String hostname = info.get(AmazonInfo.MetaDataKey.localHostname);

        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            if (address != null && address.getHostName() != null) {
                hostname = address.getHostName();
                LOGGER.info("Overriding Amazon hostname", hostname);
            }
        } catch (UnknownHostException e) {
            LOGGER.warn("An error occurred during the hostname query. Using the amazon hostname.", e);
        }

        LOGGER.info("Eureka instance ip address: {}", ipAddress);
        LOGGER.info("Eureka instance hostname: {}", hostname);

        String fullInstanceId = String.format("%s:%s:%d", instanceId, applicationName, port).toLowerCase();

        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        config.setInstanceId(fullInstanceId);
        config.setHostname(hostname);
        config.setIpAddress(ipAddress);
        config.setNonSecurePort(port);
        config.setDataCenterInfo(info);
        return config;
    }

}
