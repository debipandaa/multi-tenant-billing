package com.debii.metering_billing.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import com.debii.metering_billing.service.AggregationService;
import com.debii.metering_billing.entity.UsageEvent;
import com.debii.metering_billing.repository.UsageEventRepository;

@Service
public class UsageEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(UsageEventConsumer.class);

    private final UsageEventRepository usageEventRepository;
    private final AggregationService aggregationService; // <-- INJECT

    public UsageEventConsumer(UsageEventRepository usageEventRepository, AggregationService aggregationService) {
        this.usageEventRepository = usageEventRepository;
        this.aggregationService = aggregationService; // <-- INJECT
    }

    @KafkaListener(topics = "usage-events", groupId = "billing-service-persistor")
    public void consumeUsageEvent(UsageEvent event) {
        log.info("Consumed event with dedupe key [{}], processing...", event.dedupeKey());

        // We combine two reactive operations: saving the raw event and aggregating it.
        // Mono.zip allows us to run them in parallel.
        Mono.zip(
                usageEventRepository.save(event),
                aggregationService.processAndAggregate(event))
                // VERY IMPORTANT: Kafka listeners run on a non-blocking thread, but our DB
                // calls are blocking.
                // We must subscribe on a bounded elastic scheduler to avoid blocking the main
                // Kafka thread.
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(
                        tuple -> log.info("Successfully processed event id [{}]", tuple.getT1().id()),
                        error -> log.error("Error processing event with dedupe key [{}]: {}", event.dedupeKey(),
                                error.getMessage()));
    }
}