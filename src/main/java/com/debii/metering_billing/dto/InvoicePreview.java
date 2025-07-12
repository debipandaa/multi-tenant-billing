package com.debii.metering_billing.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

// The final output of the pricing engine
public record InvoicePreview(UUID tenantId, BigDecimal totalAmount, List<LineItem> lineItems) {
}