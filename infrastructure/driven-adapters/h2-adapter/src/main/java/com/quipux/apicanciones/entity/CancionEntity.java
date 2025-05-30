package com.quipux.apicanciones.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cancion")
public class CancionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "artista", nullable = false)
    private String artista;

    @Column(name = "album")
    private String album;

    @Column(name = "anno")
    private String anno;

    @Column(name = "genero")
    private String genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lista_id", nullable = false)
    private ListaReproduccionEntity lista;
}
