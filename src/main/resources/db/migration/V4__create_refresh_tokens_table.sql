CREATE TABLE refresh_tokens (
    id          BIG SERIAL PRIMARY KEY,
    token       VARCHAR(255) NOT NULL UNIQUE,
    username    VARCHAR(100) NOT NULL,
    expires_at  TIMESTAMP    NOT NULL,
    revoked     BOOLEAN      NOT NULL DEFAULT FALSE
);