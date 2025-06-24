# 📚 API de Alunos – Projeto Individual

Este projeto é composto por duas aplicações interligadas:

1. `carregabanco`: carrega os dados de alunos a partir de um arquivo `.csv` para o banco de dados.
2. `api-alunos`: provê uma API REST para realizar operações CRUD (criar, buscar, editar e remover alunos).

---

## ⚙️ Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL (via Docker)
- Maven
- Jakarta Validation
- Docker + Docker Compose

---

## 💻 Como Executar o Projeto

### 🔧 Pré-requisitos

- Java JDK 17+
- Maven
- Docker e Docker Compose

### 📦 1. Clonar os repositórios

```bash
git clone https://github.com/Peddrinnz/carregabanco.git
git clone https://github.com/Peddrinnz/api-alunos.git
```
### 🐳 2. Rodar o docker no carregabanco e o maven

mvn spring-boot:run

### 🚀 3. Rodar a API

mvn spring-boot:run
docker compose up -d

## 📌 Funcionalidades da API
 GET /alunos – Buscar todos os alunos

 POST /alunos – Inserir novo aluno

 PUT /alunos/{id} – Editar aluno

 DELETE /alunos/{id} – Remover aluno

 GET /alunos/buscar-por-nome?nome=João

 GET /alunos/buscar-por-curso?curso=ADS

 GET /alunos/buscar-por-campus?campus=Corumbá

## O Diagrama DER

Está no docs/diagrama_er.png
