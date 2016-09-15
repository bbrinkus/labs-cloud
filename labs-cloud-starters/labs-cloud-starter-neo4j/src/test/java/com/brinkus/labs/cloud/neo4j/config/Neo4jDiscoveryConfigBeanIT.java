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

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CloudStarterTestConfig.class }, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("it")
@WebAppConfiguration
public class Neo4jDiscoveryConfigBeanIT {

    @Autowired
    Neo4jDiscoveryConfigBean configBean;

    @Test
    public void configurationSuccess() {
        assertThat(configBean, Matchers.notNullValue());
    }

    @Test
    public void testConfigurationLoaded() {
        assertThat(configBean.isEnabled(), is(true));
        assertThat(configBean.getDriver(), is("http"));
        assertThat(configBean.getServiceId(), is("neo4j"));

        assertThat(configBean.getPackages(), notNullValue());
        assertThat(configBean.getPackages().size(), is(2));
        assertThat(configBean.getPackages().get(0), is("com.brinkus.labs.cloud.neo4j.type"));
        assertThat(configBean.getPackages().get(1), is("com.brinkus.labs.cloud.neo4j.type2"));
    }

}
