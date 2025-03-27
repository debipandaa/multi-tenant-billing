package com.example.billing.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class AggregateId implements Serializable {
    private String tenantId;
    private String metric;
    private LocalDate periodStart;

    public AggregateId() {
    }

    public AggregateId(String tenantId, String metric, LocalDate periodStart) {
        this.tenantId = tenantId;
        this.metric = metric;
        this.periodStart = periodStart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AggregateId))
            return false;
        AggregateId that = (AggregateId) o;
        return Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(metric, that.metric) &&
                Objects.equals(periodStart, that.periodStart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, metric, periodStart);
    }
}
// auto-generated minor edit 25629
// auto-generated minor edit 18554
// auto-generated minor edit 25591
// Auto-generated change #30
// Auto-generated change #60
// minor edit 24762
// Auto-generated change #43
// Auto-generated change #85
// Auto-generated change #97
// Auto-generated change #94
