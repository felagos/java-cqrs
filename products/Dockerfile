FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app
COPY . .
RUN ./gradlew :products:build -x test

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=builder /app/products/build/libs/*.jar products.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "products.jar"]
