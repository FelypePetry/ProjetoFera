## PROJETO FERA METALÚRGICA - GRUPO 1 - FACULDADE DONADUZZI
Théo Gabriel, Felype Petry, Brbara B. Simioni, Rafael Sasso, Pedro, Mateus

# 🪵 Projeto Fera — Sistema de Gestão de Marcenaria

Sistema web completo para uma marcenaria de móveis planejados, com catálogo de produtos, solicitação de orçamentos, área do cliente e painel administrativo.

---

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Como Executar](#como-executar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Rotas e Funcionalidades](#rotas-e-funcionalidades)
- [Banco de Dados](#banco-de-dados)
- [Perfis de Usuário](#perfis-de-usuário)

---

## Visão Geral

O Projeto Fera é uma aplicação Spring Boot com Thymeleaf que oferece:

- **Site público** — catálogo de produtos, página de contato e informações sobre a empresa
- **Área do cliente** — acompanhamento de pedidos, histórico, solicitação de orçamentos e envio de feedback
- **Painel administrativo** — gerenciamento de produtos, orçamentos, usuários, categorias, cores e materiais

---

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Backend | Java 21 · Spring Boot 3.2.5 |
| Persistência | Spring Data JPA · Hibernate |
| Banco de dados | PostgreSQL 16 (produção) · H2 (dev local) |
| Migrações | Flyway 10 |
| Templates | Thymeleaf 3 · thymeleaf-extras-springsecurity6 |
| Segurança | Spring Security 6 |
| Utilitários | Lombok 1.18.36 · Spring Validation |
| Build | Maven 3.9 |
| Contêineres | Docker · Docker Compose |

---

## Arquitetura

```
ProjetoFera/
├── Backend/                        # Aplicação Spring Boot
│   └── src/main/java/.../
│       ├── config/                 # SecurityConfig, WebConfig, DataInitializer
│       ├── controller/
│       │   ├── publico/            # Páginas públicas (site)
│       │   ├── cliente/            # Área logada do cliente
│       │   └── admin/              # Painel administrativo
│       ├── model/                  # Entidades JPA
│       ├── repository/             # Spring Data JPA
│       ├── service/                # Regras de negócio
│       └── security/               # UserDetailsServiceImpl
└── Frontend/                       # HTMLs estáticos originais (referência)
```

A aplicação segue o padrão **MVC com package-by-feature por camada**, onde controllers, services e repositories estão separados por responsabilidade e os templates Thymeleaf espelham as rotas de cada perfil de acesso.

---

## Pré-requisitos

- Java 21+
- Maven 3.9+
- Docker e Docker Compose (para rodar com PostgreSQL)

> **Atenção:** não coloque o projeto dentro de pastas sincronizadas pelo OneDrive ou Google Drive. O Maven gera milhares de arquivos em `target/` e os sincronizadores travam o build.

---

## Como Executar

### Opção 1 — Docker Compose (recomendado)

Sobe o PostgreSQL e a aplicação juntos:

```bash
docker compose up --build
```

A aplicação estará disponível em `http://localhost:8080`.

---

### Opção 2 — Banco local + Maven

**1. Suba o PostgreSQL e crie o banco:**

```sql
CREATE DATABASE "FeraMetalurgica";
```

**2. Configure as credenciais** em `Backend/src/main/resources/application-postgres.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/FeraMetalurgica
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

**3. Execute:**

```bash
cd Backend
mvn spring-boot:run
```

---

### Opção 3 — H2 em memória (sem PostgreSQL)

Edite `application.properties` e troque o perfil ativo:

```properties
spring.profiles.active=h2
```

Depois rode normalmente com `mvn spring-boot:run`. O console H2 estará em `http://localhost:8080/h2-console`.

> Com H2, as migrações Flyway são desativadas e o Hibernate gerencia o schema automaticamente. Os dados são perdidos ao reiniciar.

---

### Build para produção

```bash
cd Backend
mvn clean package -DskipTests
java -jar target/projeto-fera-0.0.1-SNAPSHOT.jar
```

---

## Estrutura do Projeto

### Entidades

```
Pessoa (superclasse)
├── Usuario   — funcionários/admin
└── Cliente   — clientes da marcenaria

Produto       — produtos do catálogo
├── Categoria
├── Cor
├── Material
└── ImagemProd

Orcamento     — pedidos de orçamento dos clientes
└── OrcamentoProduto  — tabela de junção Orçamento ↔ Produto

Feedback      — avaliações dos clientes por orçamento finalizado
```

### Templates Thymeleaf

```
templates/
├── login.html
├── fragments/
│   ├── publico-layout.html    # header/footer do site público
│   ├── cliente-layout.html    # sidebar da área do cliente
│   └── admin-layout.html      # sidebar do painel admin
├── publico/
│   ├── index.html
│   ├── catalogo.html
│   ├── produto.html
│   ├── sobre.html
│   └── contato.html
├── cliente/
│   ├── pedidos.html
│   ├── historico.html
│   ├── orcamento.html
│   ├── feedback.html
│   └── perfil.html
└── admin/
    ├── dashboard.html
    ├── produtos/
    ├── orcamentos/
    └── usuarios/
```

### Migrações Flyway

| Versão | Descrição |
|---|---|
| V1 | Criação das tabelas base |
| V2 | Tabelas de entidades (produtos, orçamentos) |
| V3 | Tabela de junção `orcamento_produto` |
| V4 | Correção de tipos SERIAL → BIGINT |
| V5 | Correção da FK de `avaliacao` para `orcamentos` |

---

## Rotas e Funcionalidades

### Públicas (sem login)

| Rota | Descrição |
|---|---|
| `GET /` | Página inicial |
| `GET /catalogo` | Catálogo de produtos com filtros |
| `GET /produto/{id}` | Detalhe do produto |
| `GET /sobre` | Sobre a empresa |
| `GET /contato` | Página de contato |
| `GET /login` | Tela de login |

### Área do Cliente — `ROLE_CLIENTE`

| Rota | Descrição |
|---|---|
| `GET /minha-conta/pedidos` | Pedidos em andamento |
| `GET /minha-conta/historico` | Histórico de orçamentos |
| `GET /minha-conta/orcamento` | Formulário de solicitação de orçamento |
| `POST /minha-conta/orcamento` | Envio do orçamento |
| `GET /minha-conta/feedback` | Tela de avaliação e feedbacks anteriores |
| `POST /minha-conta/feedback` | Envio de avaliação |
| `GET /minha-conta/perfil` | Dados do perfil |

### Painel Admin — `ROLE_ADMIN` / `ROLE_USER`

| Rota | Descrição |
|---|---|
| `GET /admin/dashboard` | Dashboard com métricas |
| `GET/POST /admin/produtos/**` | CRUD de produtos |
| `GET/POST /admin/orcamentos/**` | Gerenciamento de orçamentos |
| `GET/POST /admin/usuarios/**` | Gerenciamento de usuários |

---

## Banco de Dados

O Flyway executa as migrações automaticamente ao iniciar a aplicação. Não é necessário rodar nenhum script manualmente.

Com o perfil `postgres`, o `ddl-auto` está em `validate` — o Hibernate apenas verifica se as entidades batem com o schema criado pelo Flyway, sem alterar nada.

---

## Perfis de Usuário

| Role | Acesso |
|---|---|
| `ROLE_ADMIN` | Painel administrativo completo |
| `ROLE_USER` | Painel administrativo (operador) |
| `ROLE_CLIENTE` | Área do cliente |

Após o login, o redirecionamento é automático:
- Admin/operador → `/admin/dashboard`
- Cliente → `/minha-conta/pedidos`

O `DataInitializer` cria um usuário admin padrão na primeira execução se nenhum existir.
