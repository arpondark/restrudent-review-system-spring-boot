# Quick Start Guide

This guide will help you get the Restaurant Review Application up and running quickly.

## Prerequisites Check

Before starting, ensure you have:

- [ ] Java 21 or higher installed
- [ ] Maven 3.9+ installed
- [ ] Node.js 18+ and npm installed
- [ ] Docker and Docker Compose installed

## Quick Setup Steps

### Step 1: Start Infrastructure Services

```bash
docker-compose up -d
```

Wait for services to be ready (about 30 seconds). Verify services are running:

```bash
docker ps
```

You should see Elasticsearch, Kibana, and Keycloak running.

### Step 2: Configure Keycloak

1. Open http://localhost:9090 in your browser
2. Login with: admin/admin
3. Click "Create Realm"
4. Enter realm name: retrurant-review
5. Click "Create"
6. Go to "Clients" and create a new client:
   - Client ID: restrudent-client
   - Client Protocol: openid-connect
   - Access Type: public
   - Valid Redirect URIs: http://localhost:3000/*
7. Save the configuration

### Step 3: Build and Run Backend

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Wait for the application to start. You should see:

```
Started RestrudentApplication in X.XXX seconds
```

Verify backend is running by opening: http://localhost:8080/swagger-ui.html

### Step 4: Setup and Run Frontend

Open a new terminal window:

```bash
cd frontend
npm install
```

Copy the environment file:

```bash
copy .env.local.example .env.local
```

Edit .env.local and update:

```env
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
NEXT_PUBLIC_KEYCLOAK_URL=http://localhost:9090
NEXT_PUBLIC_KEYCLOAK_REALM=retrurant-review
NEXT_PUBLIC_KEYCLOAK_CLIENT_ID=restrudent-client
```

Start the frontend:

```bash
npm run dev
```

### Step 5: Access the Application

Open your browser and navigate to:

- Frontend: http://localhost:3000
- Backend API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- Elasticsearch: http://localhost:9200
- Kibana: http://localhost:5601
- Keycloak: http://localhost:9090

## Quick Test

### 1. Get JWT Token from Keycloak

First, create a user in Keycloak:

1. Go to http://localhost:9090
2. Login as admin
3. Select your realm (retrurant-review)
4. Go to Users > Add User
5. Enter username and email
6. Click Save
7. Go to Credentials tab
8. Set a password

Get token using curl:

```bash
curl -X POST http://localhost:9090/realms/retrurant-review/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=YOUR_USERNAME" \
  -d "password=YOUR_PASSWORD" \
  -d "grant_type=password" \
  -d "client_id=restrudent-client"
```

### 2. Create a Restaurant

```bash
curl -X POST http://localhost:8080/api/restaurants \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "name": "Test Restaurant",
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

### 3. View Restaurants

Open http://localhost:3000 in your browser to see the restaurant list.

## Troubleshooting

### Backend won't start

- Check Java version: `java -version` (should be 21+)
- Ensure Elasticsearch is running: `docker ps`
- Check logs for detailed error messages

### Frontend won't start

- Delete node_modules and reinstall: `rm -rf node_modules && npm install`
- Check Node.js version: `node -v` (should be 18+)
- Verify .env.local is configured correctly

### Cannot authenticate

- Verify Keycloak is running: http://localhost:9090
- Check realm name matches configuration
- Ensure user is created in Keycloak
- Verify client ID matches in both backend and frontend configs

### Elasticsearch connection errors

- Ensure Elasticsearch is running: `docker ps | grep elasticsearch`
- Check Elasticsearch logs: `docker logs elasticsearch`
- Verify port 9200 is not in use by another service

## Next Steps

1. Explore the Swagger UI to understand all available endpoints
2. Create restaurants, upload photos, and add reviews
3. Try the search functionality
4. Customize the frontend to match your branding
5. Add additional features as needed

## Stopping Services

To stop all services:

```bash
# Stop frontend (Ctrl+C in terminal)
# Stop backend (Ctrl+C in terminal)
# Stop Docker services
docker-compose down
```

To stop and remove all data:

```bash
docker-compose down -v
```

## Additional Resources

- Spring Boot Documentation: https://spring.io/projects/spring-boot
- Next.js Documentation: https://nextjs.org/docs
- Elasticsearch Documentation: https://www.elastic.co/guide/
- Keycloak Documentation: https://www.keycloak.org/documentation
- Swagger/OpenAPI: https://swagger.io/docs/

## Support

If you encounter issues:

1. Check the logs for detailed error messages
2. Review the Troubleshooting section
3. Consult the main README.md for detailed documentation
4. Open an issue in the repository with error details
