package com.brinkus.labs.cloud.service.rest;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public abstract class RestControllerBase {

    protected static Logger logger;

    @FunctionalInterface
    public interface Request<T> {

        T execute();

    }

    protected ResponseEntity createResponse(Request request) {
        try {
            Object result = request.execute();
            if (result == null || result instanceof Collection && ((Collection) result).isEmpty()) {
                return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred during the query!", e);
            throw e;
        }
    }

}
