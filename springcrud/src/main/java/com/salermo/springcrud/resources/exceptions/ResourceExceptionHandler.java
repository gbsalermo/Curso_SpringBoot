package com.salermo.springcrud.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.salermo.springcrud.services.exceptions.EntityNotFoundException;


import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice //Permite que essa classe intercepte qualquer excessão que ocorrer
public class ResourceExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        StandardError err = new StandardError(); 
        err.setTimeStamp(Instant.now()); //Pegando a hora do erro
        err.setStatus(HttpStatus.NOT_FOUND.value()); //pegando os status
        err.setError("Resource not found"); //Pegando o erro
        err.setMessage(e.getMessage()); // mensaagem
        err.setPath(request.getRequestURI()); //erro do caminho
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);//retorno o corpo do err caso de erro de nao encontrado

        
    }

}
