# Java CQRS Multi-Project

This is a multi-project Gradle build containing multiple Spring Boot microservices.

## Project Structure

```
java-cqrs/
├── discover/          # Netflix Eureka Discovery Service
├── products/          # CQRS Products Service
├── build.gradle       # Root build configuration
├── settings.gradle    # Multi-project settings
├── gradlew           # Gradle wrapper (Unix)
├── gradlew.bat       # Gradle wrapper (Windows)
└── docker-compose.yml # Docker services (Axon Server)
```

## Sub-Projects

### Discover
- **Purpose**: Netflix Eureka Discovery Service
- **Port**: Default Spring Boot port (8080)
- **Technology**: Spring Cloud Netflix Eureka Server

### Products
- **Purpose**: CQRS-based Products Service
- **Port**: Default Spring Boot port (8080)
- **Technology**: Spring Boot Web, Lombok

## Building and Running

### Build all projects
```bash
./gradlew build
```

### Build specific project
```bash
./gradlew :discover:build
./gradlew :products:build
```

### Run specific project
```bash
./gradlew :discover:bootRun
./gradlew :products:bootRun
```

### Run tests
```bash
# All projects
./gradlew test

# Specific project
./gradlew :discover:test
./gradlew :products:test
```

## Docker Support

Start Axon Server (required for CQRS):
```bash
docker-compose up -d
```

## Development

Each sub-project is a standard Spring Boot application that can be:
- Imported independently into IDEs
- Built and tested independently
- Run as standalone applications

The root project provides common configuration and dependency management for all sub-projects.
