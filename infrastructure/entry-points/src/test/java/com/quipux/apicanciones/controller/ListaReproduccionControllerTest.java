package com.quipux.apicanciones.controller;


import com.quipux.apicanciones.dto.request.CancionesRequest;
import com.quipux.apicanciones.dto.request.ListRequest;
import com.quipux.apicanciones.dto.response.CancionResponse;
import com.quipux.apicanciones.dto.response.ListaReproduccionResponse;
import com.quipux.apicanciones.service.ListaReproduccionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ListaReproduccionControllerTest {

    @Mock
    private ListaReproduccionService listaService;

    @InjectMocks
    private ListaReproduccionController listaController;

    @Test
    void testObtenerTodasLasListas() {

        ListaReproduccionResponse mockLista = new ListaReproduccionResponse();
        mockLista.setNombre("Mi lista");
        mockLista.setCanciones(List.of());
        when(listaService.obtenerTodas()).thenReturn(List.of(mockLista));

        ResponseEntity<List<ListaReproduccionResponse>> response = listaController.obtenerTodasLasListas();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Mi lista", response.getBody().get(0).getNombre());
    }

    @Test
    void testCrearListaReproduccion() {
        ListRequest request = new ListRequest();
        request.setNombre("Nueva Lista");
        request.setCanciones(List.of(new CancionesRequest()));

        ListaReproduccionResponse mockResponse = new ListaReproduccionResponse();
        mockResponse.setNombre("Nueva Lista");
        mockResponse.setCanciones(List.of(new CancionResponse()));

        when(listaService.crearListaReproduccion(request)).thenReturn(mockResponse);

        ResponseEntity<ListaReproduccionResponse> response = listaController.crearListaReproduccion(request);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Nueva Lista", response.getBody().getNombre());
        assertEquals(URI.create("/lists/Nueva+Lista"), response.getHeaders().getLocation());
    }

    @Test
    void testObtenerListaPorNombre() {

        String nombre = "Mi lista";
        ListaReproduccionResponse mockResponse = new ListaReproduccionResponse();
        mockResponse.setNombre("Mi lista");
        mockResponse.setCanciones(List.of());

        when(listaService.obtenerListaPorNombre(nombre)).thenReturn(mockResponse);

        ResponseEntity<ListaReproduccionResponse> response = listaController.obtenerListaPorNombre(nombre);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(nombre, response.getBody().getNombre());
    }

    @Test
    void testEliminarListaReproduccion() {

        String nombre = "RockHits";
        doNothing().when(listaService).eliminarListaReproduccion(nombre);

        ResponseEntity<Void> response = listaController.eliminarListaReproduccion(nombre);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

}