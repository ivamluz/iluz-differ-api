package com.ivamsantos.differ_api.api.exception;

/**
 * Exception thrown when a entity is not found in the server.
 */
public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 649400268294693557L;

    private String id;
    private Class<?> type;

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String id, Class<?> type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String getMessage() {
        String message = "Entity not found";
        if (id != null && type != null) {
            message = "The entity of type [" + type.getCanonicalName() + "] identified by [" + id + "] was not found.";
        }

        return message;
    }
}
