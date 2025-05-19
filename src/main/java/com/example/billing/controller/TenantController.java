package com.example.billing.controller;

import com.example.billing.entity.Tenant;
import com.example.billing.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) {
        Tenant saved = tenantService.createTenant(tenant);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenant(@PathVariable String id) {
        return tenantService.getTenant(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Tenant>> getAllTenants() {
        return ResponseEntity.ok(tenantService.getAllTenants());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable String id) {

        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }
}
// auto-generated minor edit 18396
// auto-generated minor edit 28912
// auto-generated minor edit 4700
// Auto-generated change #50
// minor edit 14704
// Auto-generated change #62
// Auto-generated change #34
