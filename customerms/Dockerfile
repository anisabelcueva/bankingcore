
# Base image
FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/core.customerms-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} core.customerms-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT [ "java" , "-jar" , "core.customerms-0.0.1-SNAPSHOT.jar"]

