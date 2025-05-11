package com.debii.metering_billing.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.debii.metering_billing.entity.UsageEvent;

import java.util.UUID;

@Repository
public interface UsageEventRepository extends R2dbcRepository<UsageEvent, UUID> {
    // Spring Data provides all the common methods like save(), findById(), etc.
    // We can add custom query methods here later if we need them.
}