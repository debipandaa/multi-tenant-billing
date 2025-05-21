package com.debii.metering_billing.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.debii.metering_billing.entity.Tenant;

import java.util.UUID;

@Repository
public interface TenantRepository extends R2dbcRepository<Tenant, UUID> {
    // We get existsById() for free from the parent interface!
}