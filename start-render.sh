#!/bin/bash

# Render start script for MyKino Backend
echo "🚀 Starting MyKino Backend..."

# Show environment info
echo "☕ Java version:"
java -version

echo "🌍 Environment variables:"
echo "SPRING_PROFILES_ACTIVE: ${SPRING_PROFILES_ACTIVE:-not set}"
echo "DATABASE_URL (Render): ${DATABASE_URL:-not set}"
echo "JDBC_DATABASE_URL (Our): ${JDBC_DATABASE_URL:-not set}"
echo "DATABASE_USERNAME: ${DATABASE_USERNAME:-not set}"
echo "PORT: ${PORT:-8080}"

# Show available memory
echo "💾 Available memory:"
free -m 2>/dev/null || echo "Memory info not available"

# Verify JAR exists
if [ ! -f "target/Kino-0.0.1-SNAPSHOT.jar" ]; then
    echo "❌ ERROR: JAR file not found!"
    echo "📁 Contents of target directory:"
    ls -la target/ || echo "Target directory not found"
    exit 1
fi

echo "✅ JAR file found, starting application..."

# Start the application with optimized JVM settings for free tier
exec java \
    -Xmx480m \
    -Xms256m \
    -XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=90.0 \
    -Dspring.profiles.active=prod \
    -Dserver.port=${PORT:-8080} \
    -jar target/Kino-0.0.1-SNAPSHOT.jar
