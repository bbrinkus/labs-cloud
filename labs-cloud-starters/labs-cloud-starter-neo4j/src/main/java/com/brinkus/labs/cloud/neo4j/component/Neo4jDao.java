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

import com.brinkus.labs.cloud.neo4j.type.NeoEntityBase;
import org.neo4j.ogm.model.Result;

import java.util.Map;

/**
 * Top-level data accces object to handle the queries and return an instance that extended the {@link NeoEntityBase}.
 *
 * @param <T>
 *         an extended {@link NeoEntityBase} instance
 */
public abstract class Neo4jDao<T extends NeoEntityBase> {

    private final Neo4jDiscoverySession session;

    /**
     * Create a new instance of {@link Neo4jDao}
     *
     * @param session
     *         the Neo4j discovery session
     */
    public Neo4jDao(Neo4jDiscoverySession session) {
        this.session = session;
    }

    /**
     * Execute a Cypher query that has a single result.
     *
     * @param query
     *         the Cypher query
     * @param params
     *         the query's parameters
     *
     * @return the single value result
     */
    protected Result executeSingleValueQuery(String query, Map<String, ?> params) {
        return session.query(query, params);
    }

    /**
     * Execute a Cypher query.
     *
     * @param cls
     *         the return type of the response
     * @param query
     *         the Cypher query
     * @param params
     *         the query's parameters
     *
     * @return the collection of the result
     */
    protected Iterable<T> executeQuery(Class<T> cls, String query, Map<String, ?> params) {
        return session.query(cls, query, params);
    }

}