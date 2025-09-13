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


CREATE TABLE orders (
    order_id varchar(36) NOT NULL PRIMARY KEY,
    product_id varchar(36) NOT NULL,
    user_id varchar(36) NOT NULL,
    quantity int NOT NULL,
    address_id varchar(36) NOT NULL,
    order_status varchar(50) NOT NULL,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id)
);


/* TRUNCATE TABLE IF EXISTS products; */

INSERT INTO products (id,price,quantity,title) VALUES
	 ('cda771ec-1080-416c-bad1-5bf49fe72df6',1100000.00,100,'iphone 16'),
	 ('85a6a910-2e49-4314-9662-407a718d9586',1100000.00,100,'iphone 15'),
	 ('60877f7e-9ee6-4dba-866d-50a72713f62b',1200000.00,100,'iphone 14');

INSERT INTO products_lookup (id,title) VALUES
     ('cda771ec-1080-416c-bad1-5bf49fe72df6','iphone 16'),
     ('85a6a910-2e49-4314-9662-407a718d9586','iphone 15'),
     ('60877f7e-9ee6-4dba-866d-50a72713f62b','iphone 14');

INSERT INTO orders (order_id, product_id, user_id, quantity, address_id, order_status) VALUES
    ('a1b2c3d4-e5f6-7890-abcd-1234567890ab', 'cda771ec-1080-416c-bad1-5bf49fe72df6', 'user-001', 1, 'addr-001', 'CREATED'),
    ('b2c3d4e5-f6a1-8901-bcda-2345678901bc', '85a6a910-2e49-4314-9662-407a718d9586', 'user-002', 2, 'addr-002', 'APPROVED');