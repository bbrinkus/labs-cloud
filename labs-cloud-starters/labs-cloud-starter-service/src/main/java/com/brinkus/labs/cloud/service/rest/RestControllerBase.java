package com.brinkus.labs.cloud.service.rest;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * Rest controller's base class to handle the responses easier.
 * <p>
 * <code>
 * return createResponse(() -> service.getCounties(countryCodes));
 * </code>
 */
public abstract class RestControllerBase {

    @FunctionalInterface
    protected interface Request<T extends Object> {

        T execute();

    }

    protected final Logger logger;

    protected RestControllerBase(final Logger logger) {
        this.logger = logger;
    }

    protected ResponseEntity createResponse(Request request) {
        try {
            Object result = request.execute();
            if (result == null || isEmptyArray(result) || isEmptyList(result)) {
                return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred during the query!", e);
            throw e;
        }
    }

    private boolean isEmptyArray(final Object result) {
        return result.getClass().isArray() && Array.getLength(result) == 0;
    }

    private boolean isEmptyList(final Object result) {
        return result instanceof Collection && ((Collection) result).isEmpty();
    }

}
