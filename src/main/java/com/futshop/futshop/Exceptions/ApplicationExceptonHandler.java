package com.futshop.futshop.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.rmi.AlreadyBoundException;

@ControllerAdvice
public class ApplicationExceptonHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyBoundException.class)
    public ResponseEntity errosDuplicidade(Exception e){
        return new ResponseEntity("Este email ja foi cadastrado, por favor digite outro!", HttpStatus.ALREADY_REPORTED);
    }
}
