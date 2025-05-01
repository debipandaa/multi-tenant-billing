package com.debii.metering_billing.dto;

import java.util.UUID;

public record UsageEventRequest(
        UUID tenantId,
        String metric,
        int amount,
        String dedupeKey) {
}