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

package com.brinkus.labs.cloud.neo4j.dao;

import com.brinkus.labs.cloud.neo4j.component.Neo4jDao;
import com.brinkus.labs.cloud.neo4j.component.Neo4jSession;
import com.brinkus.labs.cloud.neo4j.component.Neo4jSession;
import com.brinkus.labs.cloud.neo4j.type.Movie;
import org.neo4j.ogm.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class MovieNeo4jDao extends Neo4jDao<Movie> {

    @Autowired
    public MovieNeo4jDao(Neo4jSession session) {
        super(session);
    }

    public long getMoviesCount() {
        final String query = "MATCH (m:Movie) RETURN count(m) as moviesCount";

        Result result = executeSingleValueQuery(query, Collections.emptyMap());
        Map<String, Object> countMap = result.iterator().next();
        return (Long) countMap.get("moviesCount");
    }

    public Iterable<Movie> findMovieByTitle(final String title) {
        final String query = "MATCH (m:Movie)-[r]-(p:Person) WHERE m.title={title} RETURN *";

        Map<String, Object> params = new HashMap<>();
        params.put("title", title);

        return executeQuery(Movie.class, query, params);
    }

}
