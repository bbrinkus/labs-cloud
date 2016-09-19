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

package com.brinkus.labs.cloud.neo4j.component;

import com.google.common.collect.Iterables;
import org.neo4j.ogm.model.Result;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.Collections;

/**
 * Health check solution for Neo4j that ping the server periodically to get a health status.
 */
public class Neo4jHealthIndicator implements HealthIndicator {

    private static final String DESCRIPTION = "description";

    private static final String PING_CYPHER = "MATCH (n) RETURN count(*)";

    private final Neo4jDiscoverySession discoverySession;

    /**
     * Create a new instance of {@link Neo4jHealthIndicator}
     *
     * @param discoverySession
     *         the Neo4j discovery session
     */
    public Neo4jHealthIndicator(Neo4jDiscoverySession discoverySession) {
        this.discoverySession = discoverySession;
    }

    @Override
    public Health health() {
        try {
            Result result = discoverySession.query(PING_CYPHER, Collections.emptyMap());
            if (Iterables.size(result.queryResults()) != 0) {
                return Health.up().withDetail(DESCRIPTION, "Neo4j is up and running").build();
            } else {
                return Health.down().withDetail(DESCRIPTION, "Neo4j ping query was invalid").build();
            }
        } catch (Exception e) {
            return Health.outOfService().withDetail(DESCRIPTION, "Neo4j is not reachable").withException(e).build();
        }
    }

}