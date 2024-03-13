# Cadastrar Contatos

## Descrição
O projeto Cadastrar Contatos é uma aplicação para gerenciar contatos profissionais, fornecendo endpoints para realizar operações CRUD (Criar, Ler, Atualizar e Deletar) de contatos e profissionais. Ele foi desenvolvido em Java com o framework Spring Boot.

## Funcionalidades

### Contatos
- Listar todos os contatos
- Filtrar contatos por termo de busca e campos específicos
- Obter detalhes de um contato por ID
- Cadastrar um novo contato associado a um profissional
- Atualizar os dados de um contato existente
- Deletar um contato

### Profissionais
- Listar todos os profissionais
- Filtrar profissionais por termo de busca e campos específicos
- Obter detalhes de um profissional por ID
- Cadastrar um novo profissional
- Atualizar os dados de um profissional existente
- Deletar um profissional

## Estrutura do Projeto
O projeto está dividido em pacotes de controller, service, model e repository.

- `controller`: Contém os controladores responsáveis por receber as requisições HTTP, chamar os serviços correspondentes e retornar as respostas adequadas.
- `service`: Contém a lógica de negócio da aplicação, onde são realizadas as operações sobre os dados.
- `model`: Define as classes de modelo para os contatos e profissionais.
- `repository`: Interface que estende JpaRepository para acesso aos dados no banco.

## Pré-requisitos
- Java 8 ou superior
- Maven
- Banco de dados (H2, MySQL, PostgreSQL, etc.)

## Configuração do Ambiente
1. Clone este repositório: git clone https://github.com/seu-usuario/seu-repositorio.git


2. Importe o projeto em sua IDE favorita.

3. Certifique-se de ter configurado corretamente o banco de dados no arquivo `application.properties`.

4. Execute o projeto.

Swegger endpoints:
http://localhost:8080/swagger-ui/index.html

## Endpoints de Contato
### `GET /contatos`

Lista de contatos com base nos critérios definidos em Params.

#### Params

- `q` - String: Filtro para buscar contatos que contenham o texto em qualquer um de seus atributos.
- `fields` - List<String> (Opcional): Quando presente, apenas os campos listados em `fields` deverão ser retornados.

#### Response

Retorna uma lista de contatos.

---

### `GET /contatos/:id`

Retorna todos os dados do contato que possui o ID passado na URL.

#### Response

Retorna os dados do contato com o ID especificado.

---

### `POST /contatos`

Insere no banco de dados os dados do contato enviados via body.

#### Body

- Content-type: Json

#### Response

Retorna "Sucesso: contato com id {ID} cadastrado" após a inserção.

---

### `PUT /contatos/:id`

Atualiza os dados do contato que possui o ID passado via URL com os dados enviados no Body.

#### Body

- Content-type: Json

#### Response

Retorna "Sucesso: contato alterado" após a atualização.

---

### `DELETE /contatos/:id`

Exclui o contato que possui o ID passado na URL.

#### Response

Retorna "Sucesso: contato excluído" após a exclusão.


## Endpoints de Profissional
### `GET /profissionais`

Lista de profissionais com base nos critérios definidos em Params.

#### Params

- `q` - String: Filtro para buscar profissionais que contenham o texto em qualquer um de seus atributos.
- `fields` - List<String> (Opcional): Quando presente, apenas os campos listados em `fields` deverão ser retornados.

#### Response

Retorna uma lista de profissionais.

---

### `GET /profissional/:id`

Retorna todos os dados do contato que possui o ID passado na URL.

#### Response

Retorna os dados do contato com o ID especificado.

---

### `POST /profissionais`

Insere no banco de dados os dados do contato enviados via body.

#### Body

- Content-type: Json

#### Response

Retorna "Sucesso: contato com id {ID} cadastrado" após a inserção.

---

### `PUT /profissional/:id`

Atualiza os dados do contato que possui o ID passado via URL com os dados enviados no Body.

#### Body

- Content-type: Json

#### Response

Retorna "Sucesso: contato alterado" após a atualização.

---

### `DELETE /profissional/:id`

Exclui o contato que possui o ID passado na URL.

#### Response

Retorna "Sucesso: contato excluído" após a exclusão.
