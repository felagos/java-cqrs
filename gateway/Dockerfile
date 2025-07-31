FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app

# Copy Gradle wrapper and build files first for better caching
COPY gradlew gradlew.bat ./
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./
COPY gateway/build.gradle gateway/

# Download dependencies (this layer will be cached unless dependencies change)
RUN ./gradlew :gateway:dependencies --no-daemon

# Copy source code
COPY gateway/src/ gateway/src/

# Build the application
RUN ./gradlew :gateway:build -x test --no-daemon

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=builder /app/gateway/build/libs/*.jar gateway.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gateway.jar"]
