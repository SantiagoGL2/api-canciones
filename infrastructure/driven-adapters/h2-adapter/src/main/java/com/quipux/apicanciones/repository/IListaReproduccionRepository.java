package com.quipux.apicanciones.repository;

import com.quipux.apicanciones.entity.ListaReproduccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IListaReproduccionRepository extends JpaRepository<ListaReproduccionEntity, Long> {
    Optional<ListaReproduccionEntity> findByNombre(String nombre);
}
