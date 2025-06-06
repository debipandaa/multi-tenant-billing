package com.debii.metering_billing.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant;
import java.util.UUID;

@Table("tenants") // Maps this class to the "tenants" table in PostgreSQL
public class Tenant {
    @Id
    private UUID id;

    private String name;
    private UUID planId;
    private Instant createdAt = Instant.now();

    public Tenant() {
    }

    public Tenant(UUID id, String name, UUID planId, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.planId = planId;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getPlanId() {
        return planId;
    }

    public void setPlanId(UUID planId) {
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