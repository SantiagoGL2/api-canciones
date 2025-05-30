package com.quipux.apicanciones.adapter;

import com.quipux.apicanciones.dtos.CancionesDTO;
import com.quipux.apicanciones.dtos.ListaReproduccionDTO;
import com.quipux.apicanciones.entity.CancionEntity;
import com.quipux.apicanciones.entity.ListaReproduccionEntity;
import com.quipux.apicanciones.repository.IListaReproduccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListaReproduccionH2AdapterTest {

    @Mock
    private IListaReproduccionRepository iListaReproduccionRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ListaReproduccionH2Adapter listaReproduccionH2Adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería obtener todas las listas o una lista vacía")
    void obtenerTodas_retornaListas() {

        ListaReproduccionEntity entity = new ListaReproduccionEntity();
        ListaReproduccionDTO dto = new ListaReproduccionDTO();

        when(iListaReproduccionRepository.findAll()).thenReturn(List.of(entity));
        when(modelMapper.map(any(ListaReproduccionEntity.class), eq(ListaReproduccionDTO.class))).thenReturn(dto);

        List<ListaReproduccionDTO> resultadoConDatos = listaReproduccionH2Adapter.obtenerTodas();

        assertFalse(resultadoConDatos.isEmpty());
        verify(iListaReproduccionRepository).findAll();

        when(iListaReproduccionRepository.findAll()).thenReturn(Collections.emptyList());

        List<ListaReproduccionDTO> resultadoVacio = listaReproduccionH2Adapter.obtenerTodas();
        assertTrue(resultadoVacio.isEmpty());
    }

    @Test
    @DisplayName("Debería crear una lista de reproducción con éxito (con o sin canciones)")
    void crearListaReproduccion_exito() {
        ListaReproduccionDTO inputDto = new ListaReproduccionDTO();
        CancionesDTO cancionesDTO = new CancionesDTO();
        ListaReproduccionEntity entityToSave = new ListaReproduccionEntity();
        CancionEntity cancion = new CancionEntity();
        entityToSave.setCanciones(List.of(cancion));
        ListaReproduccionEntity savedEntity = new ListaReproduccionEntity();
        ListaReproduccionDTO outputDto = new ListaReproduccionDTO();

        when(modelMapper.map(inputDto, ListaReproduccionEntity.class)).thenReturn(entityToSave);
        when(iListaReproduccionRepository.save(any(ListaReproduccionEntity.class))).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, ListaReproduccionDTO.class)).thenReturn(outputDto);

        inputDto.setCanciones(List.of(cancionesDTO));
        ListaReproduccionDTO resultado = listaReproduccionH2Adapter.crearListaReproduccion(inputDto);

        assertNotNull(resultado);

        verify(iListaReproduccionRepository).save(any(ListaReproduccionEntity.class));
    }

    @Test
    @DisplayName("Debería obtener una lista por nombre o un DTO vacío si no existe")
    void obtenerListaPorNombre_retornaListaOVacio() {
        String nombreExistente = "Musica Pop";
        ListaReproduccionEntity entityEncontrada = new ListaReproduccionEntity();
        entityEncontrada.setNombre(nombreExistente);
        ListaReproduccionDTO dtoEncontrado = new ListaReproduccionDTO();
        dtoEncontrado.setNombre(nombreExistente);

        when(iListaReproduccionRepository.findByNombre(nombreExistente)).thenReturn(Optional.of(entityEncontrada));
        when(modelMapper.map(entityEncontrada, ListaReproduccionDTO.class)).thenReturn(dtoEncontrado);

        ListaReproduccionDTO resultadoEncontrado = listaReproduccionH2Adapter.obtenerListaPorNombre(nombreExistente);

        assertNotNull(resultadoEncontrado);
        assertEquals(nombreExistente, resultadoEncontrado.getNombre());
        verify(iListaReproduccionRepository).findByNombre(nombreExistente);

        String nombreNoExistente = "No Hay";
        ListaReproduccionEntity emptyEntity = new ListaReproduccionEntity();
        ListaReproduccionDTO emptyDto = new ListaReproduccionDTO();

        when(iListaReproduccionRepository.findByNombre(nombreNoExistente)).thenReturn(Optional.empty());

        when(modelMapper.map(emptyEntity, ListaReproduccionDTO.class)).thenReturn(emptyDto);

        ListaReproduccionDTO resultadoVacio = listaReproduccionH2Adapter.obtenerListaPorNombre(nombreNoExistente);

        assertNotNull(resultadoVacio);
        assertNull(resultadoVacio.getNombre());
    }

    @Test
    @DisplayName("Debería eliminar una lista por nombre si existe")
    void eliminarListaPorNombre_exito() {

        String nombreParaBorrar = "Borrarme";
        ListaReproduccionEntity entityToDelete = new ListaReproduccionEntity();
        when(iListaReproduccionRepository.findByNombre(nombreParaBorrar)).thenReturn(Optional.of(entityToDelete));
        doNothing().when(iListaReproduccionRepository).delete(entityToDelete);

        listaReproduccionH2Adapter.eliminarListaPorNombre(nombreParaBorrar);

        verify(iListaReproduccionRepository).findByNombre(nombreParaBorrar);
        verify(iListaReproduccionRepository).delete(entityToDelete);
    }

    @Test
    @DisplayName("Debería lanzar excepción si la lista a eliminar no existe")
    void eliminarListaPorNombre_noExiste_lanzaExcepcion() {

        String nombreNoExiste = "Desconocida";
        when(iListaReproduccionRepository.findByNombre(nombreNoExiste)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                listaReproduccionH2Adapter.eliminarListaPorNombre(nombreNoExiste)
        );
        verify(iListaReproduccionRepository).findByNombre(nombreNoExiste);
        verify(iListaReproduccionRepository, never()).delete(any(ListaReproduccionEntity.class));
    }

}