package com.quipux.apicanciones.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListaReproduccionResponse {
    private String nombre;
    private String descripcion;
    private List<CancionResponse> canciones;
}
