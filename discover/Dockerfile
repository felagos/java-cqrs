FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app

# Copy Gradle wrapper and build files first for better caching
COPY gradlew gradlew.bat ./
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./
COPY discover/build.gradle discover/

# Download dependencies (this layer will be cached unless dependencies change)
RUN ./gradlew :discover:dependencies --no-daemon

# Copy source code
COPY discover/src/ discover/src/

# Build the application
RUN ./gradlew :discover:build -x test --no-daemon

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=builder /app/discover/build/libs/*.jar discover.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "discover.jar"]
