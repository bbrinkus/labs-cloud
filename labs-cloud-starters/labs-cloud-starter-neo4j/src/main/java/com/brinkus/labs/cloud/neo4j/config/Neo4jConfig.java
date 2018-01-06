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

import com.brinkus.labs.cloud.neo4j.component.Neo4jHealthIndicator;
import com.brinkus.labs.cloud.neo4j.component.Neo4jSession;
import com.brinkus.labs.cloud.neo4j.component.Neo4jSessionImpl;
import com.brinkus.labs.cloud.neo4j.factory.BoltSessionFactory;
import com.brinkus.labs.cloud.neo4j.factory.EurekaHttpSessionFactory;
import com.brinkus.labs.cloud.neo4j.factory.Neo4jSessionFactory;
import com.brinkus.labs.cloud.neo4j.type.Neo4jEntity;
import org.neo4j.ogm.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Configuration for Neo4j discovery session.
 */
@Configuration
public class Neo4jConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(Neo4jConfig.class);

    @Autowired
    SpringClientFactory clientFactory;

    /**
     * Create a new instance of {@link Neo4jConfig}.
     */
    public Neo4jConfig() {
        LOGGER.info("Initialize a new configuration instance {}", getClass().getSimpleName());
    }

    /**
     * Get a health indicator for Neo4j that ping the server periodically.
     *
     * @param session
     *         the Neo4j discovery session
     *
     * @return the Neo4j health indicator
     */
    @Bean
    @ConditionalOnProperty(name = "labs.neo4j.enabled", havingValue = "true")
    public Neo4jHealthIndicator neo4jHealthIndicator(final Neo4jSession session) {
        return new Neo4jHealthIndicator(session);
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
    @ConditionalOnProperty(name = "labs.neo4j.enabled", havingValue = "true")
    public Neo4jSession neo4jSession(final Neo4jConfigBean config) {
        org.neo4j.ogm.config.Configuration configuration = getOgmConfiguration(config);

        LOGGER.debug("Insert internal package to the packages list");
        config.getPackages().add(0, getInternalTypePackageName());
        String[] packages = config.getPackages().toArray(new String[config.getPackages().size()]);

        Neo4jSessionFactory sessionFactory = getSessionFactory(config.getDriver(), configuration, packages);

        return new Neo4jSessionImpl(sessionFactory);
    }

    /**
     * Get a dummy Neo4j session to make sure that the implemented DAO classes are working.
     *
     * @return the Neo4j discovery session
     */
    @Bean
    @ConditionalOnProperty(name = "labs.neo4j.enabled", havingValue = "false", matchIfMissing = true)
    public Neo4jSession neo4jSessionDisabled() {
        return new Neo4jSession() {
            @Override
            public Result query(final String cypher, final Map<String, ?> parameters) {
                throw new UnsupportedOperationException("The labs neo4j configuration is missing or not enabled!");
            }

            @Override
            public <T> Iterable<T> query(final Class<T> type, final String cypher, final Map<String, ?> parameters) {
                throw new UnsupportedOperationException("The labs neo4j configuration is missing or not enabled!");
            }
        };
    }

    private org.neo4j.ogm.config.Configuration getOgmConfiguration(final Neo4jConfigBean config) {
        org.neo4j.ogm.config.Configuration.Builder builder = new org.neo4j.ogm.config.Configuration.Builder();

        switch (config.getDriver().toLowerCase()) {
            case "eureka-http":
                // Unfortunately we need to use an URI scheme
                builder.uri(String.format("http://%s", config.getEureka().getServiceId()));
                break;
            case "bolt":
                String uri = String.format("bolt://%s:%d", config.getBolt().getHost(), config.getBolt().getPort());
                builder.uri(uri)
                        .connectionPoolSize(config.getBolt().getConnectionPool());
                break;
            default:
                throw new IllegalArgumentException("Invalid driver name!");
        }

        return builder.build();
    }

    private String getInternalTypePackageName() {
        return Neo4jEntity.class.getPackage().getName();
    }

    private Neo4jSessionFactory getSessionFactory(final String driver,
                                                  final org.neo4j.ogm.config.Configuration configuration,
                                                  final String[] packages) {
        switch (driver.toLowerCase()) {
            case "eureka-http":
                return new EurekaHttpSessionFactory(clientFactory, configuration, packages);
            case "bolt":
                return new BoltSessionFactory(clientFactory, configuration, packages);
            default:
                throw new IllegalArgumentException("Invalid driver name!");
        }
    }

}
