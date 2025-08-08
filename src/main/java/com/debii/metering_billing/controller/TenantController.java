package com.debii.metering_billing.controller;

import com.debii.metering_billing.dto.CreateTenantRequest;
import com.debii.metering_billing.entity.Tenant;
import com.debii.metering_billing.service.TenantService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public Mono<ResponseEntity<Tenant>> createTenant(@RequestBody CreateTenantRequest request) {
        return tenantService.createTenant(request)
                .map(tenant -> ResponseEntity.status(HttpStatus.CREATED).body(tenant));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Tenant>> getTenantById(@PathVariable UUID id) {
        return tenantService.getTenantById(id)
                .map(ResponseEntity::ok);
        // .map(tenant -> ResponseEntity.ok(tenant)); is the same thing
    }
}