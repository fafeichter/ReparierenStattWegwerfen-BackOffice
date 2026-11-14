--liquibase formatted sql

--changeset fabian:3-model-series-add-sort-order-column
ALTER TABLE model_series
    ADD sort_order int AFTER name;

--changeset fabian:3-model-series-assign-sort-order
-- MacBook Air
UPDATE model_series
SET sort_order = 1
WHERE model_series_id = 1;

-- MacBook Pro
UPDATE model_series
SET sort_order = 2
WHERE model_series_id = 2;

-- MacBook Neo
UPDATE model_series
SET sort_order = 3
WHERE model_series_id = 3;

-- iPad Air
UPDATE model_series
SET sort_order = 99
WHERE model_series_id = 5;

-- iPad Pro
UPDATE model_series
SET sort_order = 100
WHERE model_series_id = 4;
--rollback UPDATE model_series SET sort_order = NULL;

--changeset fabian:3-model-series-set-sort-order-not-null-unique
ALTER TABLE model_series
    MODIFY sort_order int NOT NULL,
    ADD UNIQUE KEY uq_model_series_sort (sort_order);

--changeset fabian:3-model-apple-silicon-generation-add-sort-order-column
ALTER TABLE model_apple_silicon_generation
    ADD sort_order int AFTER name;

--changeset fabian:3-model-apple-silicon-generation-assign-sort-order
-- M1
UPDATE model_apple_silicon_generation
SET sort_order = 1
WHERE model_apple_silicon_generation_id = 1;

-- M2
UPDATE model_apple_silicon_generation
SET sort_order = 2
WHERE model_apple_silicon_generation_id = 2;

-- M3
UPDATE model_apple_silicon_generation
SET sort_order = 3
WHERE model_apple_silicon_generation_id = 3;

-- M4
UPDATE model_apple_silicon_generation
SET sort_order = 4
WHERE model_apple_silicon_generation_id = 4;

-- M5
UPDATE model_apple_silicon_generation
SET sort_order = 5
WHERE model_apple_silicon_generation_id = 5;

-- A18
UPDATE model_apple_silicon_generation
SET sort_order = 6
WHERE model_apple_silicon_generation_id = 6;

-- M6
UPDATE model_apple_silicon_generation
SET sort_order = 7
WHERE model_apple_silicon_generation_id = 7;
--rollback UPDATE model_apple_silicon_generation SET sort_order = NULL;

--changeset fabian:3-model-apple-silicon-generation-set-sort-order-not-null-unique
ALTER TABLE model_apple_silicon_generation
    MODIFY sort_order int NOT NULL,
    ADD UNIQUE KEY uq_model_apple_silicon_generation_sort (sort_order);

--changeset fabian:3-model-apple-silicon-variant-add-sort-order-column
ALTER TABLE model_apple_silicon_variant
    ADD sort_order int AFTER name;

--changeset fabian:3-model-apple-silicon-variant-assign-sort-order
-- M1
UPDATE model_apple_silicon_variant
SET sort_order = 1
WHERE model_apple_silicon_variant_id = 2;

-- M1 Pro
UPDATE model_apple_silicon_variant
SET sort_order = 2
WHERE model_apple_silicon_variant_id = 3;

-- M1 Max
UPDATE model_apple_silicon_variant
SET sort_order = 3
WHERE model_apple_silicon_variant_id = 4;

-- M2
UPDATE model_apple_silicon_variant
SET sort_order = 4
WHERE model_apple_silicon_variant_id = 5;

-- M2 Pro
UPDATE model_apple_silicon_variant
SET sort_order = 5
WHERE model_apple_silicon_variant_id = 6;

-- M2 Max
UPDATE model_apple_silicon_variant
SET sort_order = 6
WHERE model_apple_silicon_variant_id = 7;

-- M3
UPDATE model_apple_silicon_variant
SET sort_order = 7
WHERE model_apple_silicon_variant_id = 8;

-- M3 Pro
UPDATE model_apple_silicon_variant
SET sort_order = 8
WHERE model_apple_silicon_variant_id = 9;

-- M3 Max
UPDATE model_apple_silicon_variant
SET sort_order = 9
WHERE model_apple_silicon_variant_id = 10;

