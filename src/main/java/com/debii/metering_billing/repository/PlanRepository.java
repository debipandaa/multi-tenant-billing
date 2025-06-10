package com.debii.metering_billing.repository;

import com.debii.metering_billing.entity.Plan;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlanRepository extends R2dbcRepository<Plan, UUID> {
}