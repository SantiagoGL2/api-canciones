package com.quipux.apicanciones.service;

import com.quipux.apicanciones.dto.request.ListRequest;
import com.quipux.apicanciones.dto.response.ListaReproduccionResponse;
import com.quipux.apicanciones.dtos.ListaReproduccionDTO;
import com.quipux.apicanciones.port.IListaReproduccionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.quipux.apicanciones.utils.ModelMap.modelMapper;

@Service
public class ListaReproduccionService {

    private final IListaReproduccionPort iListaReproduccionPort;

    public ListaReproduccionService(IListaReproduccionPort iListaReproduccionPort) {
        this.iListaReproduccionPort = iListaReproduccionPort;
    }

    @Transactional
    public List<ListaReproduccionResponse> obtenerTodas() {
        return iListaReproduccionPort.obtenerTodas()
                .stream()
                .map(ListaReproduccionResponse -> modelMapper.map(ListaReproduccionResponse, ListaReproduccionResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ListaReproduccionResponse crearListaReproduccion(ListRequest listRequest){
        ListaReproduccionDTO listaDTO = modelMapper.map(listRequest, ListaReproduccionDTO.class);

        ListaReproduccionDTO listaCreada = iListaReproduccionPort.crearListaReproduccion(listaDTO);

        return modelMapper.map(listaCreada, ListaReproduccionResponse.class);
    }

    @Transactional
    public ListaReproduccionResponse obtenerListaPorNombre(String nombre) {
        ListaReproduccionDTO listaDTO = iListaReproduccionPort.obtenerListaPorNombre(nombre);
        return modelMapper.map(listaDTO, ListaReproduccionResponse.class);
    }

    @Transactional
    public void eliminarListaReproduccion(String nombreLista) {
        iListaReproduccionPort.eliminarListaPorNombre(nombreLista);
    }
}
