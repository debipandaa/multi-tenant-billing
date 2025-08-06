package com.debii.metering_billing.controller;

import com.debii.metering_billing.dto.InvoicePreview;
import com.debii.metering_billing.dto.InvoiceResponse;
import com.debii.metering_billing.entity.Invoice;
import com.debii.metering_billing.mapper.InvoiceMapper;
import com.debii.metering_billing.repository.InvoiceRepository;
import com.debii.metering_billing.repository.TenantRepository;
import com.debii.metering_billing.service.BillingRunService;
import com.debii.metering_billing.service.PricingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/api/billing")
public class BillingController {

    private final PricingService pricingService;
    private final InvoiceRepository invoiceRepository;
    private final BillingRunService billingRunService;
    private final TenantRepository tenantRepository;
    private final InvoiceMapper invoiceMapper;

    public BillingController(PricingService pricingService, InvoiceRepository invoiceRepository,
            BillingRunService billingRunService, TenantRepository tenantRepository, InvoiceMapper invoiceMapper) {
        this.pricingService = pricingService;
        this.invoiceRepository = invoiceRepository;
        this.billingRunService = billingRunService;
        this.tenantRepository = tenantRepository;
        this.invoiceMapper = invoiceMapper;
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

    // @PostMapping("/admin/tenants/{tenantId}/generate-invoice")
    // public Mono<Invoice> triggerInvoiceGeneration(@PathVariable UUID tenantId) {
    // return tenantRepository.findById(tenantId)
    // .flatMap(billingRunService::generateInvoiceForTenant);
    // }
    @PostMapping("/admin/tenants/{tenantId}/generate-invoice")
    public Mono<InvoiceResponse> triggerInvoiceGeneration(@PathVariable UUID tenantId) { // <-- Change return type
        return tenantRepository.findById(tenantId)
                .flatMap(billingRunService::generateInvoiceForTenant)
                .map(invoiceMapper::toDto); // <-- ADD THIS MAPPING STEP
    }
}