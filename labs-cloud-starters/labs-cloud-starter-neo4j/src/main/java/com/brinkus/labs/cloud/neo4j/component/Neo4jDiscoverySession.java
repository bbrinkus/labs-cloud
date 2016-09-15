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

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactoryProvider;

import java.util.Map;

public class Neo4jDiscoverySession {

    private final Session session;

    public Neo4jDiscoverySession(final SessionFactoryProvider sessionFactory) {
        this.session = sessionFactory.openSession();
    }

    public Result query(String cypher, Map<String, ?> parameters) {
        return session.query(cypher, parameters);
    }

    public <T> Iterable<T> query(Class<T> type, String cypher, Map<String, ?> parameters) {
        return session.query(type, cypher, parameters);
    }

}
