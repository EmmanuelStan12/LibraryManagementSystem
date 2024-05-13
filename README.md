# Library Management System API Documentation

This project is a Library Management System API built using Spring Boot. It allows librarians to manage books, patrons, and borrowing records (loans).

## How to Run the Project
There are two ways to run the project, you could use docker or use a more traditional approach. Please use the one more comfortable to you.

### Using Docker

1. Make sure you have Docker installed on your system.
2. Clone the repository:

   ```bash
   git clone https://github.com/EmmanuelStan12/LibraryManagementSystem.git
   cd LibraryManagementSystem
   ```
3. Build the Docker image:
   ```bash
   docker build -t library_management_system .
   ```

4. Run the Docker container:
   ```bash
   docker run -d library_management_system -p <host-port>:8080
   ```

5. The application would be accessible at
   `http://127.0.0.1:<host-port>`

### Using Maven Wrapper

1. Please make sure to use Java 17 in your running environment.
2. Clone the repository:

   ```bash
   git clone https://github.com/EmmanuelStan12/LibraryManagementSystem.git
   cd LibraryManagementSystem
   ```
3. (Optional) You can clean the project:

```bash
   ./mvnw clean
```

4. Run the application using maven wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
5. The application would be accessible at
   `http://127.0.0.1:8080` 

## API Documentation
To access the api documentation, please run the application and check `http://127.0.0.1:<host-port>/swagger-ui/index.html`.
You can test the endpoints using the `library_management_system_tests.http` file in the root directory.

## Major features

### Data Storage

- An H2 database is embedded to persist book, patron, and loan (Borrowing record) details.

### Validation and Error Handling

- Input validation for API requests is implemented, including validation of required fields and data formats.
- Exceptions are handled gracefully, returning appropriate HTTP status codes and error messages.

### Security

- JWT-based authorization can be implemented to protect the API endpoints.

### Aspects

- Logging using Aspect-Oriented Programming (AOP) to log method calls.

### Caching

- Spring's caching mechanisms can be utilized to cache frequently accessed data such as books and patrons, to improve system performance.

### Transaction Management

- Declarative transaction management for operations in spring data jpa.

### Testing

- Both Unit and Integration tests are written to validate the functionality of API endpoints by utilizing JUnit, Mockito and SpringBootTest.
- To execute all test cases run `./mvnw test`


