CREATE TABLE restaurante_usuario_responsavel
(
    restaurante_id BIGINT NOT NULL,
    usuario_id     BIGINT NOT NULL,
    PRIMARY KEY (restaurante_id, usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

ALTER TABLE restaurante_usuario_responsavel
    ADD CONSTRAINT fk_restaurante_usuario_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);

ALTER TABLE restaurante_usuario_responsavel
    ADD CONSTRAINT fk_restaurante_usuario_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id);