-- M4
UPDATE model_apple_silicon_variant
SET sort_order = 10
WHERE model_apple_silicon_variant_id = 11;

-- M4 Pro
UPDATE model_apple_silicon_variant
SET sort_order = 11
WHERE model_apple_silicon_variant_id = 12;

-- M4 Max
UPDATE model_apple_silicon_variant
SET sort_order = 12
WHERE model_apple_silicon_variant_id = 13;

-- M5
UPDATE model_apple_silicon_variant
SET sort_order = 13
WHERE model_apple_silicon_variant_id = 14;

-- M5 Pro
UPDATE model_apple_silicon_variant
SET sort_order = 14
WHERE model_apple_silicon_variant_id = 15;

-- M5 Max
UPDATE model_apple_silicon_variant
SET sort_order = 15
WHERE model_apple_silicon_variant_id = 16;

-- A18 Pro
UPDATE model_apple_silicon_variant
SET sort_order = 16
WHERE model_apple_silicon_variant_id = 1;

-- M6
UPDATE model_apple_silicon_variant
SET sort_order = 17
WHERE model_apple_silicon_variant_id = 17;
--rollback UPDATE model_apple_silicon_variant SET sort_order = NULL;

--changeset fabian:3-model-apple-silicon-variant-set-sort-order-not-null-unique
ALTER TABLE model_apple_silicon_variant
    MODIFY sort_order int NOT NULL,
    ADD UNIQUE KEY uq_model_apple_silicon_variant_sort (sort_order);

--changeset fabian:3-model-apple-silicon-add-sort-order-column
ALTER TABLE model_apple_silicon
    ADD sort_order int AFTER name;

--changeset fabian:3-model-apple-silicon-assign-sort-order
-- M1 mit 8-Core CPU und 7-Core GPU
UPDATE model_apple_silicon
SET sort_order = 1
WHERE model_apple_silicon_id = 2;

-- M1 mit 8-Core CPU und 8-Core GPU
UPDATE model_apple_silicon
SET sort_order = 2
WHERE model_apple_silicon_id = 3;

-- M1 Pro mit 8-Core CPU und 14-Core GPU
UPDATE model_apple_silicon
SET sort_order = 3
WHERE model_apple_silicon_id = 4;

-- M1 Pro mit 10-Core CPU und 14-Core GPU
UPDATE model_apple_silicon
SET sort_order = 4
WHERE model_apple_silicon_id = 5;

-- M1 Pro mit 10-Core CPU und 16-Core GPU
UPDATE model_apple_silicon
SET sort_order = 5
WHERE model_apple_silicon_id = 6;

-- M1 Max mit 10-Core CPU und 24-Core GPU
UPDATE model_apple_silicon
SET sort_order = 6
WHERE model_apple_silicon_id = 7;

-- M1 Max mit 10-Core CPU und 32-Core GPU
UPDATE model_apple_silicon
SET sort_order = 7
WHERE model_apple_silicon_id = 8;

-- M2 mit 8-Core CPU und 8-Core GPU
UPDATE model_apple_silicon
SET sort_order = 8
WHERE model_apple_silicon_id = 9;

-- M2 mit 8-Core CPU und 9-Core GPU
UPDATE model_apple_silicon
SET sort_order = 9
WHERE model_apple_silicon_id = 10;

-- M2 mit 8-Core CPU und 10-Core GPU
UPDATE model_apple_silicon
SET sort_order = 10
WHERE model_apple_silicon_id = 11;

-- M2 Pro mit 10-Core CPU und 16-Core GPU
UPDATE model_apple_silicon
SET sort_order = 11
WHERE model_apple_silicon_id = 12;

-- M2 Pro mit 12-Core CPU und 19-Core GPU
UPDATE model_apple_silicon
SET sort_order = 12
WHERE model_apple_silicon_id = 13;

-- M2 Max mit 12-Core CPU und 30-Core GPU
UPDATE model_apple_silicon
SET sort_order = 13
WHERE model_apple_silicon_id = 14;

-- M2 Max mit 12-Core CPU und 38-Core GPU
UPDATE model_apple_silicon
SET sort_order = 14
WHERE model_apple_silicon_id = 15;

