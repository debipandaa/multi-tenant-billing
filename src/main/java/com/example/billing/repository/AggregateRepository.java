package com.example.billing.repository;

import com.example.billing.entity.Aggregate;
import com.example.billing.entity.AggregateId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AggregateRepository extends JpaRepository<Aggregate, AggregateId> {

        Optional<Aggregate> findByTenantIdAndMetricAndPeriodStart(
                        String tenantId, String metric, LocalDate periodStart);

        @Query("SELECT a FROM Aggregate a " +
                        "WHERE a.tenantId = :tenantId " +
                        "AND a.periodStart >= :start " +
                        "AND a.periodEnd <= :end")
        List<Aggregate> findByTenantIdAndPeriod(@Param("tenantId") String tenantId,
                        @Param("start") LocalDate start,
                        @Param("end") LocalDate end);

}
// auto-generated minor edit 17563
// auto-generated minor edit 17126
// auto-generated minor edit 15079
// auto-generated minor edit 30866
// auto-generated minor edit 4070
// auto-generated minor edit 32147
// minor edit 22059
// minor edit 3082
// minor edit 31998
// minor edit 8104
// Auto-generated change #16
// Auto-generated change #4
// Auto-generated change #65
// Auto-generated change #72
