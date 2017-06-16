package com.ivamsantos.differ_api.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


/**
 * Generic error json response wrapper for API errors
 */
@JsonInclude(Include.NON_EMPTY)
public class ErrorResponse {

    /**
     * Holds redundantly the HTTP error status code, so that the developer can inspect it without having to analyze the
     * response’s header.
     */
    @JsonProperty
    private Integer status;

    /**
     * Short description of the error, what might have caused it and possibly a fixing proposal.
     */
    @JsonProperty
    private String message;

    /**
     * This is an internal code specific to the API (should be more relevant for business exceptions).
     */
    @JsonProperty
    private String code;

    /**
     * Detailed message, containing additional data that might be relevant to the developer. This should only be
     * available when the “debug” mode is switched on and could potentially contain stack trace information or
     * something similar.
     */
    @JsonProperty
    private String developerMessage;


    @JsonProperty
    private List<FieldValidationError> errors;


    public ErrorResponse() {
        super();
    }


    public ErrorResponse(Integer status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getDeveloperMessage() {
        return developerMessage;
    }


    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }


    public void addErrors(Throwable ex) {
        if (ex instanceof ValidationException) {
            errors = ((ValidationException) ex).getErrors();
        }
    }

    public int totalErrors() {
        return this.errors == null ? 0 : this.errors.size();
    }


    public List<FieldValidationError> errors() {
        return errors;
    }
}
