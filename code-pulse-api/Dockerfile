# Build Stage
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Runtime Stage
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/code-pulse-api-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8089
CMD ["java", "-Dserver.port=8089", "-jar", "/app/app.jar"]
