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

package com.brinkus.labs.cloud.neo4j.type;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity(label = "Movie")
public class Movie extends Neo4jEntity {

    @Property(name = "title")
    private String title;

    @Property(name = "tagline")
    private String tagline;

    @Property(name = "released")
    private int released;

    @Relationship(type = "ACTED_IN", direction = "INCOMING")
    List<Person> actors;

    @Relationship(type = "DIRECTED", direction = "INCOMING")
    List<Person> directors;

    @Relationship(type = "PRODUCED", direction = "INCOMING")
    List<Person> producers;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(final String tagline) {
        this.tagline = tagline;
    }

    public int getReleased() {
        return released;
    }

    public void setReleased(final int released) {
        this.released = released;
    }

    public List<Person> getActors() {
        return actors;
    }

    public void setActors(final List<Person> actors) {
        this.actors = actors;
    }

    public List<Person> getDirectors() {
        return directors;
    }

    public void setDirectors(final List<Person> directors) {
        this.directors = directors;
    }

    public List<Person> getProducers() {
        return producers;
    }

    public void setProducers(final List<Person> producers) {
        this.producers = producers;
    }
}
