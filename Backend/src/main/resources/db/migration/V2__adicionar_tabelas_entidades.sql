-- PRODUTOS
CREATE TABLE produtos (
    id             BIGSERIAL       PRIMARY KEY,
    nome           VARCHAR(150)    NOT NULL,
    descricao      VARCHAR(500),
    codigo         VARCHAR(50)     NOT NULL UNIQUE,
    preco          NUMERIC(12, 2)  NOT NULL,
    estoque        INT             NOT NULL,
    unidade_medida VARCHAR(20)     NOT NULL,
    material       VARCHAR(100),
    categoria      VARCHAR(100),
    ativo          BOOLEAN         NOT NULL DEFAULT TRUE,
    criado_em      TIMESTAMP       NOT NULL,
    atualizado_em  TIMESTAMP,
    mais_vendido   BOOLEAN         NOT NULL DEFAULT FALSE,
    destaque       BOOLEAN         NOT NULL DEFAULT FALSE
);

-- ORCAMENTOS
CREATE TABLE orcamentos (
    id            BIGSERIAL      PRIMARY KEY,
    tipo_movel    VARCHAR(100)   NOT NULL,
    medidas       VARCHAR(200),
    observacoes   VARCHAR(1000),
    imagem_path   VARCHAR(300),
    status        VARCHAR(30)    NOT NULL DEFAULT 'PENDENTE',
    usuario_id    BIGINT         NOT NULL,
    criado_em     TIMESTAMP      NOT NULL,
    atualizado_em TIMESTAMP,
    valor         NUMERIC(12, 2),
    progresso     INT            DEFAULT 0,

    CONSTRAINT fk_orcamento_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- FEEDBACKS
CREATE TABLE feedbacks (
    id         BIGSERIAL     PRIMARY KEY,
    mensagem   VARCHAR(1000) NOT NULL,
    usuario_id BIGINT        NOT NULL,
    criado_em  TIMESTAMP     NOT NULL,

    CONSTRAINT fk_feedback_usuario
       FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);