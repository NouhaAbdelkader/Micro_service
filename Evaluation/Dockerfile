# Dockerfile pour le microservice Spring Boot
FROM openjdk:17-jdk-slim
# Copier le fichier JAR de l'application dans l'image
COPY target/Evaluation-0.0.1-SNAPSHOT.jar /app/Evaluation-0.0.1-SNAPSHOT.jar.jar
WORKDIR /app
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "Evaluation-0.0.1-SNAPSHOT.jar"]
