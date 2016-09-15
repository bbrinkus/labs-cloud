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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CloudStarterTestConfig.class }, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("neo4j-enabled")
public class Neo4jDiscoveryConfigEnabledIT {

    @Autowired
    ApplicationContext context;

    @Test
    public void configurationSuccess() {
        assertThat(context, notNullValue());
    }

    @Test
    public void discoverySessionEnabled() {
        Neo4jDiscoverySession discoverySession = context.getBean(Neo4jDiscoverySession.class);
        assertThat(discoverySession, notNullValue());
    }

    @Test
    public void healthIndicatorEnabled() {
        Neo4jHealthIndicator healthIndicator = context.getBean(Neo4jHealthIndicator.class);
        assertThat(healthIndicator, notNullValue());
    }

}
