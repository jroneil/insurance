# Insurance Application with Spring Boot

This is a Spring Boot application that demonstrates the use of the **Command Pattern** and **Strategy Pattern** in an insurance system. The application allows users to:
- Generate insurance quotes.
- Create and update insurance policies.
- Calculate premiums using different strategies (e.g., Health, Car, Home).

The application also includes **unit tests** and **controller tests** to ensure the functionality works as expected.

---

## Table of Contents

1. [Features](#features)
2. [Technologies Used](#technologies-used)
3. [Project Structure](#project-structure)
4. [Setup and Installation](#setup-and-installation)
5. [Running the Application](#running-the-application)
6. [API Endpoints](#api-endpoints)
7. [Testing](#testing)
8. [Database Configuration](#database-configuration)
9. [Contributing](#contributing)
10. [License](#license)

---

## Features

- **Command Pattern**: Encapsulates policy creation and updates as commands.
- **Strategy Pattern**: Dynamically calculates premiums for different types of insurance policies (Health, Car, Home).
- **REST API**: Exposes endpoints for generating quotes, creating policies, and updating policies.
- **Unit Tests**: Includes tests for services, strategies, and controllers.
- **H2 Database**: Uses an in-memory H2 database for development and testing.

---

## Technologies Used

- **Spring Boot**: Framework for building the application.
- **H2 Database**: In-memory database for development and testing.
- **JPA**: Java Persistence API for database operations.
- **Mockito**: For mocking dependencies in unit tests.
- **JUnit**: For writing and running tests.
- **Maven**: Build and dependency management.

---

## Project Structure

```
src/main/java/com/oneil/insurance/
    ├── command/              # Command Pattern implementation
    |── config/               # Configuration
    |── filter/               # filters
    ├── strategy/             # Strategy Pattern implementation
    ├── repository/           # Database repositories
    ├── model/                # Entity classes (Policy, Quote)
    ├── service/              # Business logic services
    ├── controller/           # REST API controllers
    ├── exception/            # Custom exceptions
    └── InsuranceApplication.java # Main application class

src/test/java/com/oneil/insurance/
    ├── service/              # Unit tests for services
    ├── strategy/             # Unit tests for strategies
    ├── controller/           # Controller tests
```

---

## Setup and Installation

1. **Prerequisites**:
   - Java 17 or higher.
   - Maven 3.x or higher.
   - An IDE (e.g., IntelliJ IDEA, Eclipse).

2. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-repo/insurance-application.git
   cd insurance-application
   ```

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

---

## Running the Application

1. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

2. **Access the Application**:
   - The application will start at `http://localhost:8080`.

3. **Access the H2 Database Console**:
   - Open your browser and go to `http://localhost:8080/h2-console`.
   - Use the following credentials:
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Username: `sa`
     - Password: `password`

---
4. **Access Swagger UI**
Run your application and access the Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```
## API Endpoints

### Quotes
- **Generate a Quote**:
  ```http
  POST /policies/quotes/generate?policyType=health&policyHolder=John+Doe&baseAmount=1000
  ```

- **Get a Quote by ID**:
  ```http
  GET /policies/quotes/{id}
  ```

### Policies
- **Create a Policy**:
  ```http
  POST /policies/create?baseAmount=1000
  ```
  **Request Body**:
  ```json
  {
    "policyType": "health",
    "policyHolder": "John Doe",
    "startDate": "2023-10-01",
    "endDate": "2024-10-01"
  }
  ```

- **Update a Policy**:
  ```http
  PUT /policies/update?baseAmount=1200
  ```
  **Request Body**:
  ```json
  {
    "id": 1,
    "policyType": "health",
    "policyHolder": "John Doe",
    "startDate": "2023-10-01",
    "endDate": "2024-10-01"
  }
  ```

---

## Testing

### Running Unit Tests
1. Run all tests using Maven:
   ```bash
   mvn test
   ```

2. Run specific test classes or methods from your IDE.

### Test Coverage
- The application includes unit tests for:
  - **Services**: `PolicyService`, `QuoteService`.
  - **Strategies**: `PremiumContext`.
  - **Controllers**: `PolicyController`.

---

## Database Configuration

The application uses an in-memory **H2 database** for development and testing. The database configuration is defined in `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

---

## Contributing

Contributions are welcome! If you'd like to contribute, please follow these steps:
1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Commit your changes and push to the branch.
4. Submit a pull request.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Acknowledgments

- This project was created as a demonstration of the **Command** and **Strategy** patterns in a Spring Boot application.
- Special thanks to the Spring Boot and Mockito communities for their excellent documentation and tools.

---

Enjoy using the Insurance Application! If you have any questions or feedback, feel free to open an issue or contact the maintainers.