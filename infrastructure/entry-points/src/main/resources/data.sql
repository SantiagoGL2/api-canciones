CREATE TABLE usuario (
                         id BIGINT PRIMARY KEY,
                         username VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL
);

INSERT INTO usuario (id, username, password)
VALUES (1, 'usuario_demo', '$2a$10$8vIghb7bXvph6aEFtnUyKOVq3OAjMZJP6Qw8Do92Ba1g.Wb7DB83K');