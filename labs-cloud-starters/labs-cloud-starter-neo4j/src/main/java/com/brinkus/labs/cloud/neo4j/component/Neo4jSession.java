/*
 * Labs Cloud Starter Service
 * Copyright (C) 2016-2018  Balazs Brinkus
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

import java.util.Map;

/**
 * The Neo4j session with discovery support.
 */
public interface Neo4jSession {

    /**
     * Execute a query where the result is a single {@link Result} object.
     *
     * @param cypher
     *         the Cypher query
     * @param parameters
     *         the query's parameters
     *
     * @return a {@link Result} instance
     */
    Result query(String cypher, Map<String, ?> parameters);

    /**
     * Execute a query that has a collection with the requested type as a response.
     *
     * @param type
     *         the class of the response object
     * @param cypher
     *         the Cypher query
     * @param parameters
     *         the query's parameters
     * @param <T>
     *         the type of the response
     *
     * @return a collection with the result objects
     */
    <T> Iterable<T> query(Class<T> type, String cypher, Map<String, ?> parameters);

}
