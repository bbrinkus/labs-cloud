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

package com.brinkus.labs.cloud.neo4j.type;

import org.neo4j.ogm.annotation.GraphId;

/**
 * Top level instance for the Neo4j entities.
 */
public class Neo4jEntityBase {

    @GraphId(name = "id")
    private Long id;

    /**
     * Get the entity's unique identifier.
     *
     * @return the unique identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the entity's unique identifier.
     *
     * @param id
     *         the unique identifier
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || id == null || getClass() != o.getClass()) {
            return false;
        }

        Neo4jEntityBase entity = (Neo4jEntityBase) o;

        if (!id.equals(entity.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (id == null) ? -1 : id.hashCode();
    }

}
