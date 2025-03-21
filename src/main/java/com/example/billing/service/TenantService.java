package com.example.billing.service;

import com.example.billing.entity.Tenant;
import com.example.billing.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Tenant createTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public Optional<Tenant> getTenant(String id) {
        return tenantRepository.findById(id);
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public void deleteTenant(String id) {
        tenantRepository.deleteById(id);
    }
}
// auto-generated minor edit 15451
// Auto-generated change #2
// Auto-generated change #3
// Auto-generated change #45
// minor edit 9409
// Auto-generated change #7
// Auto-generated change #78
// Auto-generated change #20
