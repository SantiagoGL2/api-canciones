package com.quipux.apicanciones.service;

import com.quipux.apicanciones.dto.request.ListRequest;
import com.quipux.apicanciones.dto.response.ListaReproduccionResponse;
import com.quipux.apicanciones.dtos.ListaReproduccionDTO;
import com.quipux.apicanciones.port.IListaReproduccionPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListaReproduccionServiceTest {

    @Mock
    private IListaReproduccionPort listaReproduccionPort;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ListaReproduccionService service;

    @Test
    void testObtenerTodas() {
        ListaReproduccionDTO dto = new ListaReproduccionDTO();
        ListaReproduccionResponse response = new ListaReproduccionResponse();

        when(listaReproduccionPort.obtenerTodas()).thenReturn(List.of(dto));

        List<ListaReproduccionResponse> result = service.obtenerTodas();

        assertEquals(1, result.size());
    }

    @Test
    void testCrearListaReproduccion() {
        ListRequest request = new ListRequest();
        request.setNombre("Test");
        ListaReproduccionDTO saved = new ListaReproduccionDTO();
        saved.setNombre("Test");
        ListaReproduccionResponse response = new ListaReproduccionResponse();
        response.setNombre("Test");

        when(listaReproduccionPort.crearListaReproduccion(any(ListaReproduccionDTO.class))).thenReturn(saved);

        ListaReproduccionResponse result = service.crearListaReproduccion(request);

        assertEquals(response.getNombre(), result.getNombre());
    }

    @Test
    void testObtenerListaPorNombre() {
        ListaReproduccionDTO dto = new ListaReproduccionDTO();
        dto.setNombre("favoritas");
        ListaReproduccionResponse response = new ListaReproduccionResponse();
        response.setNombre("favoritas");

        when(listaReproduccionPort.obtenerListaPorNombre("favoritas")).thenReturn(dto);

        ListaReproduccionResponse result = service.obtenerListaPorNombre("favoritas");

        assertEquals(response.getNombre(), result.getNombre());
    }

    @Test
    void testEliminarListaReproduccion() {
        assertDoesNotThrow(() -> service.eliminarListaReproduccion("rock"));
        verify(listaReproduccionPort).eliminarListaPorNombre("rock");
    }
}