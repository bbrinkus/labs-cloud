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

package com.brinkus.labs.cloud.neo4j.config;

import com.brinkus.labs.cloud.neo4j.component.Neo4jDiscoverySession;
import com.brinkus.labs.cloud.neo4j.component.Neo4jHealthIndicator;
import com.brinkus.labs.cloud.neo4j.driver.EurekaHttpDriver;
import com.brinkus.labs.cloud.neo4j.factory.EurakaSessionFactory;
import com.brinkus.labs.cloud.neo4j.factory.EurekaHttpSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Neo4j discovery session.
 */
@Configuration
@Conditional(Neo4jDiscoveryConfigCondition.class)
public class Neo4jDiscoveryConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(Neo4jDiscoveryConfig.class);

    @Autowired
    SpringClientFactory clientFactory;

    /**
     * Create a new instance of {@link Neo4jDiscoveryConfig}.
     */
    public Neo4jDiscoveryConfig() {
        LOGGER.info("Initialize a new configuration instance {}", getClass().getSimpleName());
    }

    /**
     * Get a health indicator for Neo4j that ping the server periodically.
     *
     * @param discoverySession
     *         the Neo4j discovery session
     *
     * @return the Neo4j health indicator
     */
    @Bean
    public Neo4jHealthIndicator healthIndicator(final Neo4jDiscoverySession discoverySession) {
        return new Neo4jHealthIndicator(discoverySession);
    }

    /**
     * Get a Neo4j session that will use discovery service to execute the queries.
     *
     * @param config
     *         the configuration bean
     *
     * @return the Neo4j discovery session
     */
    @Bean
    public Neo4jDiscoverySession discoverySession(final Neo4jDiscoveryConfigBean config) {
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration();
        configuration.driverConfiguration()
                .setDriverClassName(getDriverClassName(config.getDriver()))
                .setURI(config.getServiceId());

        LOGGER.debug("Insert internal package to the packages list");
        config.getPackages().add(0, getInternalTypePackageName());
        String[] packages = config.getPackages().toArray(new String[config.getPackages().size()]);

        EurakaSessionFactory sessionFactory = getSessionFactory(config.getDriver(), configuration, packages);
        return new Neo4jDiscoverySession(sessionFactory);
    }

    private String getDriverClassName(final String driver) {
        switch (driver.toLowerCase()) {
            case "http":
                return EurekaHttpDriver.class.getTypeName();
            default:
                throw new IllegalArgumentException("Invalid driver name!");
        }
    }

    private String getInternalTypePackageName() {
        return Neo4jEntityBase.class.getPackage().getName();
    }

    private EurakaSessionFactory getSessionFactory(final String driver,
                                                   final org.neo4j.ogm.config.Configuration configuration,
                                                   final String[] packages)

    {
        switch (driver.toLowerCase()) {
            case "http":
                return new EurekaHttpSessionFactory(clientFactory, configuration, packages);
            default:
                throw new IllegalArgumentException("Invalid driver name!");
        }
    }

}
