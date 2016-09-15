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

package com.brinkus.labs.cloud.neo4j.factory;

import com.brinkus.labs.cloud.neo4j.driver.EurekaHttpDriver;
import org.neo4j.ogm.MetaData;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.service.Components;
import org.neo4j.ogm.session.Neo4jSession;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactoryProvider;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;

public class EurekaHttpSessionFactory implements SessionFactoryProvider {

    private final SpringClientFactory clientFactory;

    private final Configuration configuration;

    private final MetaData metaData;

    public EurekaHttpSessionFactory(SpringClientFactory clientFactory, Configuration configuration, String... packages) {
        this.clientFactory = clientFactory;
        this.configuration = configuration;
        this.metaData = new MetaData(packages);
        Components.configure(this.configuration);
    }

    public MetaData metaData() {
        return metaData;
    }

    public Session openSession() {
        EurekaHttpDriver driver = new EurekaHttpDriver(clientFactory);
        driver.configure(configuration.driverConfiguration());
        return new Neo4jSession(metaData, driver);
    }

}
