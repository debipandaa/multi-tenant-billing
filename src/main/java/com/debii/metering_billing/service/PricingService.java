package com.debii.metering_billing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.debii.metering_billing.dto.InvoicePreview;
// import com.debii.metering_billing.model.pricing.InvoicePreview
import com.debii.metering_billing.dto.LineItem;
// import com.debii.metering_billing.model.pricing.PricingPlan;
import com.debii.metering_billing.model.pricing.MetricPricing;
import com.debii.metering_billing.model.pricing.PriceTier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.debii.metering_billing.repository.TenantRepository;
import com.debii.metering_billing.repository.PlanRepository;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class PricingService {

    private final TenantRepository tenantRepository;
    private final PlanRepository planRepository;
    private final UsageQueryService usageQueryService;
    private final ObjectMapper objectMapper; // For parsing JSON

    public PricingService(TenantRepository tenantRepository, PlanRepository planRepository,
            UsageQueryService usageQueryService, ObjectMapper objectMapper) {
        this.tenantRepository = tenantRepository;
        this.planRepository = planRepository;
        this.usageQueryService = usageQueryService;
        this.objectMapper = objectMapper;
    }

    public Mono<InvoicePreview> generateInvoicePreview(UUID tenantId) {
        return tenantRepository.findById(tenantId)
                .flatMap(tenant -> planRepository.findById(tenant.getPlanId()))
                .flatMap(plan -> {
                    try {
                        // PricingPlan pricingPlan = objectMapper.readValue(plan.pricingJson(),
                        // PricingPlan.class);
                        // NEW LINES TO ADD:
                        TypeReference<Map<String, MetricPricing>> typeRef = new TypeReference<>() {
                        };
                        Map<String, MetricPricing> pricingMap = objectMapper.readValue(plan.pricingJson(), typeRef);

                        List<Mono<LineItem>> lineItemMonos = new ArrayList<>();

                        // pricingPlan.pricing().forEach((metric, metricPricing) -> {
                        // Mono<LineItem> mono = usageQueryService.getUsageForCurrentPeriod(tenantId,
                        // metric)
                        // .map(aggregate -> aggregate.usageCount())
                        // .reduce(0L, Long::sum) // Sum up all usage for the metric
                        // .map(totalUsage -> calculateLineItem(metric, totalUsage, metricPricing));
                        // lineItemMonos.add(mono);
                        // });
                        pricingMap.forEach((metric, metricPricing) -> { // Use pricingMap here
                            Mono<LineItem> mono = usageQueryService.getUsageForCurrentPeriod(tenantId, metric)
                                    .map(aggregate -> aggregate.usageCount())
                                    .reduce(0L, Long::sum)
                                    .map(totalUsage -> calculateLineItem(metric, totalUsage, metricPricing));
                            lineItemMonos.add(mono);
                        });

                        return Mono.zip(lineItemMonos, results -> {
                            List<LineItem> lineItems = new ArrayList<>();
                            BigDecimal total = BigDecimal.ZERO;
                            for (Object result : results) {
                                LineItem item = (LineItem) result;
                                lineItems.add(item);
                                total = total.add(item.subtotal());
                            }
                            return new InvoicePreview(tenantId, total, lineItems);
                        });

                    } catch (IOException e) {
                        return Mono.error(new RuntimeException("Failed to parse pricing plan JSON", e));
                    }
                });
    }

    private LineItem calculateLineItem(String metric, long totalUsage,
            MetricPricing metricPricing) {
        // For now, we only implement TIERED. PER_UNIT would be a simple multiplication.
        BigDecimal totalCost = BigDecimal.ZERO;
        long remainingUsage = totalUsage;
        long tierBase = 0;

        for (PriceTier tier : metricPricing.tiers()) {
            if (remainingUsage <= 0)
                break;

            long tierLimit = tier.up_to().equalsIgnoreCase("Infinity") ? Long.MAX_VALUE : Long.parseLong(tier.up_to());
            long unitsInThisTier = Math.min(remainingUsage, tierLimit - tierBase);

            BigDecimal costForTier = tier.unitPrice().multiply(BigDecimal.valueOf(unitsInThisTier));
            totalCost = totalCost.add(costForTier);

            remainingUsage -= unitsInThisTier;
            tierBase = tierLimit;
        }

        // This is a simplified line item; a real one would have multiple lines per
        // tier.
        return new LineItem(
                String.format("Usage for metric '%s'", metric),
                totalUsage,
                BigDecimal.ZERO, // Unit price is complex for tiered, so we set subtotal directly
                totalCost);
    }
}