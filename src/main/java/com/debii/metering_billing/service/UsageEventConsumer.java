package com.debii.metering_billing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.debii.metering_billing.entity.UsageEvent;
import com.debii.metering_billing.repository.UsageEventRepository;

@Service
public class UsageEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(UsageEventConsumer.class);

    private final UsageEventRepository usageEventRepository;

    public UsageEventConsumer(UsageEventRepository usageEventRepository) {
        this.usageEventRepository = usageEventRepository;
    }

    @KafkaListener(topics = "usage-events", groupId = "billing-service-persistor")
    public void consumeUsageEvent(UsageEvent event) {
        log.info("Consumed event with dedupe key [{}], saving to DB...", event.dedupeKey());

        // Use the repository to save the event.
        // The .subscribe() method kicks off the reactive operation.
        usageEventRepository.save(event)
                .subscribe(
                        savedEvent -> log.info("Successfully saved event id [{}]", savedEvent.id()),
                        error -> log.error("Error saving event with dedupe key [{}]: {}", event.dedupeKey(),
                                error.getMessage()));
    }
}