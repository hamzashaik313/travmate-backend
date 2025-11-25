# Step 1: Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven files first
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give permission to run mvnw
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the source code
COPY src src

# Build the jar
RUN ./mvnw clean package -DskipTests

# Step 2: Run stage
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
