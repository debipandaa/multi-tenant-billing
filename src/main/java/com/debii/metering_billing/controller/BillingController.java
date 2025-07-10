package com.debii.metering_billing.controller;

import com.debii.metering_billing.dto.InvoicePreview;
import com.debii.metering_billing.service.PricingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/billing")
public class BillingController {

    private final PricingService pricingService;

    public BillingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/tenants/{tenantId}/preview")
    public Mono<InvoicePreview> getInvoicePreview(@PathVariable UUID tenantId) {
        return pricingService.generateInvoicePreview(tenantId);
    }
}