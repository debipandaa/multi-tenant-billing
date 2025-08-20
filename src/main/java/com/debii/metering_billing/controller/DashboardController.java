package com.debii.metering_billing.controller;

import com.debii.metering_billing.repository.InvoiceRepository;
import com.debii.metering_billing.repository.TenantRepository;
import com.debii.metering_billing.service.UsageQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller // Note: Not @RestController
public class DashboardController {

    private final TenantRepository tenantRepository;
    private final UsageQueryService usageQueryService;
    private final InvoiceRepository invoiceRepository;

    public DashboardController(TenantRepository tenantRepository, UsageQueryService usageQueryService,
            InvoiceRepository invoiceRepository) {
        this.tenantRepository = tenantRepository;
        this.usageQueryService = usageQueryService;
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping("/dashboard/tenants/{tenantId}")
    public Mono<String> showTenantDashboard(@PathVariable UUID tenantId, Model model) {
        // The Model object is like a suitcase where we'll put all our data for the view

        // We will fetch all data in parallel using Mono.zip
        return Mono.zip(
                tenantRepository.findById(tenantId),
                usageQueryService.getUsageForCurrentPeriod(tenantId, "api_calls").collectList(),
                invoiceRepository.findByTenantId(tenantId).collectList()).map(tuple -> {
                    // Add the data to the model with descriptive names
                    model.addAttribute("tenant", tuple.getT1());
                    model.addAttribute("usage", tuple.getT2());
                    model.addAttribute("invoices", tuple.getT3());

                    // Return the name of the HTML file (without the .html extension)
                    return "dashboard";
                });
    }
}