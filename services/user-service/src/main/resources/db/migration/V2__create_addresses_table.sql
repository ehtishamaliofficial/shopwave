CREATE TABLE addresses (
                           id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           user_id     UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                           label       VARCHAR(50) NOT NULL DEFAULT 'HOME',
                           street      VARCHAR(255) NOT NULL,
                           city        VARCHAR(100) NOT NULL,
                           country     VARCHAR(100) NOT NULL,
                           postal_code VARCHAR(20),
                           is_default  BOOLEAN NOT NULL DEFAULT FALSE,
                           created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_addresses_user_id ON addresses(user_id);