package com.example.billing.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Map;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private String id;

    private String name;

    @Column(name = "pricing_rules", columnDefinition = "JSON")
    @Convert(converter = JsonMapConverter.class)
    private Map<String, Object> pricingRules;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    public Plan() {
    }

    public Plan(String id, String name, Map<String, Object> pricingRules) {
        this.id = id;
        this.name = name;
        this.pricingRules = pricingRules;
        this.createdAt = Instant.now();
    }

    // Getters and Setters
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

    public Map<String, Object> getPricingRules() {
        return pricingRules;
    }

    public void setPricingRules(Map<String, Object> pricingRules) {
        this.pricingRules = pricingRules;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
// auto-generated minor edit 32357
// auto-generated minor edit 26674
// auto-generated minor edit 32449
// auto-generated minor edit 10214
// Auto-generated change #6
// Auto-generated change #29
// Auto-generated change #58
// Auto-generated change #88
// minor edit 19061
// minor edit 17787
// minor edit 29985
// Auto-generated change #33
// Auto-generated change #84
// Auto-generated change #35
// Auto-generated change #50
// Auto-generated change #62
