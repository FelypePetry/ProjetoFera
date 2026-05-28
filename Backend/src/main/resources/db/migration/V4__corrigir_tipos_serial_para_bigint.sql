-- V4__corrigir_tipos_serial_para_bigint.sql

ALTER TABLE categoria       ALTER COLUMN id_categoria   TYPE BIGINT;
ALTER TABLE cor             ALTER COLUMN id_cor         TYPE BIGINT;
ALTER TABLE imagem_prod     ALTER COLUMN id_produto     TYPE BIGINT;
ALTER TABLE imagem_prod     ALTER COLUMN id_imagem      TYPE BIGINT;
ALTER TABLE material        ALTER COLUMN id_material    TYPE BIGINT;
ALTER TABLE prod_cor        ALTER COLUMN id_cor         TYPE BIGINT;
ALTER TABLE prod_cor        ALTER COLUMN id_produto     TYPE BIGINT;
ALTER TABLE prod_material   ALTER COLUMN id_material    TYPE BIGINT;
ALTER TABLE prod_material   ALTER COLUMN id_produto     TYPE BIGINT;
ALTER TABLE solicit_orcamento ALTER COLUMN id_orcamento     TYPE BIGINT;
ALTER TABLE solicit_orcamento ALTER COLUMN id_solicit_prod  TYPE BIGINT;
ALTER TABLE solicit_prod    ALTER COLUMN id_solicit_prod TYPE BIGINT;
ALTER TABLE solicit_prod    ALTER COLUMN id_produto      TYPE BIGINT;