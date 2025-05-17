package com.debii.metering_billing.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.debii.metering_billing.dto.UsageEventRequest;
import com.debii.metering_billing.entity.UsageEvent;

import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.UUID;

@Service
public class UsageService {

    // private final TenantRepository tenantRepository;
    // private final ReactiveStringRedisTemplate redisTemplate;

    private final KafkaTemplate<String, UsageEvent> kafkaTemplate;
    private static final String KAFKA_TOPIC = "usage-events";

    public UsageService(KafkaTemplate<String, UsageEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<Void> processUsageEvent(UsageEventRequest request) {
        // Here's where you'd add your validation logic.
        // For now, we'll keep it simple and assume the data is valid.

        // TODO 1: Validate tenant exists using TenantRepository
        // example: tenantRepository.existsById(request.tenantId())
        // .filter(exists -> exists)
        // .switchIfEmpty(Mono.error(new TenantNotFoundException()));

        // TODO 2: Check for duplicates using Redis
        // example: redisTemplate.opsForValue().setIfAbsent("dedupe:" +
        // request.dedupeKey(), "1", Duration.ofHours(24))
        // .filter(wasSet -> wasSet)
        // .switchIfEmpty(Mono.error(new DuplicateEventException()));

        UsageEvent event = new UsageEvent(
                UUID.randomUUID(),
                request.tenantId(),
                null,
                request.metric(),
                request.amount(),
                Instant.now(),
                Instant.now(),
                request.dedupeKey());

        // Send the event to Kafka. This is an async operation.
        // We return a Mono<Void> to indicate when the send operation is complete.
        return Mono.fromFuture(kafkaTemplate.send(KAFKA_TOPIC, event.tenantId().toString(), event)).then();
    }
}
