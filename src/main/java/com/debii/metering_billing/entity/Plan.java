package com.debii.metering_billing.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("plans")
public record Plan(
        @Id UUID id,
        String name,
        String pricingJson, // Storing JSON as a string for simplicity for now
        Instant createdAt) {
}