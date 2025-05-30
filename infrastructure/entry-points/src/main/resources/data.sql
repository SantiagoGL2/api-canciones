CREATE TABLE usuario (
                         id BIGINT PRIMARY KEY,
                         username VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL
);

INSERT INTO usuario (id, username, password)
VALUES (1, 'usuario', '$2a$10$jlUb.yxcOfpZXJxMCGmYyul3vFEHjfNMPstgf/YRadamRk0nqX1Y6');

CREATE TABLE cancion (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         titulo VARCHAR(255) NOT NULL,
                         artista VARCHAR(255),
                         album VARCHAR(255),
                         anno INT,
                         genero VARCHAR(100)
);

CREATE TABLE lista_reproduccion (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    nombre VARCHAR(255) NOT NULL UNIQUE,
                                    descripcion VARCHAR(500)
);

CREATE TABLE lista_reproduccion_canciones (
                                              lista_reproduccion_id BIGINT NOT NULL,
                                              canciones_id BIGINT NOT NULL,
                                              PRIMARY KEY (lista_reproduccion_id, canciones_id),
                                              FOREIGN KEY (lista_reproduccion_id) REFERENCES lista_reproduccion(id) ON DELETE CASCADE,
                                              FOREIGN KEY (canciones_id) REFERENCES cancion(id) ON DELETE CASCADE
);