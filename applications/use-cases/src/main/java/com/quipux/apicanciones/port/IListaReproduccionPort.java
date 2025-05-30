package com.quipux.apicanciones.port;


import com.quipux.apicanciones.dtos.ListaReproduccionDTO;

import java.util.List;

public interface IListaReproduccionPort {

    List<ListaReproduccionDTO> obtenerTodas();

    ListaReproduccionDTO crearListaReproduccion(ListaReproduccionDTO listaReproduccion);

    ListaReproduccionDTO obtenerListaPorNombre(String nombre);

    void eliminarListaPorNombre(String nombreLista);

}
