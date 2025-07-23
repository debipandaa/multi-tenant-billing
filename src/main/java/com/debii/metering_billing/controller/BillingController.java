package com.debii.metering_billing.controller;

import com.debii.metering_billing.dto.InvoicePreview;
import com.debii.metering_billing.entity.Invoice;
import com.debii.metering_billing.repository.InvoiceRepository;
import com.debii.metering_billing.service.PricingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.io.File;

import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/v1/billing")
public class BillingController {

    private final PricingService pricingService;
    private final InvoiceRepository invoiceRepository;

    public BillingController(PricingService pricingService, InvoiceRepository invoiceRepository) {
        this.pricingService = pricingService;
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping("/tenants/{tenantId}/preview")
    public Mono<InvoicePreview> getInvoicePreview(@PathVariable UUID tenantId) {
        return pricingService.generateInvoicePreview(tenantId);
    }

    @GetMapping("/tenants/{tenantId}/invoices")
    public Flux<Invoice> getInvoicesForTenant(@PathVariable UUID tenantId) {
        // In a real app, you would add filtering and pagination
        return invoiceRepository.findByTenantId(tenantId); // You need to add this method to the repository
    }

    @GetMapping("/invoices/{invoiceId}/download")
    public Mono<ResponseEntity<Resource>> downloadInvoice(@PathVariable UUID invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .map(invoice -> {
                    File file = new File(invoice.pdfUrl());
                    Resource resource = new FileSystemResource(file);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(resource);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}