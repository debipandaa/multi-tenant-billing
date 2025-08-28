package com.example.billing.repository;

import com.example.billing.entity.UsageEvent;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageEventRepository extends JpaRepository<UsageEvent, String> {

    Optional<UsageEvent> findByDedupeKey(String dedupeKey);

    boolean existsByDedupeKey(String dedupeKey);
}
// auto-generated minor edit 2458
// auto-generated minor edit 9778
// auto-generated minor edit 25271
// auto-generated minor edit 10558
// auto-generated minor edit 12223
// auto-generated minor edit 14228
// auto-generated minor edit 55
// Auto-generated change #56
// minor edit 29990
// minor edit 27552
// minor edit 24983
// minor edit 12533
// minor edit 9528
// Auto-generated change #87
