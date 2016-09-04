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

package com.brinkus.labs.cloud.service.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Represents an error message used by the exception controller advice.
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorMessage {

    /**
     * The exception's type.
     */
    @ApiModelProperty(value = "The exception's type")
    @JsonProperty(value = "type")
    private final String errorType;

    /**
     * The exception's message.
     */
    @ApiModelProperty(value = "The exception's message")
    @JsonProperty(value = "message")
    private final String message;

    /**
     * Create a new instance of {@link ErrorMessage}
     *
     * @param errorType
     * @param message
     */
    public ErrorMessage(final String errorType, final String message) {
        this.errorType = errorType;
        this.message = message;
    }

    /**
     * Get the type of the exception.
     *
     * @return the type
     */
    public String getType() {
        return errorType;
    }

    /**
     * Get the message of the exception
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

}
