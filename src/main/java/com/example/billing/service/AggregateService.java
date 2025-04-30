package com.example.billing.service;

import com.example.billing.entity.Aggregate;
import com.example.billing.repository.AggregateRepository;
import com.example.billing.repository.UsageEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AggregateService {

    private final JdbcTemplate jdbcTemplate;
    private final AggregateRepository aggregateRepository;
    // private final UsageEventRepository usageEventRepository;

    public AggregateService(JdbcTemplate jdbcTemplate,
            AggregateRepository aggregateRepository,
            UsageEventRepository usageEventRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.aggregateRepository = aggregateRepository;
        // this.usageEventRepository = usageEventRepository;
    }

    /**
     * Save a single aggregate manually (not used in batch aggregation).
     */
    public Aggregate saveAggregate(Aggregate aggregate) {
        return aggregateRepository.save(aggregate);
    }

    /**
     * Fetch all current aggregates.
     */
    public java.util.List<Aggregate> getAllAggregates() {
        return aggregateRepository.findAll();
    }

    /**
     * Aggregate all usage events by tenantId + metric + month and save totals.
     * Uses MySQL UPSERT to ensure idempotency.
     */
    @Transactional
    public void aggregateUsage() {
        String sql = """
                    INSERT INTO aggregates (tenant_id, metric, period_start, period_end, usage_count)
                    SELECT
                        tenant_id,
                        metric,
                        DATE_FORMAT(event_time, '%Y-%m-01') AS period_start,
                        MAX(LAST_DAY(event_time)) AS period_end,
                        SUM(amount) AS usage_count
                    FROM usage_events
                    GROUP BY tenant_id, metric, DATE_FORMAT(event_time, '%Y-%m-01')
                    ON DUPLICATE KEY UPDATE
                        usage_count = VALUES(usage_count),
                        period_end = VALUES(period_end)
                """;

        jdbcTemplate.update(sql);
    }

}
// auto-generated minor edit 4090
// auto-generated minor edit 26656
// auto-generated minor edit 19080
// auto-generated minor edit 476
// auto-generated minor edit 21939
// Auto-generated change #9
// Auto-generated change #48
// minor edit 30002
// minor edit 26592
// Auto-generated change #4
// Auto-generated change #48
// Auto-generated change #54
// Auto-generated change #95
// Auto-generated change #57
