CREATE TABLE orcamento_produto (
    orcamento_id BIGINT NOT NULL,
    produto_id   BIGINT NOT NULL,
    quantidade   INT    NOT NULL,
    PRIMARY KEY (orcamento_id, produto_id),
    CONSTRAINT fk_orcprod_orcamento FOREIGN KEY (orcamento_id) REFERENCES orcamentos(id),
    CONSTRAINT fk_orcprod_produto   FOREIGN KEY (produto_id)   REFERENCES produtos(id)
);
