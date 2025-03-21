package com.example.billing.controller;

import com.example.billing.entity.Aggregate;
import com.example.billing.service.AggregateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aggregate")
public class AggregationController {

    private final AggregateService aggregateService;

    public AggregationController(AggregateService aggregateService) {
        this.aggregateService = aggregateService;
    }

    /**
     * Trigger aggregation manually.
     */
    @PostMapping("/run")
    public ResponseEntity<String> runAggregation() {
        aggregateService.aggregateUsage();
        return ResponseEntity.ok("Usage aggregation completed successfully.");
    }

    /**
     * Fetch all current aggregates.
     */
    @GetMapping
    public ResponseEntity<List<Aggregate>> getAllAggregates() {
        List<Aggregate> aggregates = aggregateService.getAllAggregates();
        return ResponseEntity.ok(aggregates);
    }
}
// auto-generated minor edit 29653
// auto-generated minor edit 247
// auto-generated minor edit 11667
// auto-generated minor edit 2231
// auto-generated minor edit 17206
// auto-generated minor edit 27684
// auto-generated minor edit 20411
// Auto-generated change #16
// Auto-generated change #37
// Auto-generated change #38
// minor edit 24573
// minor edit 15792
// minor edit 21478
// minor edit 29401
// minor edit 8205
// minor edit 19824
// Auto-generated change #40
// Auto-generated change #86
// Auto-generated change #10
// Auto-generated change #39
