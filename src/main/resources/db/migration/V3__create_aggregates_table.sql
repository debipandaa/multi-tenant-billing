-- V3__create_aggregates_table.sql

CREATE TABLE aggregates (
    tenant_id UUID NOT NULL,
    metric VARCHAR(255) NOT NULL,
    period_start TIMESTAMPTZ NOT NULL,
    period_end TIMESTAMPTZ NOT NULL,
    usage_count BIGINT NOT NULL,
    -- A composite primary key ensures we only have one entry per tenant/metric/window
    PRIMARY KEY (tenant_id, metric, period_start)
);

-- An index to speed up lookups, especially for dashboards
CREATE INDEX idx_aggregates_tenant_period ON aggregates (tenant_id, period_start DESC);