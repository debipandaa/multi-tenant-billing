package com.debii.metering_billing.dto;

import java.math.BigDecimal;

// A single line item in the invoice
public record LineItem(String description, long quantity, BigDecimal unitPrice, BigDecimal subtotal) {
}