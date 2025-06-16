package com.debii.metering_billing.entity;

import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant;
import java.util.UUID;

@Table("aggregates")
public record UsageAggregate(
        UUID tenantId,
        String metric,
        Instant periodStart,
        Instant periodEnd,
        long usageCount) {
}