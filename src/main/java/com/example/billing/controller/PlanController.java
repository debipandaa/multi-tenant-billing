package com.example.billing.controller;

import com.example.billing.entity.Plan;
import com.example.billing.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) {
        Plan saved = planService.createPlan(plan);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlan(@PathVariable String id) {
        Optional<Plan> plan = planService.getPlan(id);
        return plan.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Plan>> getAllPlans() {
        return ResponseEntity.ok(planService.getAllPlans());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable String id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}
// auto-generated minor edit 21174
// auto-generated minor edit 26730
// auto-generated minor edit 950
// auto-generated minor edit 29187
// auto-generated minor edit 7386
// auto-generated minor edit 12512
// Auto-generated change #43
// Auto-generated change #52
// Auto-generated change #100
// minor edit 13753
// Auto-generated change #19
// Auto-generated change #90
// Auto-generated change #21
