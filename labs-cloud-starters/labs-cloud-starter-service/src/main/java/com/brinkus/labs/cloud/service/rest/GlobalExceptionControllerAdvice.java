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

import com.brinkus.labs.cloud.service.type.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller advice to handle {@link Exception} and {@link IllegalArgumentException} exceptions.
 */
@ControllerAdvice
public class GlobalExceptionControllerAdvice {

    /**
     * Handle {@link Exception} exceptions.
     *
     * @param e
     *         the exception instance
     *
     * @return a response the details of the error and HTTP status code 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGeneralException(Exception e) {
        ErrorMessage message = new ErrorMessage(e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle {@link IllegalArgumentException} exceptions.
     *
     * @param e
     *         the exception instance
     *
     * @return a response the details of the error and HTTP status code 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(Exception e) {
        ErrorMessage message = new ErrorMessage(e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}
