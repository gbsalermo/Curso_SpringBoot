package com.salermo.springcrud.services.exceptions;

public class DataBaseException extends RuntimeException {

    //Uso o runtimeException para tornar mais flexivel os tratamentos da exceções

    private static final long serialVersionUID = 1L;

    public DataBaseException(String msg){
        super(msg);    
    }
}
