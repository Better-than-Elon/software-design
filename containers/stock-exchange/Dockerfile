FROM maven:3.8.6-jdk-11-slim AS builder
WORKDIR /app
ADD ./pom.xml pom.xml
ADD ./src src/
RUN mvn clean package -DskipTests

FROM eclipse-temurin:11
WORKDIR /app
COPY --from=builder /app/target/studentsystem-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]