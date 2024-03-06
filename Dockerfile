FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} productService-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/productService-0.0.1-SNAPSHOT.jar"]