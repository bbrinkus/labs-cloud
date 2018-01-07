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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.ogm.model.QueryStatistics;
import org.neo4j.ogm.model.Result;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Neo4jHealthIndicatorTest {

    private class HealthResult implements Result {

        private List<Map<String, Object>> result;

        public HealthResult() {
            this(null);
        }

        public HealthResult(Map<String, Object> map) {
            result = new ArrayList<>();
            if (map != null) {
                result.add(map);
            }
        }

        @Override
        public Iterable<Map<String, Object>> queryResults() {
            return result;
        }

        @Override
        public QueryStatistics queryStatistics() {
            return null;
        }

        @Override
        public Iterator<Map<String, Object>> iterator() {
            return null;
        }

    }

    private Neo4jHealthIndicator neoHealthIndicator;

    private Neo4jSession session;

    @Before
    public void setUp() {
        session = mock(Neo4jSession.class);
        neoHealthIndicator = new Neo4jHealthIndicator(session);
    }

    @After
    public void tearDown() {
        neoHealthIndicator = null;
        session = null;
    }

    @Test
    public void healthUp() {
        HealthResult result = new HealthResult(new HashMap<>());

        when(session.query(anyString(), anyMap())).thenReturn(result);

        Health health = neoHealthIndicator.health();
        assertThat(health.getStatus(), is(Status.UP));
        assertThat(health.getDetails().get("description"), is("Neo4j is up and running"));
    }

    @Test
    public void healthDown() {
        HealthResult result = new HealthResult();

        when(session.query(anyString(), anyMap())).thenReturn(result);

        Health health = neoHealthIndicator.health();
        assertThat(health.getStatus(), is(Status.DOWN));
        assertThat(health.getDetails().get("description"), is("Neo4j ping query was invalid"));
    }

    @Test
    public void healthException() {
        when(session.query(anyString(), anyMap())).thenThrow(Exception.class);

        Health health = neoHealthIndicator.health();
        assertThat(health.getStatus(), is(Status.OUT_OF_SERVICE));
        assertThat(health.getDetails().get("description"), is("Neo4j is not reachable"));
    }

}
