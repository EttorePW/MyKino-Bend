#!/bin/bash

# MongoDB Atlas Connection String  
MONGO_URI="mongodb+srv://ettorepw:Diosesfiel1%2B@bibliotecadejunior.y2xvybm.mongodb.net/MyKinoDB"

echo "🧹 Cleaning and recreating MongoDB data for MyKinoDB..."
echo "======================================================"

# Function to import and validate a collection
import_collection() {
    local collection=$1
    local file=$2
    
    echo "📦 Importing $collection collection from $file..."
    if ! mongoimport --uri "$MONGO_URI" --collection "$collection" --file "$file" --jsonArray --drop; then
        echo "❌ Failed to import $collection"
        exit 1
    fi
    
    # Validate import with count
    count=$(mongosh "$MONGO_URI" --eval "db.${collection}.countDocuments()" --quiet 2>/dev/null | tail -n1)
    echo "✅ Successfully imported $collection ($count documents)"
    echo ""
}

# Import all collections in dependency order
import_collection "cinemas" "dataset-cinemas.json" 
import_collection "halls" "dataset-halls.json"
import_collection "movies" "dataset-movies.json"
import_collection "users" "dataset-users.json"
import_collection "seats" "dataset-seats-fixed.json"
import_collection "customers" "dataset-customers-fixed.json"
import_collection "bills" "dataset-bills-fixed.json"

echo "🎉 All datasets imported successfully!"
echo "======================================"
echo ""
echo "📊 MyKinoDB is now ready with:"
echo "- Cinemas with halls and schedules"
echo "- Movies with embedded hall information" 
echo "- Customers with seat reservations"
echo "- Seats properly linked to customers"
echo "- Bills with customer references"
echo "- System users for authentication"
echo ""
echo "🚀 Ready to start Spring Boot application!"
