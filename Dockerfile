# Step 1: Build stage - compile and package the Spring Boot app
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven wrapper and pom.xml first (for caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (this step is cached by Docker)
RUN ./mvnw dependency:go-offline -B

# Copy source code and build the JAR
COPY src src
RUN ./mvnw clean package -DskipTests

# Step 2: Run stage - lightweight image
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
