Projeto de Web Services com Spring Boot e JPA / Hibernate
Este é um projeto de exemplo que implementa um sistema de e-commerce básico utilizando Spring Boot, JPA/Hibernate e o banco de dados H2 para testes. Ele demonstra a criação de uma API RESTful com operações CRUD (Create, Retrieve, Update, Delete) e tratamento de exceções.

Objetivos do Projeto
Criar um projeto Spring Boot em Java.

Implementar um modelo de domínio abrangente com entidades e relacionamentos.

Estruturar o projeto em camadas lógicas: Resource (Controladores REST), Service (Regras de Negócio) e Repository (Acesso a Dados).

Configurar um banco de dados de teste em memória (H2).

Povoar o banco de dados com dados iniciais (seeding) para fácil teste.

Implementar operações CRUD completas para as principais entidades.

Adicionar tratamento de exceções personalizadas para erros comuns de API.

Modelo de Domínio (Entidades)
O projeto segue um modelo de domínio de e-commerce com as seguintes entidades:

User: Usuário do sistema (id, nome, email, telefone, senha).

Category: Categoria de produto (id, nome).

Product: Produto (id, nome, descrição, preço, URL da imagem), com relacionamento ManyToMany com Category.

Order: Pedido (id, momento, status do pedido), com relacionamento ManyToOne com User e OneToMany com OrderItem, e OneToOne com Payment.

OrderItem: Item do Pedido (quantidade, preço no momento da compra), com uma chave composta OrderItemPK que mapeia Order e Product.

Payment: Pagamento (id, momento), com relacionamento OneToOne com Order, usando @MapsId (o ID do Payment é o mesmo do Order associado).

OrderStatus: Enumeração para os possíveis status de um pedido.

Tecnologias Utilizadas
Java 17: Linguagem de programação.

Spring Boot 3.5.0: Framework para construção de aplicações Java.

Spring Data JPA: Abstração para persistência de dados com JPA.

Hibernate: Implementação do JPA.

H2 Database: Banco de dados em memória para desenvolvimento e testes.

Maven: Ferramenta de gerenciamento de projeto e construção.

Postman: Ferramenta para testar endpoints da API REST.

Visual Studio Code: IDE de desenvolvimento.

Como Rodar o Projeto
Pré-requisitos:

Java JDK 17 ou superior.

Maven (instalado ou via Maven Wrapper, que é usado neste projeto).

Git (para clonar o repositório).

Clonar o Repositório (se ainda não o fez):

git clone <URL_DO_SEU_REPOSITORIO_GITHUB>
cd myfirstapp

Configurar o Banco de Dados H2:
O projeto está configurado para usar um banco de dados H2 em memória com o perfil "test". A configuração está em src/main/resources/application.properties.

Iniciar a Aplicação:
Navegue até a pasta raiz do projeto (myfirstapp) no seu terminal e execute:

./mvnw spring-boot:run

A aplicação será iniciada na porta 8081. O console do H2 estará acessível em http://localhost:8081/h2-console.

Endpoints da API
A API expõe os seguintes endpoints RESTful:

1. Usuários (/users)
GET /users: Lista todos os usuários.

GET /users/{id}: Busca um usuário por ID.

POST /users: Insere um novo usuário.

Exemplo de Body: {"name": "Nome Sobrenome", "email": "email@example.com", "phone": "11999999999", "password": "senha"}

PUT /users/{id}: Atualiza um usuário existente.

Exemplo de Body: {"name": "Novo Nome", "email": "novo@example.com", "phone": "11888888888"}

DELETE /users/{id}: Deleta um usuário por ID.

2. Categorias (/categories)
GET /categories: Lista todas as categorias.

GET /categories/{id}: Busca uma categoria por ID.

POST /categories: Insere uma nova categoria.

Exemplo de Body: {"name": "Nova Categoria"}

PUT /categories/{id}: Atualiza uma categoria existente.

Exemplo de Body: {"name": "Categoria Atualizada"}

DELETE /categories/{id}: Deleta uma categoria por ID.

3. Produtos (/products)
GET /products: Lista todos os produtos.

GET /products/{id}: Busca um produto por ID.

POST /products: Insere um novo produto.

Exemplo de Body: {"name": "Novo Produto", "description": "Descrição", "price": 123.45, "imgUrl": "", "categories": [{"id": 1}, {"id": 2}]}

PUT /products/{id}: Atualiza um produto existente.

Exemplo de Body: {"name": "Produto Atualizado", "description": "Nova Descrição", "price": 543.21, "imgUrl": "", "categories": [{"id": 1}]}

DELETE /products/{id}: Deleta um produto por ID.

4. Pedidos (/orders)
GET /orders: Lista todos os pedidos.

GET /orders/{id}: Busca um pedido por ID.

POST /orders: Insere um novo pedido.

Exemplo de Body: {"moment": "2025-01-01T10:00:00Z", "orderStatus": 1, "clientId": 1}

(Note: orderStatus é o código do enum OrderStatus: 1 para WAITING_PAYMENT, 2 para PAID, etc. clientId é o ID do usuário associado.)

PUT /orders/{id}: Atualiza um pedido existente.

Exemplo de Body: {"moment": "2025-01-01T11:00:00Z", "orderStatus": 2}

DELETE /orders/{id}: Deleta um pedido por ID.

5. Pagamentos (/payments)
GET /payments: Lista todos os pagamentos.

GET /payments/{id}: Busca um pagamento por ID.

POST /payments: Insere um novo pagamento.

Exemplo de Body: {"moment": "2025-01-01T10:30:00Z", "order": {"id": 2}}

(Note: O id do pagamento será o mesmo do order.id que você passar. Certifique-se de que o pedido ainda não tem um pagamento associado).

PUT /payments/{id}: Atualiza um pagamento existente.

Exemplo de Body: {"moment": "2025-01-01T11:30:00Z", "order": {"id": 2}}

(Note: Você pode atualizar o moment. O order.id não deve ser alterado em um PUT, pois o ID do Payment é mapeado do ID do Order).

DELETE /payments/{id}: Deleta um pagamento por ID.

Sobre o Autor
Nome: Leticia Gomes Lopes

Curso: Ciência da Computação

Disciplina: Programação Orientada a Objetos - Turma B

Professor: David Nadler Prata

Instituição: Universidade Federal do Tocantins (UFT), Campus Palmas