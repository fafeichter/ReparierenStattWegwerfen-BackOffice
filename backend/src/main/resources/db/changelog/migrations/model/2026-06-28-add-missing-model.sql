--liquibase formatted sql
--changeset fabian:2026-06-28-data-ipad-air-m3 context:model

-- Closes a gap in model_apple_silicon: id 18 was reused for an M3 Pro variant,
-- but the M3 chip configuration actually used in the iPad Air 11"/13" (M3, 2025)
-- — 8‑Core CPU (4 performance + 4 efficiency) with a 9‑Core GPU — never got
-- its own row. Source: Apple Tech Specs (support.apple.com/de-de/122241, /122242).
INSERT INTO model_apple_silicon (model_apple_silicon_id, name, name_short, number_cpu_efficiency_cores,
                                 number_cpu_performance_cores, number_cpu_super_cores, number_gpu_cores)
VALUES (18, 'M3 mit 8‑Core CPU und 9‑Core GPU', 'M3', 4, 4, 0, 9);

-- The entire iPad Air (M3, 2025) generation was missing from `model` — there
-- was a gap between iPad Air (M2, 2024-05) and iPad Air (M4, 2026-03).
INSERT INTO model (model_id, name, model_series_id, model_number, technical_specs_url, release_year,
                   display_size, display_size_exact, release_month)
VALUES (54, 'iPad Air 11" (M3)', 5, 'A3266', 'https://support.apple.com/de-de/122241', 2025, 11, 11.0, 3),
       (55, 'iPad Air 11" (M3, Wi‑Fi + Cellular)', 5, 'A3267', 'https://support.apple.com/de-de/122241', 2025, 11,
        11.0, 3),
       (56, 'iPad Air 13" (M3)', 5, 'A3268', 'https://support.apple.com/de-de/122242', 2025, 13, 13.0, 3),
       (57, 'iPad Air 13" (M3, Wi‑Fi + Cellular)', 5, 'A3269', 'https://support.apple.com/de-de/122242', 2025, 13,
        13.0, 3);

INSERT INTO model_available_apple_silicon (model_available_apple_silicon_id, model_id, model_apple_silicon_id)
VALUES (93, 54, 18),
       (94, 55, 18),
       (95, 56, 18),
       (96, 57, 18);

-- Same finish lineup as the M2/M4 Air generations: Space Grau, Polarstern, Blau, Violett
-- (Apple: Space Gray, Starlight, Blue, Purple).
INSERT INTO model_available_color (model_available_color_id, model_id, model_color_id)
VALUES (144, 54, 11),
       (145, 54, 12),
       (146, 54, 5),
       (147, 54, 2),
       (148, 55, 11),
       (149, 55, 12),
       (150, 55, 5),
       (151, 55, 2),
       (152, 56, 11),
       (153, 56, 12),
       (154, 56, 5),
       (155, 56, 2),
       (156, 57, 11),
       (157, 57, 12),
       (158, 57, 5),
       (159, 57, 2);

INSERT INTO model_available_feature (model_available_feature_id, model_id, model_feature_id)
VALUES (130, 54, 8),
       (131, 55, 7),
       (132, 56, 8),
       (133, 57, 7);

-- Capacity per Apple Tech Specs: 128GB / 256GB / 512GB / 1TB (model_storage ids 2-5).
INSERT INTO model_silicon_available_storage (model_silicon_available_storage_id,
                                             model_available_apple_silicon_id,
                                             model_storage_id)
VALUES (341, 93, 2),
       (342, 93, 3),
       (343, 93, 4),
       (344, 93, 5),
       (345, 94, 2),
       (346, 94, 3),
       (347, 94, 4),
       (348, 94, 5),
       (349, 95, 2),
       (350, 95, 3),
       (351, 95, 4),
       (352, 95, 5),
       (353, 96, 2),
       (354, 96, 3),
       (355, 96, 4),
       (356, 96, 5);

-- Fixed 8GB unified memory, no configurable tiers (per Apple Tech Specs: "8GB RAM").
INSERT INTO model_silicon_available_unified_memory (model_silicon_available_unified_memory_id,
                                                    model_available_apple_silicon_id,
                                                    model_apple_silicon_unified_memory_id)
VALUES (188, 93, 1),
       (189, 94, 1),
       (190, 95, 1),
       (191, 96, 1);

--changeset fabian:2026-06-28-data-ipad-air-m4-cellular context:model

-- iPad Air 11"/13" (M4, 2026) only got their Wi‑Fi-only rows (model_id 48/49) when
-- that generation was added — the Wi‑Fi+Cellular siblings (which every earlier Air
-- generation has, see 38/43, 39/46, 40/47) were never inserted. Closing that gap
-- here using the "global" (non‑China) cellular model numbers, consistent with how
-- the existing rows already pick the global number over the China-only variant.
-- Source: Apple Tech Specs (support.apple.com/de-de/126471, /126472) and Apple's
-- "Identify your iPad model" page (A3460 / A3462 = global Wi‑Fi+Cellular).
INSERT INTO model (model_id, name, model_series_id, model_number, technical_specs_url, release_year,
                   display_size, display_size_exact, release_month)
VALUES (58, 'iPad Air 11" (M4, Wi‑Fi + Cellular)', 5, 'A3460', 'https://support.apple.com/de-de/126471', 2026, 11,
        11.0, 3),
       (59, 'iPad Air 13" (M4, Wi‑Fi + Cellular)', 5, 'A3462', 'https://support.apple.com/de-de/126472', 2026, 13,
        13.0, 3);

-- Same silicon as the Wi‑Fi-only siblings (model_id 48/49): silicon id 23, M4 8‑Core
-- CPU / 9‑Core GPU.
INSERT INTO model_available_apple_silicon (model_available_apple_silicon_id, model_id, model_apple_silicon_id)
VALUES (97, 58, 23),
       (98, 59, 23);

-- Same four finishes as every other iPad Air generation.
INSERT INTO model_available_color (model_available_color_id, model_id, model_color_id)
VALUES (160, 58, 11),
       (161, 58, 12),
       (162, 58, 5),
       (163, 58, 2),
       (164, 59, 11),
       (165, 59, 12),
       (166, 59, 5),
       (167, 59, 2);

INSERT INTO model_available_feature (model_available_feature_id, model_id, model_feature_id)
VALUES (134, 58, 7),
       (135, 59, 7);

-- Capacity per Apple Tech Specs: 128GB / 256GB / 512GB / 1TB (model_storage ids 2-5),
-- identical to the Wi‑Fi-only siblings.
INSERT INTO model_silicon_available_storage (model_silicon_available_storage_id,
                                             model_available_apple_silicon_id,
                                             model_storage_id)
VALUES (357, 97, 2),
       (358, 97, 3),
       (359, 97, 4),
       (360, 97, 5),
       (361, 98, 2),
       (362, 98, 3),
       (363, 98, 4),
       (364, 98, 5);

-- 12GB unified memory, same as the Wi‑Fi-only siblings (model_apple_silicon_unified_memory id 2).
INSERT INTO model_silicon_available_unified_memory (model_silicon_available_unified_memory_id,
                                                    model_available_apple_silicon_id,
                                                    model_apple_silicon_unified_memory_id)
VALUES (192, 97, 2),
       (193, 98, 2);
