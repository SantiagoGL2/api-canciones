package com.quipux.apicanciones.dtos;

import lombok.*;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaReproduccionDTO {
    private String nombre;
    private String descripcion;
    private List<CancionesDTO> canciones;
}
