# Member API Spec

## Get All Members

Endpoint : GET /api/members

Query Param :

-   name : String, member name, using like query, optional
-   position : String, member position, using like query, optional
-   superior : String, member position, using like query, optional
-   page : Integer, start from 0, default 0
-   size : Integer, default 10

Request Header :

-   X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
    "data": [
        {
            "id": "random-string",
            "name": "Helmy Fadlail",
            "position": "Software Developer",
            "superior": "Albab",
            "picture_url": "http://www.imagekit.org/helmy-fadlail.png"
        }
    ]
}
```

Response Body (Failed, 401) :

```json
{
    "errors": "Unauthorized"
}
```

Response Body (Failed, 404) :

```json
{
    "errors": "Members is not found"
}
```

## Create Member

Endpoint : POST /api/members

Request Header :

-   X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
    "name": "Helmy Fadlail",
    "position": "Software Developer",
    "superior": "Albab",
    "picture_url": "http://www.imagekit.org/helmy-fadlail.png"
}
```

Response Body (Success) :

```json
{
    "data": {
        "id": "random-string",
        "name": "Helmy Fadlail",
        "position": "Software Developer",
        "superior": "Albab",
        "picture_url": "http://www.imagekit.org/helmy-fadlail.png"
    }
}
```

Response Body (Failed, 401) :

```json
{
    "errors": "Unauthorized"
}
```

Response Body (Failed, 400) :

```json
{
    "errors": "Position cannot be null"
}
```

## Get One member

Endpoint : GET /api/members/{memberId}

Request Header :

-   X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
    "data": {
        "id": "random-string",
        "name": "Helmy Fadlail",
        "position": "Software Developer",
        "superior": "Albab",
        "picture_url": "http://www.imagekit.org/helmy-fadlail.png"
    }
}
```

Response Body (Failed, 401) :

```json
{
    "errors": "Unauthorized"
}
```

Response Body (Failed, 404) :

```json
{
    "errors": "Member is not found"
}
```
