package com.brinkus.labs.cloud.service.exception;

/**
 * Invalid cache configuration file runtime exception.
 */
public class InvalidCacheConfigurationException extends RuntimeException {

    /**
     * Create a new instance of {@link InvalidCacheConfigurationException}.
     *
     * @param message
     *         the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public InvalidCacheConfigurationException(final String message) {
        super(message);
    }

}
