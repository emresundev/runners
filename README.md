# Runners Application

A sample Java Spring Boot project for managing runners and their activities efficiently.

## Project Overview

This is a Spring Boot application that demonstrates various features and best practices in Spring Boot development. 
The project uses Spring Boot 3.4.2 and Java 21.

## Technologies Used

- **Java 21**
- **Spring Boot 3.4.2**
- **Spring Web** - For building RESTful APIs
- **Spring Data JDBC** - For database operations
- **PostgreSQL** - As the primary database
- **Spring Boot Docker Compose** - For containerization support
- **Spring Boot DevTools** - For development productivity
- **Spring Validation** - For request validation

## Prerequisites

To run this application, you need:

- Java 21 JDK
- Maven
- PostgreSQL
- Docker (optional, for containerized deployment)

## Getting Started

1. Clone the repository:
```bash
git clone [your-repository-url]
```

2. Navigate to the project directory:
```bash
cd runners
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

## Project Structure

The application follows the standard Spring Boot project structure: