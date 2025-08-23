// MongoDB initialization script
// This script creates a user for the products database
// It runs when the MongoDB container starts for the first time

// Switch to the products database
db = db.getSiblingDB('products');

// Create user with readWrite permissions for products database
db.createUser({
  user: 'app',
  pwd: 'secret',
  roles: [
    {
      role: 'readWrite',
      db: 'products'
    }
  ]
});

// Create an index on a common field (optional, for better performance)
// db.products.createIndex({ "id": 1 });

print('MongoDB initialization completed. User "app" created for products database.');
