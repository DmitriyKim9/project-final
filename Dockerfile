#FROM maven:3.8.4-openjdk-17 as builder
#ARG JAR_FILE=target/*.jar
#WORKDIR /app
#COPY ${JAR_FILE} jira-1.0.jar

FROM eclipse-temurin:17-jre-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} jira-1.0.jar
ENTRYPOINT ["java", "-jar", "/app/*.jar"]