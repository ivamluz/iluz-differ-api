package com.ivamsantos.differ_api.api.exception;

public class EntityNotFoundException extends Exception {
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
            message = "The entity of type [" + type.getCanonicalName() + "] with identified by [" + id + "] was not found";
        }

        return message;
    }

}
