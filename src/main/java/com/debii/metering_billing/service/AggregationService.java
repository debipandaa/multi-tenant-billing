package com.debii.metering_billing.service;

import com.debii.metering_billing.entity.UsageAggregate;
import com.debii.metering_billing.entity.UsageEvent;
import com.debii.metering_billing.repository.UsageAggregateRepository;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.temporal.ChronoUnit;

@Service
public class AggregationService {

    private static final Logger log = LoggerFactory.getLogger(AggregationService.class);

    private final UsageAggregateRepository aggregateRepository;
    private final ReactiveStringRedisTemplate redisTemplate;

    public AggregationService(UsageAggregateRepository aggregateRepository, ReactiveStringRedisTemplate redisTemplate) {
        this.aggregateRepository = aggregateRepository;
        this.redisTemplate = redisTemplate;
    }

    public Mono<Void> processAndAggregate(UsageEvent event) {
        // Step 1: Determine the time window (we'll use hourly windows)
        var periodStart = event.eventTime().truncatedTo(ChronoUnit.HOURS);
        var periodEnd = periodStart.plus(1, ChronoUnit.HOURS);

        // Step 2: Try to atomically update an existing aggregate record
        return aggregateRepository.incrementUsage(event.tenantId(), event.metric(), periodStart, event.amount())
                .flatMap(rowsUpdated -> {
                    if (rowsUpdated > 0) {
                        // Success! Record existed and was updated.
                        log.debug("Incremented existing aggregate for tenant {}", event.tenantId());
                        return Mono.empty(); // We are done.
                    }

                    // If rowsUpdated is 0, the record doesn't exist. We need to create it.
                    log.info("No existing aggregate found for tenant {}, creating a new one.", event.tenantId());
                    UsageAggregate newAggregate = new UsageAggregate(
                            event.tenantId(),
                            event.metric(),
                            periodStart,
                            periodEnd,
                            event.amount());
                    return aggregateRepository.save(newAggregate).then();
                })
                .then(updateRedisCache(event, periodStart)); // Step 3: Update Redis cache regardless
    }

    private Mono<Void> updateRedisCache(UsageEvent event, Instant periodStart) {
        // Example key:
        // "usage:a2e52de2-abe5-4e62-aa61-2c9a88aa219b:api_calls:1662393600"
        String redisKey = String.format("usage:%s:%s:%d",
                event.tenantId(), event.metric(), periodStart.getEpochSecond());

        // Atomically increment the value in Redis
        return redisTemplate.opsForValue().increment(redisKey, event.amount()).then();
    }
}