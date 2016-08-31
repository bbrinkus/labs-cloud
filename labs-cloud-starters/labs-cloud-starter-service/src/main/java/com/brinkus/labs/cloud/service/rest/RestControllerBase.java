package com.brinkus.labs.cloud.service.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Rest controller's base class to handle the responses easier.
 * <p>
 * <code>
 * return createResponse(() -> service.getCounties(countryCodes));
 * </code>
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
     * @throws Exception
     *         rethrow the supplier expression's error
     */
    protected ResponseEntity createResponse(Supplier supplier) {
        Assert.notNull(supplier);

        Object result;
        try {
            result = supplier.get();
        } catch (Exception e) {
            throw e;
        }

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
