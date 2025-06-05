FROM openjdk:17-jdk-slim
LABEL authors="Victus"

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]