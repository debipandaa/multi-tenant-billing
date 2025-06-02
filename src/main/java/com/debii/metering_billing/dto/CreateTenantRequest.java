package com.debii.metering_billing.dto;

import java.util.UUID;

// This record represents the JSON body for a POST request to create a tenant.
public record CreateTenantRequest(
                String name,
                UUID planId) {
}