# Chat API

API de chat com autenticação **JWT**, desenvolvida para permitir
cadastro de usuários, autenticação e troca de mensagens entre usuários.

------------------------------------------------------------------------

# Tecnologias

-   Java
-   Spring Boot
-   JWT (JSON Web Token)
-   REST API
-   RMI (comunicação com servidor remoto de chat)

------------------------------------------------------------------------

# Autenticação

A API utiliza **JWT** para proteger os endpoints privados.

Após realizar login, o cliente recebe um **token JWT**, que deve ser
enviado no header:

Authorization: Bearer SEU_TOKEN_AQUI

Endpoints públicos: - `/api/register` - `/api/login`

Endpoints protegidos: - `/api/me` - `/api/users` - `/api/messages` -
`/api/messages/{otherEmail}`

------------------------------------------------------------------------

# Endpoints

## 1. Cadastro de usuário

### Endpoint

POST /api/register

### Descrição

Cadastra um novo usuário no sistema.

### Request Body

``` json
{
  "name": "Ana Silva",
  "email": "ana@email.com",
  "password": "123456"
}
```

### DTO esperado

RegisterRequest

### Resposta de sucesso

``` json
{
  "message": "Usuário cadastrado com sucesso!"
}
```

### Possíveis respostas

-   201 Created
-   400 Bad Request
-   409 Conflict

------------------------------------------------------------------------

# 2. Login

### Endpoint

POST /api/login

### Descrição

Autentica o usuário e retorna um token JWT.

### Request Body

``` json
{
  "email": "ana@email.com",
  "password": "123456"
}
```

### DTO esperado

LoginRequest

### Resposta de sucesso

``` json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "ana@email.com",
  "name": "Ana Silva"
}
```

### DTO de resposta

LoginResponse

### Possíveis respostas

-   200 OK
-   401 Unauthorized

------------------------------------------------------------------------

# 3. Dados do usuário autenticado

### Endpoint

GET /api/me

### Descrição

Retorna os dados do usuário autenticado com base no token JWT.

### Headers

Authorization: Bearer SEU_TOKEN_AQUI

### Resposta de sucesso

``` json
{
  "name": "Ana Silva",
  "email": "ana@email.com"
}
```

### DTO de resposta

SessionUserResponse

### Possíveis respostas

-   200 OK
-   401 Unauthorized

------------------------------------------------------------------------

# 4. Listar usuários

### Endpoint

GET /api/users

### Descrição

Retorna a lista de usuários cadastrados no sistema.

### Headers

Authorization: Bearer SEU_TOKEN_AQUI

### Resposta de sucesso

``` json
[
  {
    "name": "Ana Silva",
    "email": "ana@email.com"
  },
  {
    "name": "Carlos Souza",
    "email": "carlos@email.com"
  }
]
```

### DTOs relacionados

-   UserSummaryDto
-   UserSummaryResponse

### Possíveis respostas

-   200 OK
-   401 Unauthorized

------------------------------------------------------------------------

# 5. Enviar mensagem

### Endpoint

POST /api/messages

### Descrição

Envia uma mensagem do usuário autenticado para outro usuário.

### Headers

Authorization: Bearer SEU_TOKEN_AQUI

### Request Body

``` json
{
  "receiverEmail": "ana@email.com",
  "message": "Olá, tudo bem?"
}
```

### DTO esperado

MessageRequest

### Resposta de sucesso

``` json
{
  "message": "Mensagem enviada com sucesso!"
}
```

### DTO de resposta

ApiMessageResponse

### Possíveis respostas

-   200 OK
-   400 Bad Request
-   401 Unauthorized

------------------------------------------------------------------------

# 6. Buscar conversa com outro usuário

### Endpoint

GET /api/messages/{otherEmail}

### Descrição

Retorna o histórico de conversa entre o usuário autenticado e o usuário
informado.

### Exemplo

GET /api/messages/ana@email.com

### Headers

Authorization: Bearer SEU_TOKEN_AQUI

### Resposta de sucesso

``` json
[
  {
    "id": 1,
    "senderEmail": "junior@email.com",
    "receiverEmail": "ana@email.com",
    "message": "Oi, Ana!",
    "sentAt": "2026-04-08T15:30:00"
  },
  {
    "id": 2,
    "senderEmail": "ana@email.com",
    "receiverEmail": "junior@email.com",
    "message": "Oi! Tudo certo.",
    "sentAt": "2026-04-08T15:31:00"
  }
]
```

### DTO de resposta

ChatMessageResponse

### Possíveis respostas

-   200 OK
-   401 Unauthorized
-   404 Not Found

------------------------------------------------------------------------

# Fluxo de uso da API

1.  Registrar usuário\
    POST /api/register

2.  Fazer login\
    POST /api/login

3.  Copiar o token JWT retornado

4.  Enviar mensagem autenticado\
    POST /api/messages

5.  Buscar conversa\
    GET /api/messages/{otherEmail}

------------------------------------------------------------------------

# Exemplos usando cURL

## Cadastro

    curl -X POST http://localhost:8080/api/register   -H "Content-Type: application/json"   -d '{
        "name": "Ana Silva",
        "email": "ana@email.com",
        "password": "123456"
      }'

## Login

    curl -X POST http://localhost:8080/api/login   -H "Content-Type: application/json"   -d '{
        "email": "ana@email.com",
        "password": "123456"
      }'

## Listar usuários

    curl http://localhost:8080/api/users   -H "Authorization: Bearer SEU_TOKEN_AQUI"

## Enviar mensagem

    curl -X POST http://localhost:8080/api/messages   -H "Content-Type: application/json"   -H "Authorization: Bearer SEU_TOKEN_AQUI"   -d '{
        "receiverEmail": "ana@email.com",
        "message": "Olá, tudo bem?"
      }'

## Buscar conversa

    curl http://localhost:8080/api/messages/ana@email.com   -H "Authorization: Bearer SEU_TOKEN_AQUI"

------------------------------------------------------------------------

# Segurança

A API utiliza **JWT** para autenticação.

Somente os endpoints de **login** e **cadastro** são públicos.\
Os demais endpoints exigem token válido.

------------------------------------------------------------------------

# Tratamento de erros

A aplicação possui um tratamento global de exceções através de:

GlobalExceptionHandler

Isso permite retornar respostas padronizadas para erros como:

-   usuário não encontrado
-   credenciais inválidas
-   falha de comunicação com servidor RMI
-   requisições inválidas

------------------------------------------------------------------------

# Observações

Esta API faz parte de um sistema de chat distribuído utilizando **RMI +
REST**.
