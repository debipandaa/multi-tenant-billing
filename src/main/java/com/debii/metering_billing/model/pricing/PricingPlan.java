package com.debii.metering_billing.model.pricing;

import java.util.Map;

// Represents the entire JSON object
public record PricingPlan(Map<String, MetricPricing> pricing) {
}