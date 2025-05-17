package com.example.billing.service;

import com.example.billing.entity.Aggregate;
import com.example.billing.entity.Invoice;
import com.example.billing.entity.LineItem;
import com.example.billing.entity.Plan;
import com.example.billing.repository.AggregateRepository;
import com.example.billing.repository.InvoiceRepository;
import com.example.billing.repository.PlanRepository;
import com.example.billing.repository.TenantRepository;
import com.example.billing.util.PdfInvoiceGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    private final PricingService pricingService;
    private final AggregateRepository aggregateRepository;
    private final PlanRepository planRepository;
    private final InvoiceRepository invoiceRepository;
    private final TenantRepository tenantRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public InvoiceService(PricingService pricingService,
            AggregateRepository aggregateRepository,
            PlanRepository planRepository,
            InvoiceRepository invoiceRepository,
            TenantRepository tenantRepository) {
        this.pricingService = pricingService;
        this.aggregateRepository = aggregateRepository;
        this.planRepository = planRepository;
        this.invoiceRepository = invoiceRepository;
        this.tenantRepository = tenantRepository;

    }

    public Invoice generateInvoice(String tenantId, LocalDate periodStart, LocalDate periodEnd) {
        // 1. Fetch aggregates
        List<Aggregate> aggregates = aggregateRepository
                .findByTenantIdAndPeriod(tenantId, periodStart, periodEnd);
        if (aggregates.isEmpty()) {
            throw new RuntimeException("No usage data found for tenant " + tenantId);
        }

        // 2. Get tenant plan
        Plan plan = tenantRepository.findPlanByTenantId(tenantId)
                .orElseThrow(() -> new RuntimeException("Plan not found for tenant"));

        // 3. Calculate line items
        List<LineItem> lineItems = pricingService.calculatePrice(tenantId, aggregates, plan);

        // 4. Sum total
        BigDecimal totalAmount = lineItems.stream()
                .map(LineItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 5. Generate PDF
        String pdfPath = PdfInvoiceGenerator.generateInvoicePdf(plan.getName(),
                UUID.randomUUID(), lineItems, totalAmount);

        // 6. Save invoice
        Invoice invoice = new Invoice();
        invoice.setId(UUID.randomUUID().toString());
        invoice.setTenantId(tenantId);
        invoice.setPeriodStart(periodStart);
        invoice.setPeriodEnd(periodEnd);
        invoice.setTotalAmount(totalAmount);
        invoice.setLineItemsJson(serializeLineItems(lineItems));
        invoice.setPdfUrl(pdfPath);
        invoice.setCreatedAt(Instant.now());

        invoiceRepository.save(invoice);

        return invoice;
    }

    private String serializeLineItems(List<LineItem> items) {
        try {
            return objectMapper.writeValueAsString(items);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing line items", e);
        }
    }
}
// auto-generated minor edit 28405
// auto-generated minor edit 24466
// auto-generated minor edit 32725
// auto-generated minor edit 2495
// Auto-generated change #63
// Auto-generated change #76
// minor edit 25354
// minor edit 1052
// minor edit 6370
// Auto-generated change #45
// Auto-generated change #49
// Auto-generated change #3
// Auto-generated change #42
// Auto-generated change #45
