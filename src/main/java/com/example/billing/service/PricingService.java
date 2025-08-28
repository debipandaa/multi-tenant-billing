package com.example.billing.service;

import com.example.billing.entity.Aggregate;
import com.example.billing.entity.LineItem;
import com.example.billing.entity.Plan;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PricingService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<LineItem> calculatePrice(String tenantId, List<Aggregate> usageAggregates, Plan plan) {
        List<LineItem> lineItems = new ArrayList<>();

        try {
            JsonNode pricing = objectMapper.valueToTree(plan.getPricingRules());

            for (Aggregate usage : usageAggregates) {
                String metric = usage.getMetric();
                long quantity = usage.getUsageCount();

                BigDecimal totalCost = BigDecimal.ZERO;

                // 1. Check if plan uses global tiers
                if (pricing.has("type") && "tiered".equals(pricing.get("type").asText()) && pricing.has("tiers")) {
                    totalCost = calculateTieredCost(pricing.get("tiers"), quantity);

                    // 2. Otherwise check per-metric pricing
                } else if (pricing.has(metric)) {
                    JsonNode metricNode = pricing.get(metric);

                    if (metricNode.isObject() && metricNode.has("tiers")) {
                        // format: {"api_calls": {"tiers": [...]}}
                        totalCost = calculateTieredCost(metricNode.get("tiers"), quantity);
                    } else if (metricNode.isArray()) {
                        // format: {"api_calls": [ ... ]}
                        totalCost = calculateTieredCost(metricNode, quantity);
                    } else {
                        // simple flat rate?
                        if (metricNode.has("pricePerUnit")) {
                            BigDecimal pricePerUnit = new BigDecimal(metricNode.get("pricePerUnit").asText());
                            totalCost = pricePerUnit.multiply(BigDecimal.valueOf(quantity));
                        }
                    }

                } else {
                    // no rule for this metric → cost is zero
                    continue;
                }

                LineItem item = new LineItem();
                item.setDescription(metric + " usage");
                item.setQuantity(quantity);
                item.setUnitPrice(
                        quantity > 0 ? totalCost.divide(BigDecimal.valueOf(quantity), 2, RoundingMode.HALF_UP)
                                : BigDecimal.ZERO);
                item.setAmount(totalCost);
                lineItems.add(item);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error parsing pricing rules", e);
        }

        return lineItems;
    }

    private BigDecimal calculateTieredCost(JsonNode tiersNode, long quantity) {
        BigDecimal totalCost = BigDecimal.ZERO;
        long remaining = quantity;

        Iterator<JsonNode> tiers = tiersNode.elements();
        while (tiers.hasNext() && remaining > 0) {
            JsonNode tier = tiers.next();
            long upto = tier.has("upto") && !tier.get("upto").isNull()
                    ? tier.get("upto").asLong()
                    : Long.MAX_VALUE;
            BigDecimal pricePerUnit = new BigDecimal(tier.get("price").asText());

            long tierUnits = Math.min(remaining, upto);
            BigDecimal tierCost = pricePerUnit.multiply(BigDecimal.valueOf(tierUnits));
            totalCost = totalCost.add(tierCost);
            remaining -= tierUnits;
        }
        return totalCost;
    }
}
// auto-generated minor edit 1487
// auto-generated minor edit 6138
// auto-generated minor edit 21094
// Auto-generated change #33
// Auto-generated change #65
// minor edit 5814
// minor edit 8991
// Auto-generated change #30
// Auto-generated change #58
// Auto-generated change #99
