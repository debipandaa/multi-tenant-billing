package com.example.billing.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "aggregates")
@IdClass(AggregateId.class)
public class Aggregate {

    @Id
    @Column(name = "tenant_id", length = 36)
    private String tenantId;

    @Id
    private String metric;

    @Id
    @Column(name = "period_start")
    private LocalDate periodStart;

    @Column(name = "period_end")
    private LocalDate periodEnd;

    @Column(name = "usage_count")
    private long usageCount;

    public Aggregate() {
    }

    public Aggregate(String tenantId, String metric, LocalDate periodStart, LocalDate periodEnd, long usageCount) {
        this.tenantId = tenantId;
        this.metric = metric;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.usageCount = usageCount;
    }

    // Getters and Setters
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }

    public long getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(long usageCount) {
        this.usageCount = usageCount;
    }
}
// auto-generated minor edit 11678
// auto-generated minor edit 9503
// auto-generated minor edit 25154
// Auto-generated change #1
// Auto-generated change #7
// Auto-generated change #47
// Auto-generated change #53
// minor edit 98
// minor edit 8463
// minor edit 6697
// Auto-generated change #13
// Auto-generated change #2
// Auto-generated change #95
// Auto-generated change #97
