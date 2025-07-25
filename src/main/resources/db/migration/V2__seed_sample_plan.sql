INSERT INTO plans (id, name, pricing_rules, created_at)
VALUES (
    UUID(),
    'Sample Tiered Plan',
    '{
      "type": "tiered",
      "tiers": [
        {"upto": 1000, "price": 0},
        {"upto": 5000, "price": 0.01},
        {"upto": null, "price": 0.02}
      ]
    }',
    NOW()
);// auto-generated minor edit 4786
// auto-generated minor edit 7364
// auto-generated minor edit 7515
// auto-generated minor edit 6030
// auto-generated minor edit 6201
// auto-generated minor edit 14965
// Auto-generated change #13
// Auto-generated change #64
// Auto-generated change #70
// Auto-generated change #71
// minor edit 31022
// Auto-generated change #52
// Auto-generated change #82
// Auto-generated change #30
