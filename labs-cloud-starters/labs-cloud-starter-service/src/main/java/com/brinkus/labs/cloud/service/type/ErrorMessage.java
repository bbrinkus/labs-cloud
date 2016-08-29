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
    private String errorType;

    /**
     * The exception's message.
     */
    @ApiModelProperty(value = "The exception's message")
    @JsonProperty(value = "message")
    private String message;

    public ErrorMessage() {
        // default
    }

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

    public String getType() {
        return errorType;
    }

    public void setErrorType(final String errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
