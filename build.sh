#!/bin/bash

echo "Building MyKino Backend..."

# Clean and build the project
./mvnw clean package -DskipTests

echo "Build completed successfully!"

# Check if jar was created
if [ -f "target/Kino-0.0.1-SNAPSHOT.jar" ]; then
    echo "JAR file created successfully: target/Kino-0.0.1-SNAPSHOT.jar"
    ls -la target/Kino-0.0.1-SNAPSHOT.jar
else
    echo "Error: JAR file not found!"
    exit 1
fi
