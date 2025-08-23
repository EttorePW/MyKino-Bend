#!/bin/bash

# MongoDB Atlas Connection String
MONGO_URI="mongodb+srv://ettorepw:Diosesfiel1%2B@bibliotecadejunior.y2xvybm.mongodb.net/MyKinoDB"

echo "🚀 Starting MongoDB import process for MyKinoDB..."
echo "======================================================"

# Function to import a collection
import_collection() {
    local collection=$1
    local file=$2
    
    echo "📦 Importing $collection collection from $file..."
    mongoimport --uri "$MONGO_URI" --collection "$collection" --file "$file" --jsonArray --drop
    
    if [ $? -eq 0 ]; then
        echo "✅ Successfully imported $collection"
    else
        echo "❌ Failed to import $collection"
        exit 1
    fi
    echo ""
}

# Import all collections
import_collection "cinemas" "dataset-cinemas.json"
import_collection "halls" "dataset-halls.json"  
import_collection "movies" "dataset-movies.json"
import_collection "customers" "dataset-customers.json"
import_collection "seats" "dataset-seats.json"
import_collection "bills" "dataset-bills.json"
import_collection "users" "dataset-users.json"

echo "🎉 All datasets imported successfully!"
echo "======================================"
echo ""
echo "📊 Database Summary:"
echo "- 4 cinemas (Wien, Berlin, München, Hamburg)"
echo "- 10 halls with different movie formats (D2D, DBOX, R3D)"
echo "- 5 popular movies with embedded halls"
echo "- 8 customers with realistic personal data"
echo "- 15 seat reservations across different movies/times"
echo "- 8 bills corresponding to customer reservations"
echo "- 4 system users (admin, manager, staff, test)"
echo ""
echo "🌐 Ready to test the API at: http://localhost:8080"
echo "📚 Swagger UI available at: http://localhost:8080/swagger-ui.html"
echo ""
echo "🧪 Test endpoints:"
echo "- GET http://localhost:8080/api/movie (list all movies with embedded halls)"
echo "- GET http://localhost:8080/api/cinema (list all cinemas)" 
echo "- GET http://localhost:8080/api/customers (list all customers with seat references)"
echo "- GET http://localhost:8080/api/bill (list all bills)"
