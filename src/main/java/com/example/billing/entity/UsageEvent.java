package com.example.billing.entity;

import jakarta.persistence.*;
import java.time.Instant;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "usage_events")
public class UsageEvent {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(name = "tenant_id", length = 36)
    private String tenantId;

    private String metric;
    private int amount;

    @Column(name = "event_time")
    private Instant eventTime;

    @Column(name = "dedupe_key", unique = true)
    private String dedupeKey;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    public UsageEvent() {
    }

    public UsageEvent(String id, String tenantId, String metric, int amount, Instant eventTime, String dedupeKey) {
        this.id = id;
        this.tenantId = tenantId;
        this.metric = metric;
        this.amount = amount;
        this.eventTime = eventTime;
        this.dedupeKey = dedupeKey;
        this.createdAt = Instant.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
// auto-generated minor edit 28951
// auto-generated minor edit 18720
// auto-generated minor edit 16089
// auto-generated minor edit 6395
// Auto-generated change #5
// Auto-generated change #10
// Auto-generated change #40
// minor edit 4375
// minor edit 15974
// minor edit 19784
// Auto-generated change #10
// Auto-generated change #18
// Auto-generated change #92
