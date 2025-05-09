package com.debii.metering_billing.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("usage_events") // Maps this class to the "usage_events" table in PostgreSQL
public record UsageEvent(
                @Id // Marks this field as the primary key
                UUID id,
                UUID tenantId,
                UUID userId,
                String metric,
                int amount,
                Instant eventTime,
                Instant ingestedAt,
                String dedupeKey) {
}
