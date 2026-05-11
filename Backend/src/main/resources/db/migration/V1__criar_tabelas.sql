-- 1. TIPOS ENUM
CREATE TYPE tipo_usuario_enum    AS ENUM ('ADMIN', 'CLIENTE');
CREATE TYPE status_orcamento_enum AS ENUM ('PENDENTE', 'APROVADO', 'REPROVADO', 'FINALIZADO', 'CANCELADO');
CREATE TYPE nota_enum             AS ENUM ('1', '2', '3', '4', '5');

-- 2. MATERIAL
CREATE TABLE material (
    id_material SERIAL PRIMARY KEY,
    nome_material VARCHAR(100) NOT NULL
);

-- 3. COR
CREATE TABLE cor (
    id_cor SERIAL PRIMARY KEY,
    nome_cor VARCHAR(50) NOT NULL
);

-- 4. CATEGORIA
CREATE TABLE categoria (
    id_categoria SERIAL PRIMARY KEY,
    nome_categoria VARCHAR(100) NOT NULL,
    descricao VARCHAR(255)
);


-- 5. PERFIL-
CREATE TABLE perfil (
    id_perfil SERIAL PRIMARY KEY,
    cidade VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(150) NOT NULL UNIQUE
);

-- 6. USUARIO
CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    id_perfil INT NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT NOW(),
    tipo_usuario tipo_usuario_enum NOT NULL,

    CONSTRAINT fk_usuario_perfil
        FOREIGN KEY (id_perfil) REFERENCES perfil(id_perfil)
);

-- 7. PRODUTO
CREATE TABLE produto (
    id_produto SERIAL PRIMARY KEY,
    id_categoria INT NOT NULL,
    nome_produto VARCHAR(150) NOT NULL,
    descricao VARCHAR(500),
    data_cadastro DATE DEFAULT CURRENT_DATE,
    ativo BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_produto_categoria
        FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

-- 8. PROD_MATERIAL
CREATE TABLE prod_material (
    id_produto INT NOT NULL,
    id_material INT NOT NULL,

    PRIMARY KEY (id_produto, id_material),

    CONSTRAINT fk_prodmat_produto
        FOREIGN KEY (id_produto) REFERENCES produto(id_produto),
    CONSTRAINT fk_prodmat_material
        FOREIGN KEY (id_material) REFERENCES material(id_material)
);

-- 9. PROD_COR
CREATE TABLE prod_cor (
    id_produto INT NOT NULL,
    id_cor INT NOT NULL,

    PRIMARY KEY (id_produto, id_cor),

    CONSTRAINT fk_prodcor_produto
        FOREIGN KEY (id_produto) REFERENCES produto(id_produto),
    CONSTRAINT fk_prodcor_cor
        FOREIGN KEY (id_cor) REFERENCES cor(id_cor)
);

-- 10. IMAGEM_PROD
CREATE TABLE imagem_prod (
    id_imagem SERIAL PRIMARY KEY,
    id_produto INT NOT NULL,
    url_imagem VARCHAR(500) NOT NULL,
    descricao_alt VARCHAR(255),

    CONSTRAINT fk_imagem_produto
        FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

-- 11. SOLICIT_PROD
CREATE TABLE solicit_prod (
    id_solicit_prod SERIAL PRIMARY KEY,
    id_produto INT NOT NULL,
    quantidade INT NOT NULL CHECK (quantidade > 0),
    data_solicitacao TIMESTAMP DEFAULT NOW(),

    CONSTRAINT fk_solicitprod_produto
        FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

-- 12. SOLICIT_ORCAMENTO
CREATE TABLE solicit_orcamento (
    id_orcamento SERIAL PRIMARY KEY,
    id_solicit_prod INT NOT NULL,
    id_usuario INT NOT NULL,
    data_solicitacao TIMESTAMP DEFAULT NOW(),
    status status_orcamento_enum NOT NULL DEFAULT 'PENDENTE',
    observacoes VARCHAR(500),
    medidas VARCHAR(255),
    imagem VARCHAR(500),

    CONSTRAINT fk_orcamento_solicitprod
        FOREIGN KEY (id_solicit_prod) REFERENCES solicit_prod(id_solicit_prod),
    CONSTRAINT fk_orcamento_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- 13. AVALIACAO
CREATE TABLE avaliacao (
    id_avaliacao SERIAL PRIMARY KEY,
    id_orcamento   INT       NOT NULL,
    id_usuario     INT       NOT NULL,
    nota           nota_enum NOT NULL,
    comentario     VARCHAR(500),
    data_avaliacao DATE      DEFAULT CURRENT_DATE,
    moderado       BOOLEAN   DEFAULT FALSE,

    CONSTRAINT fk_avaliacao_orcamento
        FOREIGN KEY (id_orcamento) REFERENCES solicit_orcamento(id_orcamento),
    CONSTRAINT fk_avaliacao_usuario
        FOREIGN KEY (id_usuario)   REFERENCES usuario(id_usuario)
);