#!/bin/bash

# Render build script for MyKino Backend
echo "🚀 Starting MyKino Backend build..."

# Check Java version
echo "☕ Java version:"
java -version

# Make mvnw executable
echo "🔧 Making Maven wrapper executable..."
chmod +x ./mvnw

# Check Maven version
echo "📦 Maven version:"
./mvnw --version

# Clean and build
echo "🏗️  Building application..."
./mvnw clean package -DskipTests -B

# Verify JAR was created
echo "✅ Verifying JAR file..."
if [ -f "target/Kino-0.0.1-SNAPSHOT.jar" ]; then
    echo "✅ JAR file created successfully:"
    ls -la target/Kino-0.0.1-SNAPSHOT.jar
    echo "📊 JAR file size: $(du -h target/Kino-0.0.1-SNAPSHOT.jar | cut -f1)"
else
    echo "❌ ERROR: JAR file not found!"
    echo "📁 Contents of target directory:"
    ls -la target/
    exit 1
fi

echo "🎉 Build completed successfully!"
