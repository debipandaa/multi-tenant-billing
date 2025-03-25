package com.example.billing.dto;

import java.time.Instant;

public class UsageEventRequest {

    private String tenantId;
    private String metric;
    private int amount;
    private Instant eventTime;
    private String dedupeKey;

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Instant getEventTime() {
        return eventTime;
    }

    public void setEventTime(Instant eventTime) {
        this.eventTime = eventTime;
    }

    public String getDedupeKey() {
        return dedupeKey;
    }

    public void setDedupeKey(String dedupeKey) {
        this.dedupeKey = dedupeKey;
    }
}
// auto-generated minor edit 27577
// auto-generated minor edit 17719
// auto-generated minor edit 14115
// auto-generated minor edit 12978
// auto-generated minor edit 20423
// auto-generated minor edit 28595
// Auto-generated change #99
// minor edit 17598
// minor edit 26819
// Auto-generated change #58
// Auto-generated change #59
// Auto-generated change #75
// Auto-generated change #83
