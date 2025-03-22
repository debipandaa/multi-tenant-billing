package com.example.billing.entity;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "tenants")
public class Tenant {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private String id;

    private String name;
    @Column(length = 36)
    private String planId;
    private Instant createdAt = Instant.now();

    public Tenant() {
    }

    public Tenant(String id, String name, String planId, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.planId = planId;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", planId=" + planId +
                ", createdAt=" + createdAt +
                '}';
    }
}
// auto-generated minor edit 26913
// auto-generated minor edit 4111
// auto-generated minor edit 6023
// Auto-generated change #25
// Auto-generated change #26
// minor edit 21749
// minor edit 2455
// minor edit 7050
// minor edit 3798
// Auto-generated change #47
// Auto-generated change #63
// Auto-generated change #66
// Auto-generated change #28
// Auto-generated change #31
// Auto-generated change #91
