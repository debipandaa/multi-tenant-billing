package com.debii.metering_billing.model;

import java.time.Instant;
import java.util.UUID;

public record UsageEvent(
        UUID id,
        UUID tenantId,
        UUID userId,
        String metric,
        int amount,
        Instant eventTime,
        Instant ingestedAt,
        String dedupeKey) {
}