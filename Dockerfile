#FROM maven:3.8.4-openjdk-17 as builder
#ARG JAR_FILE=target/*.jar
#WORKDIR /app
#COPY ${JAR_FILE} jira-1.0.jar

FROM openjdk:17-buster
ARG JAR_FILE=target/*.jar
COPY resources ./resources
COPY ${JAR_FILE} jira-1.0.jar
ENTRYPOINT ["java","-jar","/jira-1.0.jar"]