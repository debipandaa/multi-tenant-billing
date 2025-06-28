package com.debii.metering_billing.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.debii.metering_billing.dto.UsageEventRequest;
import com.debii.metering_billing.entity.UsageAggregate;
import com.debii.metering_billing.service.UsageService;
import com.debii.metering_billing.service.UsageQueryService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/usage")
public class UsageController {

    private final UsageService usageService;
    private final UsageQueryService usageQueryService;

    public UsageController(UsageService usageService, UsageQueryService usageQueryService) {
        this.usageService = usageService;
        this.usageQueryService = usageQueryService;
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> ingestUsage(@RequestBody UsageEventRequest request) {
        return usageService.processUsageEvent(request)
                .then(Mono.just(ResponseEntity.status(HttpStatus.ACCEPTED).build()));
    }

    @GetMapping("/tenants/{tenantId}")
    public Flux<UsageAggregate> getTenantUsage(
            @PathVariable UUID tenantId,
            @RequestParam String metric) {
        return usageQueryService.getUsageForCurrentPeriod(tenantId, metric);
    }
}