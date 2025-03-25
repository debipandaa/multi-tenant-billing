package com.example.billing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.billing.entity.Plan;
import com.example.billing.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, String> {
    @Query("SELECT p FROM Plan p JOIN Tenant t ON t.planId = p.id WHERE t.id = :tenantId")
    Optional<Plan> findPlanByTenantId(@Param("tenantId") String tenantId);

}
// auto-generated minor edit 7746
// auto-generated minor edit 14891
// auto-generated minor edit 4310
// auto-generated minor edit 20629
// auto-generated minor edit 32463
// auto-generated minor edit 18455
// Auto-generated change #44
// Auto-generated change #11
// Auto-generated change #41
// Auto-generated change #27
// Auto-generated change #54
