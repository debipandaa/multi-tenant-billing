-- V4__create_invoices_table.sql

CREATE TABLE invoices (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    tenant_id UUID NOT NULL REFERENCES tenants (id),
    plan_id UUID NOT NULL REFERENCES plans (id),
    period_start TIMESTAMPTZ NOT NULL,
    period_end TIMESTAMPTZ NOT NULL,
    total_amount NUMERIC(10, 4) NOT NULL,
    status VARCHAR(50) NOT NULL, -- e.g., DRAFT, FINALIZED, PAID
    line_items_json JSONB NOT NULL,
    pdf_url VARCHAR(512),
    issued_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_invoices_tenant_id ON invoices (tenant_id);