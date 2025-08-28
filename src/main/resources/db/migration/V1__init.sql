CREATE TABLE tenants (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  plan_id CHAR(36) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE plans (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  pricing_rules JSON NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usage_events (
  id CHAR(36) PRIMARY KEY,
  tenant_id CHAR(36) NOT NULL,
  metric VARCHAR(50) NOT NULL,
  amount INT NOT NULL,
  event_time TIMESTAMP NOT NULL,
  dedupe_key VARCHAR(200) UNIQUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (tenant_id) REFERENCES tenants(id)
);

CREATE TABLE aggregates (
  tenant_id CHAR(36) NOT NULL,
  metric VARCHAR(50) NOT NULL,
  period_start DATE NOT NULL,
  period_end DATE NOT NULL,
  usage_count BIGINT NOT NULL,
  PRIMARY KEY (tenant_id, metric, period_start)
);

CREATE TABLE invoices (
  id CHAR(36) PRIMARY KEY,
  tenant_id CHAR(36) NOT NULL,
  period_start DATE NOT NULL,
  period_end DATE NOT NULL,
  total_amount DECIMAL(12,2),
  line_items JSON,
  pdf_url VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (tenant_id) REFERENCES tenants(id)
);
// auto-generated minor edit 31201
// auto-generated minor edit 14449
// auto-generated minor edit 17941
// Auto-generated change #19
// minor edit 14203
// minor edit 18300
// minor edit 11568
// minor edit 32029
