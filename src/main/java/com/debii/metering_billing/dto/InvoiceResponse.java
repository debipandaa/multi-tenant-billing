package com.debii.metering_billing.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

// This is the "public-facing" version of an Invoice
public record InvoiceResponse(
                UUID id,
                UUID tenantId,
                Instant issuedAt,
                BigDecimal totalAmount,
                String status,
                String pdfUrl,
                List<LineItem> lineItems // <-- The clean, serializable list
) {
}