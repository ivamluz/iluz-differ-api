package com.ivamsantos.differ_api.api.model;

import com.google.common.collect.Lists;

import java.util.List;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;
    private List<FieldValidationError> errors;

    private ValidationException(String code, String message) {
        super(message);

        this.code = code;
    }

    private ValidationException(String code, String message, String field) {
        super(message);

        this.code = code;
        this.errors = Lists.newArrayListWithExpectedSize(1);
        this.errors.add(new ValidationError(code, field, message));
    }

    public static void throwNewValidationException(String code, String message, String field) {
        throw new ValidationException(code, message, field);
    }

    public static ValidationException newValidationException(String code, String message) {
        return new ValidationException(code, message);
    }

    public ValidationException addError(String code, String message, String field) {
        if (this.errors == null) {
            this.errors = Lists.newArrayList();
        }

        this.errors.add(new ValidationError(code, field, message));
        return this;
    }

    public String getCode() {
        return code;
    }

    public List<FieldValidationError> getErrors() {
        return errors;
    }

    private static class ValidationError implements FieldValidationError {

        private String code;
        private String field;
        private String message;

        public ValidationError(String code, String field, String message) {
            super();
            this.code = code;
            this.field = field;
            this.message = message;
        }

        @Override
        public String code() {
            return code;
        }

        @Override
        public String field() {
            return field;
        }

        @Override
        public String message() {
            return message;
        }

    }

}
