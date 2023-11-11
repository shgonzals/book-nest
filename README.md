# Book Nest

[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-latest-brightgreen)](https://www.mongodb.com/)
[![Swagger](https://img.shields.io/badge/Swagger-2.2.0-orange)](https://springdoc.org/)

## Overview

Book Nest is an application that allows users to track the books they've read. It uses MongoDB to store book details and includes security features with JWT authentication. The project is built with Java 17 and Spring Boot 3.1.5.

## Table of Contents

- [Dependencies](#dependencies)
- [Build and Run](#build-and-run)
- [MongoDB Configuration](#mongodb-configuration)
- [Swagger API Documentation](#swagger-api-documentation)
- [Data](#data)
- [Additional Configuration](#additional-configuration)
- [Contributing](#contributing)
- [License](#license)

## Dependencies

The project uses Maven for dependency management. Here are some key dependencies:

- **Spring Boot Starter Data MongoDB:** For MongoDB integration.
- **Spring Boot Starter Security:** For security features.
- **Spring Boot Starter Web:** For building web applications.
- **Lombok:** A library to reduce boilerplate code.
- **JUnit5, Mockito and Spring Security Test:** For testing.
- **JWT (JSON Web Token):** For authentication.
- **Springdoc OpenAPI Starter Web MVC UI:** For Swagger API documentation.

## Build and Run

Make sure you have Java 17 and Maven installed on your machine.

```bash
# Clone the repository
git clone https://github.com/yourusername/book-nest.git

# Navigate to the project directory
cd book-nest

# Build the project
mvn clean install

# Run the application
java -jar target/book-nest-0.0.1-SNAPSHOT.jar --add-opens=java.base/java.time=ALL-UNNAMED
```

The application will be accessible at http://localhost:8080.

## MongoDB Configuration
The project uses MongoDB as its database. You can configure the MongoDB connection in the docker-compose.yaml file. The default credentials are:
- Username: rootuser
- Password: rootpass

To start MongoDB and Mongo Express, run:
```bash
docker-compose up
```
Mongo Express will be accessible at http://localhost:8081.

## Swagger API Documentation
The API documentation is generated using Swagger. After starting the application, you can access the Swagger UI at:
```bash
http://localhost:8080/swagger-ui/index.html
```

## Data
In the "resources" folder, you can find examples of data to import into MongoDB.

## Additional Configuration
If you're using an IDE, make sure to add the following VM option to avoid any issues with the latest Java versions:
```bash
--add-opens=java.base/java.time=ALL-UNNAMED
```

## Contributing
If you want to contribute to the project, feel free to fork the repository and submit a pull request.

## License
This project is licensed under the MIT License. Feel free to use, modify, and distribute the code.