-- M3 mit 8-Core CPU und 8-Core GPU
UPDATE model_apple_silicon
SET sort_order = 15
WHERE model_apple_silicon_id = 16;

-- M3 mit 8-Core CPU und 9-Core GPU
UPDATE model_apple_silicon
SET sort_order = 16
WHERE model_apple_silicon_id = 18;

-- M3 mit 8-Core CPU und 10-Core GPU
UPDATE model_apple_silicon
SET sort_order = 17
WHERE model_apple_silicon_id = 17;

-- M3 Pro mit 11-Core CPU und 14-Core GPU
UPDATE model_apple_silicon
SET sort_order = 18
WHERE model_apple_silicon_id = 19;

-- M3 Pro mit 12-Core CPU und 18-Core GPU
UPDATE model_apple_silicon
SET sort_order = 19
WHERE model_apple_silicon_id = 20;

-- M3 Max mit 14-Core CPU und 30-Core GPU
UPDATE model_apple_silicon
SET sort_order = 20
WHERE model_apple_silicon_id = 21;

-- M3 Max mit 16-Core CPU und 40-Core GPU
UPDATE model_apple_silicon
SET sort_order = 21
WHERE model_apple_silicon_id = 22;

-- M4 mit 8-Core CPU und 9-Core GPU
UPDATE model_apple_silicon
SET sort_order = 22
WHERE model_apple_silicon_id = 23;

-- M4 mit 9-Core CPU und 10-Core GPU
UPDATE model_apple_silicon
SET sort_order = 23
WHERE model_apple_silicon_id = 24;

-- M4 mit 10-Core CPU und 8-Core GPU
UPDATE model_apple_silicon
SET sort_order = 24
WHERE model_apple_silicon_id = 25;

-- M4 mit 10-Core CPU und 10-Core GPU
UPDATE model_apple_silicon
SET sort_order = 25
WHERE model_apple_silicon_id = 26;

-- M4 Pro mit 12-Core CPU und 16-Core GPU
UPDATE model_apple_silicon
SET sort_order = 26
WHERE model_apple_silicon_id = 27;

-- M4 Pro mit 14-Core CPU und 20-Core GPU
UPDATE model_apple_silicon
SET sort_order = 27
WHERE model_apple_silicon_id = 28;

-- M4 Max mit 14-Core CPU und 32-Core GPU
UPDATE model_apple_silicon
SET sort_order = 28
WHERE model_apple_silicon_id = 29;

-- M4 Max mit 16-Core CPU und 40-Core GPU
UPDATE model_apple_silicon
SET sort_order = 29
WHERE model_apple_silicon_id = 30;

-- M5 mit 9-Core CPU und 10-Core GPU
UPDATE model_apple_silicon
SET sort_order = 30
WHERE model_apple_silicon_id = 31;

-- M5 mit 10-Core CPU und 8-Core GPU
UPDATE model_apple_silicon
SET sort_order = 31
WHERE model_apple_silicon_id = 32;

-- M5 mit 10-Core CPU und 10-Core GPU
UPDATE model_apple_silicon
SET sort_order = 32
WHERE model_apple_silicon_id = 33;

-- M5 Pro mit 15-Core CPU und 16-Core GPU
UPDATE model_apple_silicon
SET sort_order = 33
WHERE model_apple_silicon_id = 34;

-- M5 Pro mit 18-Core CPU und 20-Core GPU
UPDATE model_apple_silicon
SET sort_order = 34
WHERE model_apple_silicon_id = 35;

-- M5 Max mit 18-Core CPU und 32-Core GPU
UPDATE model_apple_silicon
SET sort_order = 35
WHERE model_apple_silicon_id = 36;

-- M5 Max mit 18-Core CPU und 40-Core GPU
UPDATE model_apple_silicon
SET sort_order = 36
WHERE model_apple_silicon_id = 37;

-- A18 Pro mit 6-Core CPU und 5-Core GPU
UPDATE model_apple_silicon
SET sort_order = 37
WHERE model_apple_silicon_id = 1;

-- M6 mit 12-Core CPU und 12-Core GPU
UPDATE model_apple_silicon
SET sort_order = 38
WHERE model_apple_silicon_id = 38;
--rollback UPDATE model_apple_silicon SET sort_order = NULL;

