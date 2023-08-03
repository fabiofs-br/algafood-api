ALTER TABLE restaurante ADD aberto tinyint(1) NOT NULL;
UPDATE restaurante SET aberto = FALSE;