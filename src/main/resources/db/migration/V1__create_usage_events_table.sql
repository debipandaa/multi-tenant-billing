-- V1__create_usage_events_table.sql

CREATE TABLE usage_events (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL,
    user_id UUID,
    metric VARCHAR(255) NOT NULL,
    amount BIGINT NOT NULL,
    event_time TIMESTAMPTZ NOT NULL,
    ingested_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    dedupe_key VARCHAR(255) UNIQUE NOT NULL
);

-- Create an index on tenant_id and time for faster lookups
CREATE INDEX idx_usage_events_tenant_time ON usage_events (tenant_id, event_time);