--changeset fabian:3-model-apple-silicon-set-sort-order-not-null-unique
ALTER TABLE model_apple_silicon
    MODIFY sort_order int NOT NULL,
    ADD UNIQUE KEY uq_model_apple_silicon_sort (sort_order);

--changeset fabian:3-model-apple-silicon-unified-memory-add-sort-order-column
ALTER TABLE model_apple_silicon_unified_memory
    ADD sort_order int AFTER unit;

--changeset fabian:3-model-apple-silicon-unified-memory-assign-sort-order
-- 8 GB
UPDATE model_apple_silicon_unified_memory
SET sort_order = 1
WHERE model_apple_silicon_unified_memory_id = 1;

-- 12 GB
UPDATE model_apple_silicon_unified_memory
SET sort_order = 2
WHERE model_apple_silicon_unified_memory_id = 2;

-- 16 GB
UPDATE model_apple_silicon_unified_memory
SET sort_order = 3
WHERE model_apple_silicon_unified_memory_id = 3;

-- 18 GB
UPDATE model_apple_silicon_unified_memory
SET sort_order = 4
WHERE model_apple_silicon_unified_memory_id = 4;

-- 24 GB
UPDATE model_apple_silicon_unified_memory
SET sort_order = 5
WHERE model_apple_silicon_unified_memory_id = 5;

-- 32 GB
UPDATE model_apple_silicon_unified_memory
SET sort_order = 6
WHERE model_apple_silicon_unified_memory_id = 6;

-- 36 GB
UPDATE model_apple_silicon_unified_memory
SET sort_order = 7
WHERE model_apple_silicon_unified_memory_id = 7;

-- 48 GB
UPDATE model_apple_silicon_unified_memory
SET sort_order = 8
WHERE model_apple_silicon_unified_memory_id = 8;

-- 64 GB
UPDATE model_apple_silicon_unified_memory
SET sort_order = 9
WHERE model_apple_silicon_unified_memory_id = 9;

-- 96 GB
UPDATE model_apple_silicon_unified_memory
SET sort_order = 10
WHERE model_apple_silicon_unified_memory_id = 10;

-- 128 GB
UPDATE model_apple_silicon_unified_memory
SET sort_order = 11
WHERE model_apple_silicon_unified_memory_id = 11;
--rollback UPDATE model_apple_silicon_unified_memory SET sort_order = NULL;

--changeset fabian:3-model-apple-silicon-unified-memory-set-sort-order-not-null-unique
ALTER TABLE model_apple_silicon_unified_memory
    MODIFY sort_order int NOT NULL,
    ADD UNIQUE KEY uq_model_apple_silicon_unified_memory_sort (sort_order);

--changeset fabian:3-model-storage-add-sort-order-column
ALTER TABLE model_storage
    ADD sort_order int AFTER unit;

--changeset fabian:3-model-storage-assign-sort-order
-- 64 GB
UPDATE model_storage
SET sort_order = 1
WHERE model_storage_id = 1;

-- 128 GB
UPDATE model_storage
SET sort_order = 2
WHERE model_storage_id = 2;

-- 256 GB
UPDATE model_storage
SET sort_order = 3
WHERE model_storage_id = 3;

-- 512 GB
UPDATE model_storage
SET sort_order = 4
WHERE model_storage_id = 4;

-- 1 TB
UPDATE model_storage
SET sort_order = 5
WHERE model_storage_id = 5;

-- 2 TB
UPDATE model_storage
SET sort_order = 6
WHERE model_storage_id = 6;

-- 4 TB
UPDATE model_storage
SET sort_order = 7
WHERE model_storage_id = 7;

-- 8 TB
UPDATE model_storage
SET sort_order = 8
WHERE model_storage_id = 8;

-- 16 TB
UPDATE model_storage
SET sort_order = 9
WHERE model_storage_id = 9;
--rollback UPDATE model_storage SET sort_order = NULL;

--changeset fabian:3-model-storage-set-sort-order-not-null-unique
ALTER TABLE model_storage
    MODIFY sort_order int NOT NULL,
    ADD UNIQUE KEY uq_model_storage_sort (sort_order);

--changeset fabian:3-model-color-add-sort-order-column
ALTER TABLE model_color
    ADD sort_order int AFTER name;

