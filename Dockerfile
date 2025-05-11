FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/cliente-persona-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} cliente-persona.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cliente-persona.jar"]