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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuration for Neo4j discovery session.
 * <p>
 * Example configuration file:
 * <pre><code>
 *  labs:
 *    neo4j:
 *      enabled: true
 *      driver: http
 *      serviceId: neo4j
 *      packages: com.brinkus.labs.cloud.neo4j.type, com.brinkus.labs.cloud.neo4j.type2
 * </code></pre>
 */
@Component
@ConfigurationProperties(prefix = "labs.neo4j")
public class Neo4jDiscoveryConfigBean {

    private boolean enabled;

    private String driver;

    private String serviceId;

    private List<String> packages;

    /**
     * Flag to indicate that the Neo4j discovery session is enabled or disabled state.
     *
     * @return the flag value (default false)
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set the cache enabled or disabled state.
     *
     * @param enabled
     *         the cache state
     */
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get the name of the connection's driver (only http supported at this moment).
     *
     * @return the name of the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Set the name of the connection's driver (only http supported at this moment).
     *
     * @param driver
     *         the name of the driver
     */
    public void setDriver(final String driver) {
        this.driver = driver;
    }

    /**
     * Get the service identifier of the Neo4j instances.
     *
     * @return the service identifier
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * Set the service identifier of the Neo4j instances.
     *
     * @param serviceId
     *         the service identifier
     */
    public void setServiceId(final String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Get the meta packages that are containing the Neo4j entity representations.
     *
     * @return the list of the meta packages
     */
    public List<String> getPackages() {
        return packages;
    }

    /**
     * Set the meta packages that are containing the Neo4j entity representations.
     *
     * @param packages
     *         the list of the meta packages
     */
    public void setPackages(final List<String> packages) {
        this.packages = packages;
    }
}
