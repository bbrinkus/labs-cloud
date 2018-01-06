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

package com.brinkus.labs.cloud.neo4j.driver;

import com.brinkus.labs.cloud.neo4j.config.CloudStarterTestConfig;
import com.brinkus.labs.cloud.neo4j.dao.MovieNeo4jDao;
import com.brinkus.labs.cloud.neo4j.type.Movie;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CloudStarterTestConfig.class }, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("eureka-http")
@WebAppConfiguration
public class EurekaHttpDriverIT {

    @Autowired
    MovieNeo4jDao dao;

    @Test
    public void configurationSuccess() {
        assertThat(dao, notNullValue());
    }

    @Test
    public void findTheMatrixMovie() {
        Iterable<Movie> movies = dao.findMovieByTitle("The Matrix");
        assertThat(Iterables.size(movies), is(1));

        Movie movie = Iterables.get(movies, 0);
        assertThat(movie.getTitle(), is("The Matrix"));
        assertThat(movie.getTagline(), is("Welcome to the Real World"));
        assertThat(movie.getReleased(), is(1999));

        assertThat(movie.getActors().size(), is(5));
        assertThat(movie.getActors().get(0).getName(), is("Hugo Weaving"));
        assertThat(movie.getActors().get(0).getBorn(), is(1960));
        assertThat(movie.getDirectors().size(), is(2));
        assertThat(movie.getProducers().size(), is(1));
        assertThat(movie.getProducers().get(0).getName(), is("Joel Silver"));
    }

}
