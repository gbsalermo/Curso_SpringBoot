package com.salermo.springcrud.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.salermo.springcrud.services.exceptions.DataBaseException;
import com.salermo.springcrud.services.exceptions.ResourceNotFoundException;


import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice //Permite que essa classe intercepte qualquer excessão que ocorrer
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError err = new StandardError(); 
        err.setTimeStamp(Instant.now()); //Pegando a hora do erro
        err.setStatus(status.value()); //pegando os status
        err.setError("Resource not found"); //Pegando o erro
        err.setMessage(e.getMessage()); // mensagem
        err.setPath(request.getRequestURI()); //erro do caminho
        return ResponseEntity.status(status).body(err);//retorno o corpo do err caso de erro de nao encontrado

        
    }

@ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> dataBase(DataBaseException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError err = new StandardError(); 
        err.setTimeStamp(Instant.now()); //Pegando a hora do erro
        err.setStatus(status.value()); //pegando os status
        err.setError("DataBase exception"); //Pegando o erro
        err.setMessage(e.getMessage()); // mensagem
        err.setPath(request.getRequestURI()); //erro do caminho
        return ResponseEntity.status(status).body(err);//retorno o corpo do err caso de erro de nao encontrado

    }



}
