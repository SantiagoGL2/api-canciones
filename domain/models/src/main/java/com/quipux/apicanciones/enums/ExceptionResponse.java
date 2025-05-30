package com.quipux.apicanciones.enums;

import lombok.Getter;

@Getter
public enum ExceptionResponse {
    LISTA_NO_ENCONTRADA("La lista no existe."),
    LISTA_EXISTENTE("La lista ya existe."),
    NOMBRE_VACIO("El nombre no puede estar vacío.");

    private final String mensaje;

    ExceptionResponse(String mensaje) {
        this.mensaje = mensaje;
    }
}
