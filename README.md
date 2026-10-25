# Restaurant API

A Spring Boot REST API for managing and searching restaurants. It uses Elasticsearch for restaurant data and search, Keycloak-issued JWTs to protect write operations, and local filesystem storage for photos.

## Architecture

This project is a **layered monolith**: it runs as one Spring Boot application and organizes code into controllers, services, repositories, and domain models. It is not strict Clean Architecture because domain entities and repositories depend directly on Spring Data Elasticsearch.

- **Controllers** expose REST endpoints and handle HTTP requests.
- **Services** implement restaurant, geolocation, photo, and storage operations.
- **Repositories** provide access to Elasticsearch documents.
- **Domain** contains Elasticsearch entities, request models, and API DTOs.
- **Mappers** convert between API DTOs and internal request/entity models.

## Technologies

- Java 21
- Spring Boot 4
- Spring Web MVC, Validation, and Security
- Spring Data Elasticsearch
- Elasticsearch and Kibana
- Keycloak (JWT issuer)
- Maven

## Requirements

- Java 21
- Docker and Docker Compose

## Run locally

Start the supporting services:

```bash
docker compose up -d
```

This starts Elasticsearch at `http://localhost:9200`, Kibana at `http://localhost:5601`, and Keycloak at `http://localhost:9090`.

The application expects a Keycloak realm named `restaurant-review` at:

```text
http://localhost:9090/realms/restaurant-review
```

The Compose configuration starts Keycloak but does not import or create this realm. Create and configure it in Keycloak before using authenticated endpoints.

Run the API:

```bash
./mvnw spring-boot:run
```

The API listens on the default Spring Boot port, `8080`.

Build and run tests:

```bash
./mvnw clean package
./mvnw test
```

## API

Base paths: `/api/restaurants` and `/api/photos`.

| Method | Path | Description | Access |
| --- | --- | --- | --- |
| `GET` | `/api/restaurants` | Search restaurants; supports `q`, `minRating`, `latitude`, `longitude`, `radius`, `page`, and `size` | Public |
| `GET` | `/api/restaurants/{id}` | Get a restaurant | Public |
| `POST` | `/api/restaurants` | Create a restaurant | JWT required |
| `PUT` | `/api/restaurants/{id}` | Update a restaurant | JWT required |
| `DELETE` | `/api/restaurants/{id}` | Delete a restaurant | JWT required |
| `GET` | `/api/photos/{id}` | Retrieve a photo | Public |
| `POST` | `/api/photos` | Upload a photo as multipart form data using the `file` field | JWT required |

Restaurant search defaults to page `1` and page size `20`. Photo uploads are stored in the local `uploads` directory by default.

Authenticated requests must include a Keycloak access token:

```http
Authorization: Bearer <access-token>
```

## Configuration

Default settings are in `src/main/resources/application.properties`:

- Elasticsearch: `http://localhost:9200`
- JWT issuer: `http://localhost:9090/realms/restaurant-review`
- Photo storage directory: `uploads` (the storage service default)

Spring Boot environment variables can override application properties, for example `SPRING_ELASTICSEARCH_URIS` and `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI`.

## Project structure

```text
src/
├── main/
│   ├── java/com/devtiro/restaurant/
│   │   ├── config/          # Security configuration
│   │   ├── controllers/     # REST endpoints and error handling
│   │   ├── domain/          # Entities, DTOs, and request models
│   │   ├── mappers/         # DTO/entity mappings
│   │   ├── respositories/   # Elasticsearch repository
│   │   └── services/        # Service interfaces and implementations
│   └── resources/
│       └── application.properties
└── test/
    └── java/                # Application and data-loader tests
```
