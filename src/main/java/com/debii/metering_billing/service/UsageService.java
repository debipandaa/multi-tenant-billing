package com.debii.metering_billing.service;

import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.debii.metering_billing.dto.UsageEventRequest;
import com.debii.metering_billing.entity.UsageEvent;
import com.debii.metering_billing.exception.DuplicateEventException;
import com.debii.metering_billing.exception.TenantNotFoundException;
import com.debii.metering_billing.repository.TenantRepository;

import java.time.Duration;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.UUID;

@Service
public class UsageService {

    private final TenantRepository tenantRepository;
    private final ReactiveStringRedisTemplate redisTemplate;
    private final KafkaTemplate<String, UsageEvent> kafkaTemplate;

    private static final String KAFKA_TOPIC = "usage-events";
    private static final Duration DEDUPE_TTL = Duration.ofHours(24);

    public UsageService(TenantRepository tenantRepository,
            ReactiveStringRedisTemplate redisTemplate,
            KafkaTemplate<String, UsageEvent> kafkaTemplate) {
        this.tenantRepository = tenantRepository;
        this.redisTemplate = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<Void> processUsageEvent(UsageEventRequest request) {
        // This is a reactive chain. Each step executes sequentially.
        // If any step fails (e.g., returns an empty Mono or an error), the chain stops.
        return validateTenant(request.tenantId())
                .then(checkForDuplicate(request.dedupeKey()))
                .flatMap(wasSet -> {
                    if (!wasSet) {
                        // If it wasn't set, it means the key already existed.
                        return Mono
                                .error(new DuplicateEventException("Duplicate event with key: " + request.dedupeKey()));
                    }

                    // Create the full event object
                    UsageEvent event = new UsageEvent(
                            UUID.randomUUID(),
                            request.tenantId(),
                            null, // userId
                            request.metric(),
                            request.amount(),
                            Instant.now(), // eventTime
                            Instant.now(), // ingestedAt
                            request.dedupeKey());

                    // Send to Kafka and return a Mono<Void> for completion signal
                    return Mono.fromFuture(kafkaTemplate.send(KAFKA_TOPIC, event.tenantId().toString(), event)).then();
                });

    }

    private Mono<Void> validateTenant(UUID tenantId) {
        return tenantRepository.existsById(tenantId)
                .filter(exists -> exists) // Only continue if exists is true
                .switchIfEmpty(Mono.error(new TenantNotFoundException("Tenant not found with id: " + tenantId)))
                .then(); // Discard the boolean result and just signal completion
    }

    private Mono<Boolean> checkForDuplicate(String dedupeKey) {
        // setIfAbsent is an atomic operation. It will set the key only if it does not
        // exist.
        // It returns true if the key was set, false otherwise.
        String redisKey = "dedupe:" + dedupeKey;
        return redisTemplate.opsForValue().setIfAbsent(redisKey, "1", DEDUPE_TTL);
    }
}