package com.quipux.apicanciones.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancionesDTO {
    private String titulo;
    private String artista;
    private String album;
    private String anno;
    private String genero;
}
