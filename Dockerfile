FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:17 AS runtime
WORKDIR /app
COPY --from=build /app/target/productService-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "productService-0.0.1-SNAPSHOT.jar"]