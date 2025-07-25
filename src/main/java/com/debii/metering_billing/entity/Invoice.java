package com.debii.metering_billing.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table("invoices")
public record Invoice(
        @Id UUID id,
        UUID tenantId,
        UUID planId,
        Instant periodStart,
        Instant periodEnd,
        BigDecimal totalAmount,
        String status,
        String lineItemsJson,
        String pdfUrl,
        Instant issuedAt) {
    // Helper method to update the PDF URL since records are immutable
    public Invoice withPdfUrl(String url) {
        return new Invoice(this.id, this.tenantId, this.planId, this.periodStart, this.periodEnd, this.totalAmount,
                this.status, this.lineItemsJson, url, this.issuedAt);
    }
}