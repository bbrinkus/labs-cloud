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
