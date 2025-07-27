FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app
COPY . .
RUN ./gradlew :discover:build -x test

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=builder /app/discover/build/libs/*.jar discover.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "discover.jar"]
