package com.quipux.apicanciones.adapter;

import com.quipux.apicanciones.dtos.ListaReproduccionDTO;
import com.quipux.apicanciones.enums.ExceptionResponse;
import com.quipux.apicanciones.exception.CancionesException;
import com.quipux.apicanciones.exception.ConsultasException;
import com.quipux.apicanciones.port.IListaReproduccionPort;
import com.quipux.apicanciones.ports.IListaReproduccionPersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ListaReproduccionAdapter implements IListaReproduccionPort {

    private final IListaReproduccionPersistencePort iListaReproduccionPersistencePort;

    public ListaReproduccionAdapter(IListaReproduccionPersistencePort iListaReproduccionPersistencePort) {
        this.iListaReproduccionPersistencePort = iListaReproduccionPersistencePort;
    }

    @Override
    public List<ListaReproduccionDTO> obtenerTodas() {
        return this.iListaReproduccionPersistencePort.obtenerTodas();
    }

    @Override
    public ListaReproduccionDTO crearListaReproduccion(ListaReproduccionDTO listaReproduccion) {
        if(Objects.isNull(listaReproduccion.getNombre())){
            throw new CancionesException(ExceptionResponse.NOMBRE_VACIO.getMensaje());
        }
        ListaReproduccionDTO listaReproduccionDTO = this.iListaReproduccionPersistencePort.obtenerListaPorNombre(listaReproduccion.getNombre());

        if(Objects.nonNull(listaReproduccionDTO.getNombre())){
            throw new CancionesException(ExceptionResponse.LISTA_EXISTENTE.getMensaje());
        }
        return this.iListaReproduccionPersistencePort.crearListaReproduccion(listaReproduccion);
    }

    @Override
    public ListaReproduccionDTO obtenerListaPorNombre(String nombre) {
        return getListaReproduccionDTO(nombre);
    }

    @Override
    public void eliminarListaPorNombre(String nombreLista) {
        this.iListaReproduccionPersistencePort.eliminarListaPorNombre(nombreLista);
    }

    private ListaReproduccionDTO getListaReproduccionDTO(String nombreLista) {
        ListaReproduccionDTO listaEncontrada = this.iListaReproduccionPersistencePort.obtenerListaPorNombre(nombreLista);
        if(Objects.isNull(listaEncontrada.getNombre())){
            throw new ConsultasException(ExceptionResponse.LISTA_NO_ENCONTRADA.getMensaje());
        }
        return listaEncontrada;
    }


}
