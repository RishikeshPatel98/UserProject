# UserProject - Demo Application for Managing Users
This is a simple RESTful API application built using Spring Boot 3+ and Java 17 that allows users to perform CRUD operations (Create, Retrieve, Update, and Delete) on User records. The app uses H2 Database for simplicity and Spring Data JPA for database interactions. This application demonstrates the creation of a basic user management system and API.

# Features
- Create a new user: An API endpoint to create a user with username, email, password, and role.
- Retrieve all users: An API endpoint to retrieve a list of all users.
- Retrieve a user by ID: An API endpoint to retrieve a user by their unique identifier (ID).

# Getting Started
# Prerequisites
  - Java 17 or higher installed on your machine.
  - Spring Boot (3+).
  - Maven (for building the project).

## Local Setup
Clone project
```bash
git clone https://github.com/RishikeshPatel98/UserProject.git
```

Navigate to root directory of Project and the run the command
```bash
mvn clean install
```

***Note - Before Running Maven Install Make Sure to Configure application.properties for local setup***

# Access the OpenAPI Documentation
```bash
http://localhost:8080/swagger-ui/index.html
```

# H2 Database Configuration
- spring.application.name=userProject

- spring.datasource.url=jdbc:h2:mem:testdb
- spring.datasource.driverClassName=org.h2.Driver
- spring.datasource.username=xxxxx
- spring.datasource.password=xxxxx
- spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
- spring.h2.console.enabled=true
- spring.jpa.hibernate.ddl-auto=update

