package com.debii.metering_billing.model.pricing;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

// Represents a single tier in a TIERED strategy
public record PriceTier(String up_to, @JsonProperty("unit_price") BigDecimal unitPrice) {
}