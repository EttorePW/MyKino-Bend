#!/bin/bash

# MongoDB Atlas Connection String  
MONGO_URI="mongodb+srv://ettorepw:Diosesfiel1%2B@bibliotecadejunior.y2xvybm.mongodb.net/MyKinoDB"

echo "🧹 Cleaning ALL collections from MyKinoDB..."
echo "============================================="

# Function to drop a collection
drop_collection() {
    local collection=$1
    
    echo "🗑️  Dropping $collection collection..."
    mongosh "$MONGO_URI" --eval "db.${collection}.deleteMany({})" --quiet
    
    if [ $? -eq 0 ]; then
        echo "✅ Successfully cleaned $collection"
    else
        echo "❌ Failed to clean $collection"
    fi
    echo ""
}

# Drop all collections to start fresh
echo "⚠️  WARNING: This will delete ALL data from the database!"
echo "Press Enter to continue or Ctrl+C to cancel..."
read

drop_collection "bills"
drop_collection "customers" 
drop_collection "seats"
drop_collection "movies"
drop_collection "halls"
drop_collection "cinemas"
drop_collection "users"

echo "🎉 Database cleaned successfully!"
echo "================================"
echo ""
echo "✅ All collections are now empty"
echo "🚀 Ready for fresh data import or API usage"
echo ""
echo "💡 Next steps:"
echo "- Use the API to create new customers, bills, etc."
echo "- Or run import-all-datasets.sh to populate with test data"
