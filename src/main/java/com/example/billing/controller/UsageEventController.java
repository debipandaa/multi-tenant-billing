package com.example.billing.controller;

import com.example.billing.dto.UsageEventRequest;
import com.example.billing.entity.UsageEvent;
import com.example.billing.service.UsageEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usage-events")
public class UsageEventController {

    private final UsageEventService usageEventService;

    public UsageEventController(UsageEventService usageEventService) {
        this.usageEventService = usageEventService;
    }

    @PostMapping
    public ResponseEntity<UsageEvent> recordUsage(@RequestBody UsageEventRequest request) {
        UsageEvent saved = usageEventService.createUsageEvent(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsageEvent> getUsageEvent(@PathVariable String id) {
        Optional<UsageEvent> event = usageEventService.getUsageEvent(id);
        return event.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UsageEvent>> getAllUsageEvents() {
        return ResponseEntity.ok(usageEventService.getAllUsageEvents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsageEvent(@PathVariable String id) {
        usageEventService.deleteUsageEvent(id);
        return ResponseEntity.noContent().build();
    }
}
// auto-generated minor edit 7239
// auto-generated minor edit 963
// auto-generated minor edit 16105
// auto-generated minor edit 677
// auto-generated minor edit 4943
// Auto-generated change #32
// Auto-generated change #42
// minor edit 17438
// minor edit 27555
// minor edit 3260
// minor edit 25936
// Auto-generated change #53
// Auto-generated change #57
// Auto-generated change #61
// Auto-generated change #89
