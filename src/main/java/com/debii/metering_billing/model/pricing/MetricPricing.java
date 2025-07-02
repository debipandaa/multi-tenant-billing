package com.debii.metering_billing.model.pricing;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

// Represents the rules for a single metric
public record MetricPricing(
        PricingStrategy strategy,
        List<PriceTier> tiers,
        @JsonProperty("unit_price") BigDecimal unitPrice // For PER_UNIT strategy
) {
}