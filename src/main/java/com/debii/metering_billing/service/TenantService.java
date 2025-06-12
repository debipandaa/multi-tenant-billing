package com.debii.metering_billing.service;

import com.debii.metering_billing.dto.CreateTenantRequest;
import com.debii.metering_billing.entity.Tenant;
import com.debii.metering_billing.exception.PlanNotFoundException;
import com.debii.metering_billing.exception.TenantNotFoundException;
import com.debii.metering_billing.repository.PlanRepository;
import com.debii.metering_billing.repository.TenantRepository;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final PlanRepository planRepository;

    public TenantService(TenantRepository tenantRepository, PlanRepository planRepository) {
        this.tenantRepository = tenantRepository;
        this.planRepository = planRepository;
    }

    public Mono<Tenant> getTenantById(UUID id) {
        return tenantRepository.findById(id)
                .switchIfEmpty(Mono.error(new TenantNotFoundException("Tenant not found with id: " + id)));
    }

    public Mono<Tenant> createTenant(CreateTenantRequest request) {
        // First, validate that the plan exists
        return planRepository.existsById(request.planId())
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new PlanNotFoundException("Plan not found with id: " + request.planId())))
                .flatMap(exists -> {

                    Tenant newTenant = new Tenant(
                            null,
                            request.name(),
                            request.planId(),
                            Instant.now());
                    return tenantRepository.save(newTenant);
                });
    }
}