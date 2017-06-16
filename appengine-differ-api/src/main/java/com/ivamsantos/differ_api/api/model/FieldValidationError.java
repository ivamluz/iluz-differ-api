package com.ivamsantos.differ_api.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface FieldValidationError {

    @JsonProperty
    String message();

    @JsonProperty
    String field();

    @JsonProperty
    String code();

}
