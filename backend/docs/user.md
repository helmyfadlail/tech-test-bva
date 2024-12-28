# User API Spec

## Register User

Endpoint : POST /api/auth/register

Request Body :

```json
{
  "username": "helmy_fadlail",
  "email": "helmy_fadlail@gmail.com",
  "password": "rahasia"
}
```

Response Body (Success) :

```json
{
  "data": "OK"
}
```

Response Body (Failed, 400) :

```json
{
  "errors": "Username must not blank"
}
```

## Login User

Endpoint : POST /api/auth/login

Request Body :

```json
{
  "username": "helmy_fadlail",
  "email": "helmy_fadlail@gmail.com",
  "password": "rahasia"
}
```

Response Body (Success) :

```json
{
  "data": {
    "token": "token",
    "expiredAt": 2342342423423 // milliseconds
  }
}
```

Response Body (Failed, 400) :

```json
{
  "errors": "Username or password wrong"
}
```

## Logout User

Endpoint : DELETE /api/auth/logout

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data": "OK"
}
```

Response Body (Failed, 401) :

```json
{
  "errors": "Unauthorized"
}
```
