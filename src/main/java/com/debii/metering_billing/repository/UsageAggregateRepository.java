package com.debii.metering_billing.repository;

import com.debii.metering_billing.entity.UsageAggregate;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface UsageAggregateRepository extends R2dbcRepository<UsageAggregate, Void> { // Note: Void PK

    /**
     * Atomically increments the usage_count for a given aggregation window.
     * This is the key to preventing race conditions and handling late events
     * correctly.
     *
     * @return The number of rows updated (0 or 1).
     */
    @Modifying
    @Query("UPDATE aggregates SET usage_count = usage_count + :amount " +
            "WHERE tenant_id = :tenantId AND metric = :metric AND period_start = :periodStart")
    Mono<Long> incrementUsage(UUID tenantId, String metric, Instant periodStart, long amount);

    @Query("SELECT * FROM aggregates WHERE tenant_id = :tenantId AND metric = :metric AND period_start >= :since")
    Flux<UsageAggregate> findByTenantAndMetricSince(UUID tenantId, String metric, Instant since);
}
