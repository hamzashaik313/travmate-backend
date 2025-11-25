# Use Java 21 runtime (Render supports Java 21)
FROM openjdk:21-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the compiled jar from target folder
COPY target/*.jar app.jar

# Expose the backend port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
