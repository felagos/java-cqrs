DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id varchar(36) NOT NULL,
    title varchar(255) NOT NULL UNIQUE,
    price decimal(10,2) NOT NULL,
    quantity int NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE products_lookup (
    id varchar(36) NOT NULL,
    title varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);


/* TRUNCATE TABLE IF EXISTS products; */

INSERT INTO products (id,price,quantity,title) VALUES
	 ('cda771ec-1080-416c-bad1-5bf49fe72df6',1100000.00,2,'iphone 16'),
	 ('85a6a910-2e49-4314-9662-407a718d9586',1100000.00,2,'iphone 15'),
	 ('60877f7e-9ee6-4dba-866d-50a72713f62b',1200000.00,3,'iphone 14');

INSERT INTO products_lookup (id,title) VALUES
     ('cda771ec-1080-416c-bad1-5bf49fe72df6','iphone 16'),
     ('85a6a910-2e49-4314-9662-407a718d9586','iphone 15'),
     ('60877f7e-9ee6-4dba-866d-50a72713f62b','iphone 14');