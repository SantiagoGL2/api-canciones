CREATE TABLE usuario (
                         id BIGINT PRIMARY KEY,
                         username VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL
);

INSERT INTO usuario (id, username, password)
VALUES (1, 'usuario', '$2a$10$jlUb.yxcOfpZXJxMCGmYyul3vFEHjfNMPstgf/YRadamRk0nqX1Y6');