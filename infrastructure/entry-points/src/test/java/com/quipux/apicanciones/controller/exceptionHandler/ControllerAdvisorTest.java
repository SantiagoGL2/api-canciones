package com.quipux.apicanciones.controller.exceptionHandler;

import com.quipux.apicanciones.exception.CancionesException;
import com.quipux.apicanciones.exception.ConsultasException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class ControllerAdvisorTest {

    private ControllerAdvisor advisor;

    @BeforeEach
    void setUp() {
        advisor = new ControllerAdvisor();
    }

    @Test
    void testHandleCancionesException() {
        CancionesException ex = new CancionesException("Error de canciones");
        ResponseEntity<?> response = advisor.handleCancionesException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error de canciones", response.getBody());
    }

    @Test
    void testHandleConsultasException() {
        ConsultasException ex = new ConsultasException("Consulta no encontrada");
        ResponseEntity<?> response = advisor.handleConsultasException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Consulta no encontrada", response.getBody());
    }

    @Test
    void testHandleNoSuchElementException() {
        NoSuchElementException ex = new NoSuchElementException("Elemento no encontrado");
        ResponseEntity<String> response = advisor.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Elemento no encontrado", response.getBody());
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Argumento inválido");
        ResponseEntity<String> response = advisor.handleBadRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Argumento inválido", response.getBody());
    }

    @Test
    void testHandleMethodArgumentNotValidException() {
        // Simular el error de validación en un campo
        FieldError fieldError = new FieldError("obj", "campo", "El campo es obligatorio");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<?> response = advisor.validacionRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El campo es obligatorio", response.getBody());
    }

}