--changeset fabian:3-model-color-assign-sort-order
-- Himmelblau
UPDATE model_color
SET sort_order = 1
WHERE model_color_id = 7;

-- Silber
UPDATE model_color
SET sort_order = 2
WHERE model_color_id = 1;

-- Space Grau
UPDATE model_color
SET sort_order = 3
WHERE model_color_id = 2;

-- Gold
UPDATE model_color
SET sort_order = 4
WHERE model_color_id = 3;

-- Polarstern
UPDATE model_color
SET sort_order = 5
WHERE model_color_id = 5;

-- Rosé
UPDATE model_color
SET sort_order = 6
WHERE model_color_id = 13;

-- Mitternacht
UPDATE model_color
SET sort_order = 7
WHERE model_color_id = 4;

-- Space Schwarz
UPDATE model_color
SET sort_order = 8
WHERE model_color_id = 6;

-- Violett
UPDATE model_color
SET sort_order = 9
WHERE model_color_id = 12;

-- Blau
UPDATE model_color
SET sort_order = 10
WHERE model_color_id = 11;

-- Rosa
UPDATE model_color
SET sort_order = 11
WHERE model_color_id = 8;

-- Zitrus
UPDATE model_color
SET sort_order = 12
WHERE model_color_id = 9;

-- Indigo
UPDATE model_color
SET sort_order = 13
WHERE model_color_id = 10;
--rollback UPDATE model_color SET sort_order = NULL;

--changeset fabian:3-model-color-set-sort-order-not-null-unique
ALTER TABLE model_color
    MODIFY sort_order int NOT NULL,
    ADD UNIQUE KEY uq_model_color_sort (sort_order);

--changeset fabian:3-model-feature-category-add-sort-order-column
ALTER TABLE model_feature_category
    ADD sort_order int AFTER value;

--changeset fabian:3-model-feature-category-assign-sort-order
-- DISPLAY
UPDATE model_feature_category
SET sort_order = 1
WHERE model_feature_category_id = 1;

-- BATTERY
UPDATE model_feature_category
SET sort_order = 2
WHERE model_feature_category_id = 2;

-- DEVICE
UPDATE model_feature_category
SET sort_order = 3
WHERE model_feature_category_id = 3;
--rollback UPDATE model_feature_category SET sort_order = NULL;

--changeset fabian:3-model-feature-category-set-sort-order-not-null-unique
ALTER TABLE model_feature_category
    MODIFY sort_order int NOT NULL,
    ADD UNIQUE KEY uq_model_feature_category_sort (sort_order);

--changeset fabian:3-model-feature-add-sort-order-column
ALTER TABLE model_feature
    ADD sort_order int AFTER name;

--changeset fabian:3-model-feature-assign-sort-order
-- Wi-Fi
UPDATE model_feature
SET sort_order = 9
WHERE model_feature_id = 8;

-- Wi-Fi + Cellular
UPDATE model_feature
SET sort_order = 10
WHERE model_feature_id = 7;

-- Ultra Retina XDR Display
UPDATE model_feature
SET sort_order = 11
WHERE model_feature_id = 9;

-- True Tone
UPDATE model_feature
SET sort_order = 99
WHERE model_feature_id = 1;

-- ProMotion bis 120 Hz
UPDATE model_feature
SET sort_order = 100
WHERE model_feature_id = 2;

-- Liquid Retina Display
UPDATE model_feature
SET sort_order = 101
WHERE model_feature_id = 5;

-- Liquid Retina XDR Display
UPDATE model_feature
SET sort_order = 102
WHERE model_feature_id = 4;

-- PROLED XDR Display
UPDATE model_feature
SET sort_order = 103
WHERE model_feature_id = 10;

-- Schnelles Aufladen
UPDATE model_feature
SET sort_order = 999
WHERE model_feature_id = 3;

-- Touch Bar
UPDATE model_feature
SET sort_order = 1000
WHERE model_feature_id = 6;
--rollback UPDATE model_feature SET sort_order = NULL;

--changeset fabian:3-model-feature-set-sort-order-not-null-unique
ALTER TABLE model_feature
    MODIFY sort_order int NOT NULL,
    ADD UNIQUE KEY uq_model_feature_sort (sort_order);