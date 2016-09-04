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

package com.brinkus.labs.cloud.service.component;

import java.util.ArrayList;
import java.util.List;

/**
 * Functional interface definition to convert the input object(s) to the output object(s).
 *
 * @param <I>
 *         the type of the ingoing object
 * @param <O>
 *         the type of the output object
 */
@FunctionalInterface
public interface Converter<I, O> {

    /**
     * Convert an object to another one.
     *
     * @param input
     *         the input object
     *
     * @return the output object
     */
    O convert(I input);

    /**
     * Convert an {@link Iterable} of input objects to an output {@link List}.
     *
     * @param inputs
     *         the input {@link Iterable}
     *
     * @return the output {@link List}
     */
    default List<O> convert(final Iterable<I> inputs) {
        List<O> outputs = new ArrayList<>();
        inputs.forEach(input -> outputs.add(convert(input)));
        return outputs;
    }

}