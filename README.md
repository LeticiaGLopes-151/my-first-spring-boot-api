# 🛒 Projeto de Web Services com Spring Boot e JPA / Hibernate

Este é um projeto de exemplo que implementa um **sistema de e-commerce básico** utilizando Spring Boot, JPA/Hibernate e o banco de dados H2. Ele demonstra a criação de uma API RESTful com operações CRUD e tratamento de exceções.

---

## 🎯 Objetivos do Projeto

- Criar um projeto Spring Boot em Java.
- Implementar um modelo de domínio com entidades e relacionamentos.
- Estruturar o projeto em camadas:
  - `Resource`: Controladores REST
  - `Service`: Regras de negócio
  - `Repository`: Acesso a dados
- Configurar um banco de dados de testes em memória (H2).
- Povoar o banco com dados iniciais (seeding).
- Implementar operações CRUD completas.
- Adicionar tratamento de exceções personalizadas.

---

## 🧩 Modelo de Domínio (Entidades)

O projeto segue um modelo de e-commerce com as seguintes entidades:

- **User**: `id`, `nome`, `email`, `telefone`, `senha`
- **Category**: `id`, `nome`
- **Product**: `id`, `nome`, `descrição`, `preço`, `URL da imagem`  
  ↳ *Relacionamento ManyToMany com Category*
- **Order**: `id`, `momento`, `status do pedido`  
  ↳ *Relacionamento ManyToOne com User*  
  ↳ *OneToMany com OrderItem*  
  ↳ *OneToOne com Payment*
- **OrderItem**: `quantidade`, `preço`  
  ↳ *Chave composta (Order + Product)*
- **Payment**: `id`, `momento`  
  ↳ *Relacionamento OneToOne com Order usando `@MapsId`*
- **OrderStatus**: Enum com status do pedido

---

## 🛠 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.0**
- **Spring Data JPA**
- **Hibernate**
- **H2 Database** (em memória)
- **Maven**
- **Postman** (para testar a API)
- **Visual Studio Code**

---

## 🚀 Como Rodar o Projeto

### ✅ Pré-requisitos

- Java JDK 17+
- Maven (ou Maven Wrapper)
- Git

### 📥 Clonar o Repositório

```bash
git clone <URL_DO_SEU_REPOSITORIO_GITHUB>
cd myfirstapp
```

### ⚙️ Configurar o Banco de Dados

O projeto usa H2 em memória com o perfil `test`. A configuração está em:

```bash
src/main/resources/application.properties
```

### ▶️ Rodar a Aplicação

Execute no terminal:

```bash
./mvnw spring-boot:run
```

A aplicação iniciará na porta **8081**.  
Console H2: [http://localhost:8081/h2-console](http://localhost:8081/h2-console)

---

## 📡 Endpoints da API

### 🔹 Usuários (`/users`)

- `GET /users` – Lista usuários  
- `GET /users/{id}` – Detalha um usuário  
- `POST /users` – Cria usuário  
```json
{
  "name": "Nome Sobrenome",
  "email": "email@example.com",
  "phone": "11999999999",
  "password": "senha"
}
```
- `PUT /users/{id}` – Atualiza usuário  
- `DELETE /users/{id}` – Remove usuário

---

### 🔸 Categorias (`/categories`)

- `GET /categories`  
- `GET /categories/{id}`  
- `POST /categories`  
```json
{
  "name": "Nova Categoria"
}
```
- `PUT /categories/{id}`  
- `DELETE /categories/{id}`

---

### 🔸 Produtos (`/products`)

- `GET /products`  
- `GET /products/{id}`  
- `POST /products`  
```json
{
  "name": "Novo Produto",
  "description": "Descrição",
  "price": 123.45,
  "imgUrl": "",
  "categories": [{"id": 1}, {"id": 2}]
}
```
- `PUT /products/{id}`  
- `DELETE /products/{id}`

---

### 📦 Pedidos (`/orders`)

- `GET /orders`  
- `GET /orders/{id}`  
- `POST /orders`  
```json
{
  "moment": "2025-01-01T10:00:00Z",
  "orderStatus": 1,
  "clientId": 1
}
```
- `PUT /orders/{id}`  
- `DELETE /orders/{id}`

---

### 💳 Pagamentos (`/payments`)

- `GET /payments`  
- `GET /payments/{id}`  
- `POST /payments`  
```json
{
  "moment": "2025-01-01T10:30:00Z",
  "order": { "id": 2 }
}
```
- `PUT /payments/{id}`  
- `DELETE /payments/{id}`

---

## 👩‍💻 Sobre o Autor

- **Nome**: Leticia Gomes Lopes  
- **Curso**: Ciência da Computação  
- **Disciplina**: Programação Orientada a Objetos - Turma B  
- **Professor**: David Nadler Prata  
- **Instituição**: Universidade Federal do Tocantins (UFT), Campus Palmas

---

> Projeto criado com fins educacionais para a disciplina de POO.
