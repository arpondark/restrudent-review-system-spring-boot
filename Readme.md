# Restaurant Review Application (Restrudent)

A Spring Boot application for managing restaurant reviews with Elasticsearch integration, OAuth2 security, and file storage capabilities.

## Features

- **Restaurant Management**: Create, read, update, and delete restaurants
- **Photo Upload**: Upload and retrieve restaurant photos
- **Elasticsearch Integration**: Full-text search capabilities for restaurants
- **OAuth2 Security**: Secure endpoints with Keycloak integration
- **RESTful API**: Well-structured REST API with proper error handling

## Technology Stack

- **Java 25**
- **Spring Boot 4.0.3**
- **Spring Security with OAuth2 Resource Server**
- **Spring Data Elasticsearch**
- **Lombok**
- **MapStruct** for object mapping
- **Elasticsearch 8.12.0**
- **Keycloak 23.0** for authentication

## Prerequisites

- Java 21 or higher
- Maven 3.9+
- Docker and Docker Compose (for running Elasticsearch and Keycloak)

## Getting Started

### 1. Start Infrastructure Services

Run Elasticsearch, Kibana, and Keycloak using Docker Compose:

```bash
docker-compose up -d
```

This will start:
- **Elasticsearch** on `http://localhost:9200`
- **Kibana** on `http://localhost:5601`
- **Keycloak** on `http://localhost:9090`

### 2. Configure Keycloak

1. Access Keycloak admin console at `http://localhost:9090`
2. Login with credentials: `admin/admin`
3. Create a realm named `retrurant-review`
4. Create a client for your application
5. Configure users and roles as needed

### 3. Build the Application

```bash
./mvnw clean install
```

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Restaurant Endpoints

- `POST /api/restaurants` - Create a new restaurant
- `GET /api/restaurants/{id}` - Get restaurant by ID
- `GET /api/restaurants` - Get all restaurants (paginated)
- `PUT /api/restaurants/{id}` - Update a restaurant
- `DELETE /api/restaurants/{id}` - Delete a restaurant
- `GET /api/restaurants/search?query={query}` - Search restaurants

### Photo Endpoints

- `POST /api/photos` - Upload a photo
- `GET /api/photos/{id}` - Download/view a photo

## Configuration

## Project Structure

### Backend Structure

```
src/main/java/com/shazan/restrudent/
├── config/              Configuration classes
│   ├── ElasticsearchConfig.java
│   ├── OpenApiConfig.java
│   └── SecurityConfig.java
├── controller/          REST controllers
│   ├── GlobalExceptionHandler.java
│   ├── PhotoController.java
│   └── RestrurantController.java
├── domain/
│   ├── dto/            Data Transfer Objects
│   └── entity/         Elasticsearch entities
├── expections/         Custom exceptions
├── mapers/             MapStruct mappers
├── repo/               Elasticsearch repositories
└── services/           Service layer
    └── impl/           Service implementations
```

### Frontend Structure

```
frontend/
├── app/                Next.js App Router
│   ├── layout.tsx      Root layout
│   ├── page.tsx        Home page
│   └── globals.css     Global styles
├── components/         Reusable React components
├── lib/                Utilities and API client
│   └── api.ts         API service layer
├── types/              TypeScript definitions
│   └── index.ts       Shared types
├── public/             Static assets
└── package.json        Dependencies
```

## API Usage Examples

### Create a Restaurant

```bash
curl -X POST http://localhost:8080/api/restaurants \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "name": "Italian Paradise",
    "cuisineType": "Italian",
    "contactInformation": "+1234567890",
    "latitude": 40.7128,
    "longitude": -74.0060,
    "address": {
      "streetNumber": "123",
      "streetName": "Main St",
      "city": "New York",
      "state": "NY",
      "postalCode": "10001",
      "country": "USA"
    }
  }'
```

### Upload a Photo

```bash
curl -X POST http://localhost:8080/api/photos \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -F "file=@/path/to/photo.jpg"
```

### Search Restaurants

```bash
curl -X GET "http://localhost:8080/api/restaurants?page=0&size=20" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Security

All endpoints are secured with OAuth2 JWT authentication except for:
- Swagger UI: /swagger-ui.html
- API Documentation: /api-docs

To access protected endpoints:

1. Obtain a JWT token from Keycloak
2. Include the token in the Authorization header: Authorization: Bearer YOUR_JWT_TOKEN

## Development

### Running Backend Tests

```bash
./mvnw test
```

### Building for Production

Backend:
```bash
./mvnw clean package -DskipTests
java -jar target/restrudent-0.0.1-SNAPSHOT.jar
```

Frontend:
```bash
cd frontend
npm run build
npm start
```

### Code Generation

MapStruct generates mapper implementations during compilation. To regenerate:

```bash
./mvnw clean compile
```

## Error Handling

The application includes global exception handling with proper HTTP status codes:

- 200 OK - Successful operation
- 201 Created - Resource created successfully
- 204 No Content - Resource deleted successfully
- 400 Bad Request - Validation errors or invalid input
- 401 Unauthorized - Authentication required or invalid token
- 404 Not Found - Resource not found
- 500 Internal Server Error - Server errors

## Environment Variables

### Backend
- SPRING_ELASTICSEARCH_URIS - Elasticsearch connection URI
- SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI - Keycloak issuer URI
- APP_STORAGE_LOCATION - File storage location

### Frontend
- NEXT_PUBLIC_API_BASE_URL - Backend API base URL
- NEXT_PUBLIC_KEYCLOAK_URL - Keycloak URL
- NEXT_PUBLIC_KEYCLOAK_REALM - Keycloak realm name
- NEXT_PUBLIC_KEYCLOAK_CLIENT_ID - Keycloak client ID

## Troubleshooting

### Common Issues

1. Elasticsearch connection refused
   - Ensure Elasticsearch is running: docker ps
   - Check Elasticsearch logs: docker logs elasticsearch

2. Keycloak authentication issues
   - Verify Keycloak realm and client configuration
   - Check token expiration

3. File upload issues
   - Check uploads directory permissions
   - Verify multipart configuration in application.properties

4. Frontend cannot connect to backend
   - Ensure backend is running on port 8080
   - Check NEXT_PUBLIC_API_BASE_URL in .env.local
   - Verify CORS is configured in backend

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the Apache License 2.0.

## Support

For questions or support, please open an issue in the repository or contact the development team.
