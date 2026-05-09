package com.salermo.springcrud.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    //Uso o runtimeException para tornar mais flexivel os tratamentos da exceções

    private static final long serialVersionUID = 1;

    public ResourceNotFoundException(String msg){
        super(msg);    
    }
}
