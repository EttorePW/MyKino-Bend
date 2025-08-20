#!/bin/bash

echo "Starting build process..."

# Make mvnw executable
chmod +x mvnw

# Clean and package
./mvnw clean package -DskipTests

echo "Build completed successfully!"
