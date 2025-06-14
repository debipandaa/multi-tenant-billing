-- V2__create_plans_and_tenants.sql

CREATE TABLE plans (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    pricing_json JSONB NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    plan_id UUID REFERENCES plans(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Let's add some default plans to get started
INSERT INTO plans (id, name, pricing_json) VALUES
(gen_random_uuid(), 'Free', '{}'),
(gen_random_uuid(), 'Pro', '{}'),
(gen_random_uuid(), 'Enterprise', '{}');