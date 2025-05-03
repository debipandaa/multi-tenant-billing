package com.debii.metering_billing.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.debii.metering_billing.dto.UsageEventRequest;
import com.debii.metering_billing.service.UsageService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/usage")
public class UsageController {

    private final UsageService usageService;

    public UsageController(UsageService usageService) {
        this.usageService = usageService;
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> ingestUsage(@RequestBody UsageEventRequest request) {
        return usageService.processUsageEvent(request)
                .then(Mono.just(ResponseEntity.status(HttpStatus.ACCEPTED).build()));
    }
}