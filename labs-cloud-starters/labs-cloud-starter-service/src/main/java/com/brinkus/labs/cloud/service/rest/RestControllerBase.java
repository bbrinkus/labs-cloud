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

package com.brinkus.labs.cloud.service.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Rest controller's base class to handle the responses easier.
 * <pre>{@code
 *  return createResponse(() -> service.getCounties(countryCodes));
 * }</pre>
 */
public abstract class RestControllerBase {

    /**
     * Create an HTTP response entity from the given supplier's response.
     * <p>
     * The application will return No Content (204) the supplier's response is null or empty array/list.
     * Otherwise if there is no exception it will return with Ok (200)
     *
     * @param supplier
     *         the functional interface that executes the expression and returns with a value
     *
     * @return the response entity with status code 200 or 204
     *
     * @throws IllegalArgumentException
     *         if the supplier is null
     */
    protected ResponseEntity createResponse(Supplier supplier) {
        Assert.notNull(supplier);

        Object result = supplier.get();
        if (result == null || isEmptyArray(result) || isEmptyList(result)) {
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private boolean isEmptyArray(final Object result) {
        return result.getClass().isArray() && Array.getLength(result) == 0;
    }

    private boolean isEmptyList(final Object result) {
        return result instanceof Collection && ((Collection) result).isEmpty();
    }

}
