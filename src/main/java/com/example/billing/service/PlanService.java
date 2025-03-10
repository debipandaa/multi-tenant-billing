package com.example.billing.service;

import com.example.billing.entity.Plan;
import com.example.billing.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Plan createPlan(Plan plan) {
        return planRepository.save(plan);
    }

    public Optional<Plan> getPlan(String id) {
        return planRepository.findById(id);
    }

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public void deletePlan(String id) {
        planRepository.deleteById(id);
    }
}
// auto-generated minor edit 6505
// auto-generated minor edit 25229
// auto-generated minor edit 7895
// auto-generated minor edit 3646
// Auto-generated change #14
// Auto-generated change #51
// Auto-generated change #94
// minor edit 32225
// minor edit 3692
// Auto-generated change #8
// Auto-generated change #9
// Auto-generated change #38
// Auto-generated change #60
// Auto-generated change #33
