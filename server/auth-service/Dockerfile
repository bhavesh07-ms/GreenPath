# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy JAR file from target folder to container
COPY target/auth-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
