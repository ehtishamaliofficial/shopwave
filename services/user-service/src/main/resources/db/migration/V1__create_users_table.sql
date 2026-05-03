CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
                       id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       email       VARCHAR(255) UNIQUE NOT NULL,
                       password    VARCHAR(255) NOT NULL,
                       full_name   VARCHAR(255) NOT NULL,
                       phone       VARCHAR(20),
                       role        VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
                       is_active   BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
                       updated_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users(email);