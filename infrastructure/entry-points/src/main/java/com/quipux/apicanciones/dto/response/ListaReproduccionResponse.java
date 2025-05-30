package com.quipux.apicanciones.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaReproduccionResponse {
    private String nombre;
    private String descripcion;
    private List<CancionResponse> canciones;
}
