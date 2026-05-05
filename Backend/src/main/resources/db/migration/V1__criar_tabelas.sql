CREATE TABLE teste_flyway (
    id        BIGSERIAL PRIMARY KEY,
    mensagem  VARCHAR(100) NOT NULL,
    criado_em TIMESTAMP DEFAULT NOW()
);

INSERT INTO teste_flyway (mensagem)
VALUES ('Flyway funcionando com sucesso!');