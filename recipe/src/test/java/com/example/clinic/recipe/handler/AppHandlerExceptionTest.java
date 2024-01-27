package com.example.clinic.recipe.handler;

import com.example.clinic.recipe.exception.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppHandlerExceptionTest {

    private final AppHandlerException appHandlerException = new AppHandlerException();

    @Test
    void handleEntityNotFoundException() {
        EntityNotFoundException ex = new EntityNotFoundException("Entity not found");
        ResponseEntity<Map<String, Object>> response = appHandlerException.handleEntityNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Entity Not Found", response.getBody().get("error"));
        assertEquals("Entity not found", response.getBody().get("message"));
    }

    @Test
    void handleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");
        ResponseEntity<Map<String, Object>> response = appHandlerException.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad Request", response.getBody().get("error"));
        assertEquals("Invalid argument", response.getBody().get("message"));
    }

    @Test
    void handleGeneralException() {
        Exception ex = new Exception("General error");
        ResponseEntity<Map<String, Object>> response = appHandlerException.handleGeneralException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", response.getBody().get("error"));
        assertEquals("An unexpected error occurred", response.getBody().get("message"));
    }

    @Test
    void handleNotFoundException() {
        NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "Resource not found");
        ResponseEntity<Map<String, Object>> response = appHandlerException.handleNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not Found", response.getBody().get("error"));
        assertEquals("Resource not found", response.getBody().get("message"));
    }

    @Test
    void handleMethodArgumentTypeMismatchException() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(null, null, null, null, null);
        ResponseEntity<Map<String, Object>> response = appHandlerException.handleMethodArgumentTypeMismatchException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad Request", response.getBody().get("error"));
        assertEquals("Invalid parameter type", response.getBody().get("message"));
    }

    @Test
    void handleBindException() {
        Object target = new Object();
        BindException ex = new BindException(target, "target");

        ResponseEntity<Map<String, Object>> response = appHandlerException.handleBindException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad Request", response.getBody().get("error"));
        assertEquals("Invalid request", response.getBody().get("message"));
    }

    @Test
    void handleHttpMessageConversionException() {
        HttpMessageConversionException ex = new HttpMessageConversionException("Conversion error");
        ResponseEntity<Map<String, Object>> response = appHandlerException.handleHttpMessageConversionException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad Request", response.getBody().get("error"));
        assertEquals("Invalid request", response.getBody().get("message"));
    }

    @Test
    void handleBadRequestException() {
        BadRequestException ex = new BadRequestException("Bad request");
        ResponseEntity<Map<String, Object>> response = appHandlerException.handleBadRequestException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad Request", response.getBody().get("error"));
        assertEquals("Bad request", response.getBody().get("message"));
    }
}
