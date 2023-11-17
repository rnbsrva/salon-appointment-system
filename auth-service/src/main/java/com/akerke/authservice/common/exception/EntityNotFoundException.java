package com.akerke.authservice.common.exception;

/**
 * Exception thrown when an entity is not found in the database.
 */
public class EntityNotFoundException extends RuntimeException {
    /**
     * Constructs an EntityNotFoundException with a message indicating the entity's class and ID that was not found.
     *
     * @param entityClass The class of the entity.
     * @param entityId    The ID of the entity.
     */
    public EntityNotFoundException(Class<?> entityClass, Object entityId) {
        super("%s with id: %s not found".formatted(entityClass.getSimpleName(), entityId.toString()));
    }

    public EntityNotFoundException(Class<?> entityClass) {
        super("%s not found".formatted(entityClass.getSimpleName()));
    }

    public EntityNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
