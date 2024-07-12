package com.management.userManagement.config;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionMapping {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @GetMapping
    public String handleParseException() {
        return "redirect:/user/list";
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @GetMapping
    public String handeUnsupportedMethodException() {
        return "redirect:/user/list";
    }
}