package com.github.kairocesar.simuladorsimplesnacional.controller.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class MyErrorController  {

    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex) {
        System.out.println(ex.getStackTrace());
        return "error";
    }


}

