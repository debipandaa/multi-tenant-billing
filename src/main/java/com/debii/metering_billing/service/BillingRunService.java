package com.debii.metering_billing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.debii.metering_billing.dto.InvoicePreview;
import com.debii.metering_billing.entity.Invoice;
import com.debii.metering_billing.entity.Tenant;
import com.debii.metering_billing.repository.InvoiceRepository;
import com.debii.metering_billing.repository.TenantRepository;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

@Service
public class BillingRunService {
    private static final Logger log = LoggerFactory.getLogger(BillingRunService.class);

    private final TenantRepository tenantRepository;
    private final PricingService pricingService;
    private final InvoiceRepository invoiceRepository;
    private final PdfGenerationService pdfGenerationService;
    private final ObjectMapper objectMapper;

    public BillingRunService(TenantRepository tenantRepository, PricingService pricingService,
            InvoiceRepository invoiceRepository, PdfGenerationService pdfGenerationService,
            ObjectMapper objectMapper) {
        this.tenantRepository = tenantRepository;
        this.pricingService = pricingService;
        this.invoiceRepository = invoiceRepository;
        this.pdfGenerationService = pdfGenerationService;
        this.objectMapper = objectMapper;
    }

    // This cron expression means "at midnight on the 1st day of every month"
    @Scheduled(cron = "0 0 0 1 * *")
    public void runMonthlyBilling() {
        log.info("Starting monthly billing run...");
        tenantRepository.findAll()
                .cast(Tenant.class)
                .flatMap(tenant -> pricingService.generateInvoicePreview(tenant.getId()))
                .flatMap(preview -> {
                    try {
                        String lineItemsJson = objectMapper.writeValueAsString(preview.lineItems());
                        Invoice invoice = new Invoice(null, preview.tenantId(), null, // planId can be added
                                Instant.now().minusSeconds(2592000), Instant.now(), // Dummy period
                                preview.totalAmount(), "FINALIZED", lineItemsJson, null, Instant.now());
                        return invoiceRepository.save(invoice);
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                })
                .flatMap(savedInvoice -> {
                    try {
                        String pdfUrl = pdfGenerationService.generateInvoicePdf(savedInvoice);
                        return invoiceRepository.save(savedInvoice.withPdfUrl(pdfUrl));
                    } catch (IOException e) {
                        log.error("Failed to generate PDF for invoice {}", savedInvoice.id(), e);
                        return Mono.just(savedInvoice); // Proceed without PDF URL
                    }
                })
                .subscribe(
                        finalInvoice -> log.info("Successfully generated invoice {} for tenant {}", finalInvoice.id(),
                                finalInvoice.tenantId()),
                        error -> log.error("Error during billing run", error));
    }
}