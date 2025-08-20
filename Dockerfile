# Use OpenJDK 21 as base image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Copy source code
COPY src ./src

# Make mvnw executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Set active profile to production
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
CMD ["java", "-jar", "target/Kino-0.0.1-SNAPSHOT.jar"]
