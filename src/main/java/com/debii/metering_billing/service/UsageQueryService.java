package com.debii.metering_billing.service;

import com.debii.metering_billing.entity.UsageAggregate;
import com.debii.metering_billing.repository.UsageAggregateRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class UsageQueryService {

    private final UsageAggregateRepository aggregateRepository;
    // We would also inject Redis here to build a cache-aside read pattern,
    // but for simplicity, we'll query Postgres directly for now.

    public UsageQueryService(UsageAggregateRepository aggregateRepository) {
        this.aggregateRepository = aggregateRepository;
    }

    /**
     * Gets the total usage for a tenant for a specific metric in the current
     * billing period (e.g., this month).
     */
    public Flux<UsageAggregate> getUsageForCurrentPeriod(UUID tenantId, String metric) {
        // For this example, let's define the "current period" as the last 30 days.
        Instant since = Instant.now().minus(30, ChronoUnit.DAYS);
        return aggregateRepository.findByTenantAndMetricSince(tenantId, metric, since);
    }
}