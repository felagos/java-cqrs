FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app

# Copy Gradle wrapper and build files first for better caching
COPY gradlew gradlew.bat ./
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./
COPY products/build.gradle products/

# Fix line endings and make gradlew executable
RUN sed -i 's/\r$//' ./gradlew && chmod +x ./gradlew

# Download dependencies (this layer will be cached unless dependencies change)
RUN ./gradlew :products:dependencies --no-daemon

# Copy source code
COPY products/src/ products/src/

# Build the application
RUN ./gradlew :products:build -x test --no-daemon

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=builder /app/products/build/libs/*.jar products.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "products.jar"]
