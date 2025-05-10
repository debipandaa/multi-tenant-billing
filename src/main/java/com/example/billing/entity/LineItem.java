package com.example.billing.entity;

import java.math.BigDecimal;

public class LineItem {
    private String description;
    private long quantity;
    private BigDecimal unitPrice;
    private BigDecimal amount;

    public LineItem() {
    }

    public LineItem(String description, long quantity, BigDecimal unitPrice, BigDecimal amount) {
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    // Getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
// auto-generated minor edit 4623
// auto-generated minor edit 11502
// auto-generated minor edit 14347
// auto-generated minor edit 28191
// auto-generated minor edit 21497
// auto-generated minor edit 28625
// auto-generated minor edit 14661
// auto-generated minor edit 15501
// auto-generated minor edit 16453
// auto-generated minor edit 16414
// auto-generated minor edit 24009
// Auto-generated change #96
// minor edit 19736
// minor edit 29631
// Auto-generated change #46
// Auto-generated change #64
// Auto-generated change #70
// Auto-generated change #12
