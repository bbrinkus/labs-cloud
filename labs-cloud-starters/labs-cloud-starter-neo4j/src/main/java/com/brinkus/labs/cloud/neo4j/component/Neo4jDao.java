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

public abstract class Neo4jDao<T extends NeoEntityBase> {

    private final Neo4jDiscoverySession session;

    public Neo4jDao(Neo4jDiscoverySession session) {
        this.session = session;
    }

    protected Result executeSingleValueQuery(String query, Map<String, Object> params) {
        return session.query(query, params);
    }

    protected Iterable<T> executeQuery(Class<T> cls, String query, Map<String, Object> params) {
        return session.query(cls, query, params);
    }

}