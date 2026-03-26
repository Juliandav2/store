CREATE TABLE token_blacklist (
    id          BIG SERIAL PRIMARY KEY,
    token       VARCHAR(512) NOT NULL UNIQUE,
    blacklisted_at TIMESTAMP NOT NULL DEFAULT NOW()
);