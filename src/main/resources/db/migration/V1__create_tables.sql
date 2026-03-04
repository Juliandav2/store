-- Customers table
CREATE TABLE customers (
    id          VARCHAR(255) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    type        VARCHAR(50)  NOT NULL,
    PRIMARY KEY (id)
);

-- Products table
CREATE TABLE products (
    id          VARCHAR(255) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    price       NUMERIC(19, 2) NOT NULL,
    PRIMARY KEY (id)
);

-- Orders table
CREATE TABLE orders (
    id          VARCHAR(255) NOT NULL,
    state       VARCHAR(50)  NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Order items table
CREATE TABLE order_items (
    id          BIGSERIAL    NOT NULL,
    order_id    VARCHAR(255) NOT NULL,
    product_id  VARCHAR(255) NOT NULL,
    amount      INT          NOT NULL,
    unit_price  NUMERIC(19, 2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id)   REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);