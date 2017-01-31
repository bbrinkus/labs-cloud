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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Eureka discovery client configuration bean to load external configuration.
 * <p>
 * Example configuration file:
 * <pre><code>
 *  labs:
 *    eureka:
 *      enabled: true
 * </code></pre>
 */
@Component
@ConfigurationProperties(prefix = "labs.eureka")
public class EurekaDiscoveryClientConfigBean {

    private boolean enabled;

    /**
     * Flag to indicate that the swagger is enabled or disabled state.
     *
     * @return the flag value (default false)
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set the swagger enabled or disabled state.
     *
     * @param enabled
     *         the cache state
     */
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

}
