package com.akerke.salonservice.exception;

public class EntityNotFoundException extends  RuntimeException{
    public EntityNotFoundException(Class<?> c , Object id){
        super("%s with id: %s not found".formatted(c.getSimpleName(), id.toString()));
    }
}
