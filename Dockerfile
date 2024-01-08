FROM openjdk:21-jdk-slim

WORKDIR /app
COPY ./target/speed-typing-result-service-0.0.1-SNAPSHOT.jar /app

EXPOSE 8070

CMD ["java", "-jar", "speed-typing-result-service-0.0.1-SNAPSHOT.jar"]
