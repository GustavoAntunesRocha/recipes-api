# Recipes API
Api system to manage food recipes

## Swagger access
- localhost:8080/swagger-ui/index.html

## H2 database console access
- localhost:8080/h2-console
- JDBC Url: jdbc:h2:mem:tools

## Methods
API request standards:

### Base URL: /recipe

| Method | Params | Description |
|---|---|---|
| `GET/{id}` || Return information of a record passing it's ID as a paramether. |
| `GET` | name, page, size | Return information of records containing the name. Pageable results. |
| `POST` || Creates a new record. |
| `PUT` || Updates a record. |
| `DELETE/{id}` || Deletes a record passing it's ID as a paramether. |

### Base URL: /tags

| Method | Params | Description |
|---|---|---|
| `GET` | name, page, size | Return information of records containing the name. Pageable results. |
| `GET/{id}` || Return information of a record passing it's ID as a paramether. |
| `POST` || Creates a new record. |
| `POST/multiple` || Creates multiple records. |
| `PUT` || Updates a record. |
| `DELETE/{id}` || Deletes a record passing it's ID as a paramether. |

### Base URL: /users

| Method | Description |
|---|---|
| `GET` | Return information of all records. |
| `GET/{id}` | Return information of a record passing it's ID as a paramether. |
| `GET/email/{email}` | Return information of a record passing it's email as a paramether. |
| `POST` | Creates a new record. |
| `POST/login` | Authenticates a user returning a token. |
| `PUT/{id}` | Updates a record given it's ID as a paramether. |
| `DELETE/{id}` | Deletes a record passing it's ID as a paramether. |


## Authentication schema

![Diagrama de autenticação (1)](https://user-images.githubusercontent.com/31359489/225329715-0975324d-57c7-4910-93a2-a2d5afeb72ee.png)


### Built with

- Java
- Spring Boot
- Spring Security
