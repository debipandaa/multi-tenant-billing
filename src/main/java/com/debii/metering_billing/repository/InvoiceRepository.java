package com.debii.metering_billing.repository;

import com.debii.metering_billing.entity.Invoice;

import reactor.core.publisher.Flux;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends R2dbcRepository<Invoice, UUID> {
    Flux<Invoice> findByTenantId(UUID tenantId);
}