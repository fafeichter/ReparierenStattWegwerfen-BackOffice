--liquibase formatted sql

--changeset fabian:6-model-fix-m4-pro-max-14-silicon-split
DELETE
FROM model_silicon_available_unified_memory
WHERE model_available_apple_silicon_id IN (29, 30, 31, 32);
DELETE
FROM model_silicon_available_storage
WHERE model_available_apple_silicon_id IN (29, 30, 31, 32);
DELETE
FROM model_available_apple_silicon
WHERE model_available_apple_silicon_id IN (29, 30, 31, 32);

--changeset fabian:6-model-fix-unified-memory-options
DELETE
FROM model_silicon_available_unified_memory
WHERE model_silicon_available_unified_memory_id IN (184, 185, 73, 85, 111, 186);
INSERT INTO model_silicon_available_unified_memory (model_silicon_available_unified_memory_id,
                                                    model_available_apple_silicon_id,
                                                    model_apple_silicon_unified_memory_id)
VALUES (213, 4, 5),
       (214, 36, 7),
       (215, 19, 5);

--changeset fabian:6-model-fix-storage-options
DELETE
FROM model_silicon_available_storage
WHERE model_silicon_available_storage_id IN (215, 196, 209, 214, 343);

--changeset fabian:6-model-fix-m1-pro-8-core-cpu
UPDATE model_apple_silicon
SET number_cpu_efficiency_cores  = 2,
    number_cpu_performance_cores = 6
WHERE model_apple_silicon_id = 4;

--changeset fabian:6-model-fix-ipad-display-features
INSERT INTO model_available_feature (model_available_feature_id, model_id, model_feature_id)
VALUES
    -- iPad Pro M4/M5: True Tone, ProMotion
    (156, 41, 1),
    (157, 41, 2),
    (158, 44, 1),
    (159, 44, 2),
    (160, 42, 1),
    (161, 42, 2),
    (162, 45, 1),
    (163, 45, 2),
    (164, 50, 1),
    (165, 50, 2),
    (166, 51, 1),
    (167, 51, 2),
    (168, 52, 1),
    (169, 52, 2),
    (170, 53, 1),
    (171, 53, 2),
    -- iPad Pro 11" 3./4. Gen: True Tone, ProMotion, Liquid Retina
    (172, 34, 1),
    (173, 34, 2),
    (174, 34, 5),
    (175, 35, 1),
    (176, 35, 2),
    (177, 35, 5),
    (178, 36, 1),
    (179, 36, 2),
    (180, 36, 5),
    (181, 37, 1),
    (182, 37, 2),
    (183, 37, 5),
    -- iPad Pro 12,9" 5./6. Gen: True Tone, ProMotion, Liquid Retina XDR
    (184, 32, 1),
    (185, 32, 2),
    (186, 32, 4),
    (187, 33, 1),
    (188, 33, 2),
    (189, 33, 4),
    (190, 30, 1),
    (191, 30, 2),
    (192, 30, 4),
    (193, 31, 1),
    (194, 31, 2),
    (195, 31, 4),
    -- iPad Air (all generations): True Tone, Liquid Retina
    (196, 38, 1),
    (197, 38, 5),
    (198, 43, 1),
    (199, 43, 5),
    (200, 39, 1),
    (201, 39, 5),
    (202, 46, 1),
    (203, 46, 5),
    (204, 40, 1),
    (205, 40, 5),
    (206, 47, 1),
    (207, 47, 5),
    (208, 54, 1),
    (209, 54, 5),
    (210, 55, 1),
    (211, 55, 5),
    (212, 56, 1),
    (213, 56, 5),
    (214, 57, 1),
    (215, 57, 5),
    (216, 48, 1),
    (217, 48, 5),
    (218, 58, 1),
    (219, 58, 5),
    (220, 49, 1),
    (221, 49, 5),
    (222, 59, 1),
    (223, 59, 5),
    -- MacBook Neo: Liquid Retina
    (224, 29, 5);

--changeset fabian:6-model-fix-technical-specs-url-locale
UPDATE model
SET technical_specs_url = REPLACE(technical_specs_url, '/de-de/', '/de-at/')
WHERE technical_specs_url LIKE 'https://support.apple.com/de-de/%';

--changeset fabian:6-model-fix-macbook-air-13-m5-8-core-gpu-memory
DELETE
FROM model_silicon_available_unified_memory
WHERE model_silicon_available_unified_memory_id IN (99, 100)
  AND model_available_apple_silicon_id = 43;