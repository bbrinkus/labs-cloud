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

import org.neo4j.ogm.MetaData;
import org.neo4j.ogm.session.Session;

/**
 * Defines the creation and handling of the new Neo4j session with discovery service support.
 */
public interface EurakaSessionFactory {

    /**
     * Retrieves the meta-data that was built up when this {@link EurakaSessionFactory} was constructed.
     *
     * @return The underlying {@link MetaData}
     */
    MetaData metaData();

    /**
     * Opens a new Neo4j mapping {@link Session} using the Driver specified in the OGM configuration
     * The driver should be configured to connect to the database using the appropriate
     * DriverConfig
     *
     * @return A new {@link Session}
     */
    Session openSession();

}
