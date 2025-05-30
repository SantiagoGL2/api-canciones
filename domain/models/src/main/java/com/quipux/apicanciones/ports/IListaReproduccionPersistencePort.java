package com.quipux.apicanciones.ports;

import com.quipux.apicanciones.dtos.ListaReproduccionDTO;

import java.util.List;

public interface IListaReproduccionPersistencePort {

    List<ListaReproduccionDTO> obtenerTodas();

    ListaReproduccionDTO crearListaReproduccion(ListaReproduccionDTO listaReproduccion);

    ListaReproduccionDTO obtenerListaPorNombre(String nombre);

    void eliminarListaPorNombre(String nombreLista);
}
