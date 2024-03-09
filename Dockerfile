FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package

FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=build /app/target/speed-typing-result-service-0.0.1-SNAPSHOT.jar /app

EXPOSE 8070

CMD ["java", "-jar", "speed-typing-result-service-0.0.1-SNAPSHOT.jar"]
