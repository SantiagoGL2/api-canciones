package com.quipux.apicanciones.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CancionesRequest {
    private String titulo;
    private String artista;
    private String album;
    private String anno;
    private String genero;
}
