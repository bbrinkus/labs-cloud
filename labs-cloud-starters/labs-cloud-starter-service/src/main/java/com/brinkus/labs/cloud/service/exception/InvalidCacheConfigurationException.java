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
