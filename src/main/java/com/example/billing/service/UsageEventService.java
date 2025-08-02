package com.example.billing.service;

import com.example.billing.dto.UsageEventRequest;
import com.example.billing.entity.UsageEvent;
import com.example.billing.exception.ApiException;
import com.example.billing.repository.TenantRepository;
import com.example.billing.repository.UsageEventRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UsageEventService {

    private final UsageEventRepository usageEventRepository;
    private final TenantRepository tenantRepository;

    public UsageEventService(UsageEventRepository usageEventRepository, TenantRepository tenantRepository) {
        this.usageEventRepository = usageEventRepository;
        this.tenantRepository = tenantRepository;
    }

    public UsageEvent createUsageEvent(UsageEventRequest request) {
        // Validate tenant exists
        tenantRepository.findById(request.getTenantId())
                .orElseThrow(() -> new ApiException("nah gng find tenant: " + request.getTenantId()));

        // Validate metric (add allowed metrics as needed)
        if (!Arrays.asList("api_calls", "storage", "users").contains(request.getMetric())) {
            throw new ApiException("Invalid metric: " + request.getMetric());
        }

        // Check for duplicate
        if (usageEventRepository.existsByDedupeKey(request.getDedupeKey())) {
            return usageEventRepository.findByDedupeKey(request.getDedupeKey())
                    .orElseThrow(() -> new ApiException("Duplicate dedupeKey found"));
        }

        UsageEvent event = new UsageEvent();
        event.setTenantId(request.getTenantId());
        event.setMetric(request.getMetric());
        event.setAmount(request.getAmount());
        event.setEventTime(request.getEventTime());
        event.setDedupeKey(request.getDedupeKey());

        return usageEventRepository.save(event);
    }

    // public UsageEvent recordUsage(UsageEvent event) {
    // Optional<UsageEvent> existing =
    // usageEventRepository.findByDedupeKey(event.getDedupeKey());
    // if (existing.isPresent()) {
    // return existing.get(); // Return existing instead of saving duplicate
    // }
    // return usageEventRepository.save(event);
    // }

    public Optional<UsageEvent> getUsageEvent(String id) {
        return usageEventRepository.findById(id);
    }

    public List<UsageEvent> getAllUsageEvents() {
        return usageEventRepository.findAll();
    }

    public void deleteUsageEvent(String id) {
        usageEventRepository.deleteById(id);
    }
}
// auto-generated minor edit 8486
// auto-generated minor edit 25322
// auto-generated minor edit 27813
// auto-generated minor edit 12360
// auto-generated minor edit 6171
// auto-generated minor edit 3174
// Auto-generated change #69
// Auto-generated change #89
// minor edit 10856
// Auto-generated change #55
// Auto-generated change #56
// Auto-generated change #80
// Auto-generated change #8
