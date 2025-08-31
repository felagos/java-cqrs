DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id varchar(36) NOT NULL,
    title varchar(255) NOT NULL,
    price decimal(10,2) NOT NULL,
    quantity int NOT NULL,
    PRIMARY KEY (id)
);