package com.quipux.apicanciones.dtos;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private String username;
    private String password;
}
