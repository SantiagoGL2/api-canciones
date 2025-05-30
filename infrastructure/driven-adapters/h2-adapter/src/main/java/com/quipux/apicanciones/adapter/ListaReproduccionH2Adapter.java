package com.quipux.apicanciones.adapter;

import com.quipux.apicanciones.dtos.ListaReproduccionDTO;
import com.quipux.apicanciones.entity.CancionEntity;
import com.quipux.apicanciones.entity.ListaReproduccionEntity;
import com.quipux.apicanciones.ports.IListaReproduccionPersistencePort;
import com.quipux.apicanciones.repository.IListaReproduccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.quipux.apicanciones.utils.ModelMap.modelMapper;

@Service
public class ListaReproduccionH2Adapter implements IListaReproduccionPersistencePort {

    private final IListaReproduccionRepository iListaReproduccionRepository;

    public ListaReproduccionH2Adapter(IListaReproduccionRepository iListaReproduccionRepository) {
        this.iListaReproduccionRepository = iListaReproduccionRepository;
    }

    @Override
    public List<ListaReproduccionDTO> obtenerTodas() {
        return iListaReproduccionRepository.findAll()
                .stream()
                .map(listaReproduccionEntity -> modelMapper.map(listaReproduccionEntity, ListaReproduccionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ListaReproduccionDTO crearListaReproduccion(ListaReproduccionDTO listaReproduccion) {
        ListaReproduccionEntity listaEntity = modelMapper.map(listaReproduccion, ListaReproduccionEntity.class);
        if (!listaEntity.getCanciones().isEmpty()) {
            for (CancionEntity cancion : listaEntity.getCanciones()) {
                cancion.setLista(listaEntity);
            }
        }
        ListaReproduccionEntity listaGuardada = iListaReproduccionRepository.save(listaEntity);
        return modelMapper.map(listaGuardada, ListaReproduccionDTO.class);
    }

    @Override
    public ListaReproduccionDTO obtenerListaPorNombre(String nombre) {
        ListaReproduccionEntity entity = iListaReproduccionRepository.findByNombre(nombre)
                .orElse(new ListaReproduccionEntity());
        return modelMapper.map(entity, ListaReproduccionDTO.class);
    }

    @Override
    public void eliminarListaPorNombre(String nombreLista) {
        ListaReproduccionEntity entity = this.iListaReproduccionRepository.findByNombre(nombreLista)
                .orElseThrow(() -> new NoSuchElementException("Lista no encontrada: " + nombreLista));
        iListaReproduccionRepository.delete(entity);
    }
}
