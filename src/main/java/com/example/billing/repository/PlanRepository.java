package com.example.billing.repository;

import com.example.billing.entity.Plan;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, String> {
    Optional<Plan> findById(String id);
}
// auto-generated minor edit 24561
// auto-generated minor edit 4640
// Auto-generated change #54
// minor edit 10851
// Auto-generated change #27
// Auto-generated change #38
// Auto-generated change #47
