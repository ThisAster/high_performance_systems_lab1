package com.example.clinic.auth.controller;

import com.example.clinic.auth.util.UserAlreadyExistException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ControllerExceptionHandlerTest {

    private final ControllerExceptionHandler exceptionHandler = new ControllerExceptionHandler();

    @Test
    public void testHandleUserAEE() {
        UserAlreadyExistException exception = new UserAlreadyExistException("User already exists");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<?> response = exceptionHandler.handleUserAEE(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
    }

    @Test
    public void testHandleUserBCE() {
        BadCredentialsException exception = new BadCredentialsException("Bad credentials");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<?> response = exceptionHandler.handleUserBCE(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad credentials", response.getBody());
    }

    @Test
    public void testHandleCCE() {
        ResponseEntity<?> response = exceptionHandler.handleCCE();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("You can't access this resource", response.getBody());
    }

    @Test
    public void testHandleIAE() {
        IllegalArgumentException exception = new IllegalArgumentException("Illegal argument");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<?> response = exceptionHandler.handleIAE(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Illegal argument", response.getBody());
    }
}
