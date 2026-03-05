To test the API and verify the data directly in the H2 database, 
we can use the H2 Console (usually at http://localhost:8080/h2-console).

Here are the SQL queries categorized by the assignment tasks. 
Note that because we used @Enumerated(EnumType.STRING), the status column will contain the actual text 
(e.g., 'ACTIVE') rather than numbers.

1. Basic CRUD Queries
   Use these to see if the Controller and Service are correctly persisting data.

View all products:

SQL

`SELECT * FROM PRODUCT;
Find a specific product by ID:`

SQL

`SELECT * FROM PRODUCT WHERE id = 1;
Check total stock value (Price * Quantity):`

SQL

`SELECT name, price, quantity, (price * quantity) AS total_value
FROM PRODUCT;`

2. Testing the Business Logic (Task 2)
   These queries help verify the constraints we implemented in the Java layer.

Check for duplicate SKUs (Uniqueness test):

SQL

`SELECT sku, COUNT(*)
FROM PRODUCT
GROUP BY sku
HAVING COUNT(*) > 1;`

Find products with invalid SKU formats:
(This finds any SKU that doesn't follow the SKU-XXXXXXXX pattern you set in your service layer logic).

SQL

`SELECT * FROM PRODUCT
WHERE sku NOT REGEXP '^SKU-[A-Z0-9]{8}$';`

3. Testing Derived & JPQL Equivalents
   These SQL queries mirror the logic in the Derived Queries and JPQL we added to your repository.

Filter by Status (Equivalent to findByStatus):

SQL

`SELECT * FROM PRODUCT WHERE status = 'ACTIVE';`

Low Stock Alert (Equivalent to findByQuantityLessThan):

SQL

`SELECT name, quantity FROM PRODUCT WHERE quantity < 10;`

Price Range Filter (Equivalent to your JPQL query):

SQL

`SELECT * FROM PRODUCT WHERE price BETWEEN 50.00 AND 200.00;`

4. Admin/Cleanup Queries
   Delete all products (Reset for testing):

SQL

`DELETE FROM PRODUCT;`

Reset the ID Auto-Increment counter:

SQL

`ALTER TABLE PRODUCT ALTER COLUMN id RESTART WITH 1;`

Quick Troubleshooting Tips:

Table Not Found: 
If `SELECT * FROM PRODUCT` fails, check the application.properties. 
Ensure the database name in the JDBC URL (e.g., jdbc:h2:mem:productinventorydb) matches what we type into
the H2 login screen.

Case Sensitivity: 

By default, H2 is case-insensitive for table names, but EnumType.STRING values in the database are case-sensitive
(e.g., 'active' is not the same as 'ACTIVE').


