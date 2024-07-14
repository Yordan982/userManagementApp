package com.management.userManagement.config;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionMapping {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class,
            HttpRequestMethodNotSupportedException.class,
            NoResourceFoundException.class,
            NoSuchElementException.class})
    @GetMapping
    public String handleException() {
        return "redirect:/user/list";
    }
}