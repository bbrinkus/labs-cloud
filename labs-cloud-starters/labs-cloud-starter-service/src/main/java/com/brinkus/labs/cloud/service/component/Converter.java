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