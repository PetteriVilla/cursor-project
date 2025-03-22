# Todo Application

## Prerequisites
- Java 17
- Maven
- Node.js (v20.11.0)
- npm (10.2.4)

## Running the Application

### Development Mode
1. Start the backend:
```bash
mvn spring-boot:run
```
The application will be available at `http://localhost:8080`

### Production Build
1. Build the entire project:
```bash
mvn clean install
```
2. Run the packaged application:
```bash
java -jar target/todo-app-1.0-SNAPSHOT.jar
```

### Frontend Development
1. Navigate to frontend directory:
```bash
cd frontend
npm start
```
The React development server will run on `http://localhost:3000`

## Features
- Create, Read, Update, and Delete Todos
- Responsive React Frontend
- Spring Boot Backend
- H2 In-memory Database

## Technologies
- Backend: Spring Boot, Spring Data JPA
- Frontend: React, React Bootstrap
- Build Tool: Maven
- Database: H2

## API Endpoints
- `GET /api/todos`: List all todos
- `GET /api/todos/{id}`: Get a specific todo
- `POST /api/todos`: Create a new todo
- `PUT /api/todos/{id}`: Update an existing todo
- `DELETE /api/todos/{id}`: Delete a todo

## Database Console
Access H2 database console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:tododb`
- Username: `sa`
- Password: `password`

## Testing
Run tests using:
```
mvn test
``` 