package com.quipux.apicanciones.adapter;

import com.quipux.apicanciones.dtos.ListaReproduccionDTO;
import com.quipux.apicanciones.enums.ExceptionResponse;
import com.quipux.apicanciones.exception.CancionesException;
import com.quipux.apicanciones.exception.ConsultasException;
import com.quipux.apicanciones.ports.IListaReproduccionPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListaReproduccionAdapterTest {



    @Mock
    private IListaReproduccionPersistencePort iListaReproduccionPersistencePort;

    @InjectMocks
    private ListaReproduccionAdapter listaReproduccionAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería crear una lista de reproducción exitosamente cuando no existe")
    void crearListaReproduccion_CuandoNoExiste_DeberiaCrearExitosamente() {
        ListaReproduccionDTO nuevaLista = new ListaReproduccionDTO();
        nuevaLista.setNombre("Mi Nueva Lista");

        when(iListaReproduccionPersistencePort.obtenerListaPorNombre(nuevaLista.getNombre()))
                .thenReturn(new ListaReproduccionDTO());

        when(iListaReproduccionPersistencePort.crearListaReproduccion(nuevaLista))
                .thenReturn(nuevaLista);

        ListaReproduccionDTO resultado = listaReproduccionAdapter.crearListaReproduccion(nuevaLista);

        assertNotNull(resultado);
        assertEquals("Mi Nueva Lista", resultado.getNombre());

        verify(iListaReproduccionPersistencePort, times(1)).obtenerListaPorNombre(nuevaLista.getNombre());
        verify(iListaReproduccionPersistencePort, times(1)).crearListaReproduccion(nuevaLista);
    }

    @Test
    @DisplayName("Debería lanzar CancionesException cuando el nombre de la lista es nulo")
    void crearListaReproduccion_CuandoNombreEsNulo_DeberiaLanzarExcepcion() {

        ListaReproduccionDTO listaConNombreNulo = new ListaReproduccionDTO();
        listaConNombreNulo.setNombre(null);

        CancionesException exception = assertThrows(CancionesException.class, () ->
                listaReproduccionAdapter.crearListaReproduccion(listaConNombreNulo)
        );

        assertEquals(ExceptionResponse.NOMBRE_VACIO.getMensaje(), exception.getMessage());

        verify(iListaReproduccionPersistencePort, never()).obtenerListaPorNombre(anyString());
        verify(iListaReproduccionPersistencePort, never()).crearListaReproduccion(any(ListaReproduccionDTO.class));
    }

    @Test
    @DisplayName("Debería lanzar CancionesException cuando la lista ya existe")
    void crearListaReproduccion_CuandoListaYaExiste_DeberiaLanzarExcepcion() {
        ListaReproduccionDTO listaExistente = new ListaReproduccionDTO();
        listaExistente.setNombre("Lista Existente");

        ListaReproduccionDTO listaEncontrada = new ListaReproduccionDTO();
        listaEncontrada.setNombre("Lista Existente");
        when(iListaReproduccionPersistencePort.obtenerListaPorNombre(listaExistente.getNombre()))
                .thenReturn(listaEncontrada);

        CancionesException exception = assertThrows(CancionesException.class, () ->
                listaReproduccionAdapter.crearListaReproduccion(listaExistente)
        );

        assertEquals(ExceptionResponse.LISTA_EXISTENTE.getMensaje(), exception.getMessage());

        verify(iListaReproduccionPersistencePort, times(1)).obtenerListaPorNombre(listaExistente.getNombre());

        verify(iListaReproduccionPersistencePort, never()).crearListaReproduccion(any(ListaReproduccionDTO.class));
    }

    @Test
    @DisplayName("Debería crear una lista de reproducción exitosamente si no existe")
    void crearListaReproduccion_exitosa() {

        ListaReproduccionDTO nuevaLista = new ListaReproduccionDTO();
        nuevaLista.setNombre("Mi Nueva Lista");

        when(iListaReproduccionPersistencePort.obtenerListaPorNombre(nuevaLista.getNombre()))
                .thenReturn(new ListaReproduccionDTO());
        when(iListaReproduccionPersistencePort.crearListaReproduccion(nuevaLista))
                .thenReturn(nuevaLista);

        ListaReproduccionDTO resultado = listaReproduccionAdapter.crearListaReproduccion(nuevaLista);

        assertNotNull(resultado);
        assertEquals("Mi Nueva Lista", resultado.getNombre());
        verify(iListaReproduccionPersistencePort, times(1)).obtenerListaPorNombre(nuevaLista.getNombre());
        verify(iListaReproduccionPersistencePort, times(1)).crearListaReproduccion(nuevaLista);
    }

    @Test
    @DisplayName("Debería lanzar excepción si el nombre de la lista es nulo o vacío")
    void crearListaReproduccion_nombreVacio_lanzaExcepcion() {

        ListaReproduccionDTO listaSinNombre = new ListaReproduccionDTO();
        listaSinNombre.setNombre(null);


        CancionesException exception = assertThrows(CancionesException.class, () ->
                listaReproduccionAdapter.crearListaReproduccion(listaSinNombre)
        );

        assertEquals(ExceptionResponse.NOMBRE_VACIO.getMensaje(), exception.getMessage());
        verifyNoInteractions(iListaReproduccionPersistencePort);
    }

    @Test
    @DisplayName("Debería lanzar excepción si la lista de reproducción ya existe")
    void crearListaReproduccion_listaExistente_lanzaExcepcion() {

        ListaReproduccionDTO listaExistente = new ListaReproduccionDTO();
        listaExistente.setNombre("Lista Duplicada");

        ListaReproduccionDTO listaEncontrada = new ListaReproduccionDTO();
        listaEncontrada.setNombre("Lista Duplicada");
        when(iListaReproduccionPersistencePort.obtenerListaPorNombre(listaExistente.getNombre()))
                .thenReturn(listaEncontrada);

        CancionesException exception = assertThrows(CancionesException.class, () ->
                listaReproduccionAdapter.crearListaReproduccion(listaExistente)
        );

        assertEquals(ExceptionResponse.LISTA_EXISTENTE.getMensaje(), exception.getMessage());
        verify(iListaReproduccionPersistencePort, times(1)).obtenerListaPorNombre(listaExistente.getNombre());
        verify(iListaReproduccionPersistencePort, never()).crearListaReproduccion(any(ListaReproduccionDTO.class));
    }

    @Test
    @DisplayName("Debería retornar todas las listas de reproducción o una lista vacía")
    void obtenerTodas_retornaListas() {

        ListaReproduccionDTO lista1 = new ListaReproduccionDTO();
        lista1.setNombre("Rock");
        ListaReproduccionDTO lista2 = new ListaReproduccionDTO();
        lista2.setNombre("Pop");
        List<ListaReproduccionDTO> listasMock = Arrays.asList(lista1, lista2);

        when(iListaReproduccionPersistencePort.obtenerTodas()).thenReturn(listasMock);

        List<ListaReproduccionDTO> resultado = listaReproduccionAdapter.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(lista1));
        verify(iListaReproduccionPersistencePort, times(1)).obtenerTodas();

        when(iListaReproduccionPersistencePort.obtenerTodas()).thenReturn(Collections.emptyList());

        resultado = listaReproduccionAdapter.obtenerTodas();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(iListaReproduccionPersistencePort, times(2)).obtenerTodas();
    }


    @Test
    @DisplayName("Debería retornar una lista de reproducción por nombre si existe")
    void obtenerListaPorNombre_listaEncontrada_retornaLista() {

        String nombreBuscado = "Lista de Verano";
        ListaReproduccionDTO listaEncontradaMock = new ListaReproduccionDTO();
        listaEncontradaMock.setNombre(nombreBuscado);

        when(iListaReproduccionPersistencePort.obtenerListaPorNombre(nombreBuscado))
                .thenReturn(listaEncontradaMock);

        ListaReproduccionDTO resultado = listaReproduccionAdapter.obtenerListaPorNombre(nombreBuscado);

        assertNotNull(resultado);
        assertEquals(nombreBuscado, resultado.getNombre());
        verify(iListaReproduccionPersistencePort, times(1)).obtenerListaPorNombre(nombreBuscado);
    }

    @Test
    @DisplayName("Debería lanzar excepción si la lista por nombre no es encontrada")
    void obtenerListaPorNombre_listaNoEncontrada_lanzaExcepcion() {

        String nombreNoExistente = "Lista Desconocida";

        when(iListaReproduccionPersistencePort.obtenerListaPorNombre(nombreNoExistente))
                .thenReturn(new ListaReproduccionDTO());

        ConsultasException exception = assertThrows(ConsultasException.class, () ->
                listaReproduccionAdapter.obtenerListaPorNombre(nombreNoExistente)
        );

        assertEquals(ExceptionResponse.LISTA_NO_ENCONTRADA.getMensaje(), exception.getMessage());
        verify(iListaReproduccionPersistencePort, times(1)).obtenerListaPorNombre(nombreNoExistente);
    }

    @Test
    @DisplayName("Debería eliminar una lista de reproducción exitosamente por nombre")
    void eliminarListaPorNombre_exitosa() {

        String nombreParaEliminar = "Lista Eliminable";

        doNothing().when(iListaReproduccionPersistencePort).eliminarListaPorNombre(nombreParaEliminar);

        listaReproduccionAdapter.eliminarListaPorNombre(nombreParaEliminar);

        verify(iListaReproduccionPersistencePort, times(1)).eliminarListaPorNombre(nombreParaEliminar);
    }

    @Test
    @DisplayName("Debería propagar cualquier excepción de persistencia al eliminar")
    void eliminarListaPorNombre_fallaPersistencia_lanzaExcepcion() {

        String nombreParaEliminar = "Lista con Fallo";

        doThrow(new RuntimeException("Error simulado de base de datos"))
                .when(iListaReproduccionPersistencePort).eliminarListaPorNombre(nombreParaEliminar);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                listaReproduccionAdapter.eliminarListaPorNombre(nombreParaEliminar)
        );

        assertEquals("Error simulado de base de datos", exception.getMessage());
        verify(iListaReproduccionPersistencePort, times(1)).eliminarListaPorNombre(nombreParaEliminar);
    }
}