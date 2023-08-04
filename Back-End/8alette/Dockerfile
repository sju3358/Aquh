FROM openjdk:17-jdk
COPY ../../../application-AWS-KEY.properties application-AWS-KEY.properties
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
