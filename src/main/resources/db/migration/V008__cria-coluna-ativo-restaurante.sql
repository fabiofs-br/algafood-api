ALTER TABLE restaurante ADD ativo tinyint(1) NOT NULL;
UPDATE restaurante SET ativo = TRUE;