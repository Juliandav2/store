CREATE TABLE order_history (
    id          BIG SERIAL PRIMARY KEY,
    order_id    VARCHAR(36) NOT NULL REFERENCES orders(id),
    state       VARCHAR(58) NOT NULL,
    changed_at  TIMESTAMP   NOT NULL DEFAULT NOW()

);