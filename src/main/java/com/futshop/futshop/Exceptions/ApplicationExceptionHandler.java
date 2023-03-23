package com.futshop.futshop.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity usuarioInexistente(Exception e){
        return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
