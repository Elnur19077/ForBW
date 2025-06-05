FROM openjdk:17-jdk-slim
LABEL authors="Victus"

WORKDIR /app

COPY target/black-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
