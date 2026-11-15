--liquibase formatted sql

--changeset fabian:5-model-delete-proled-xdr-placeholder-models
DELETE
FROM model_silicon_available_unified_memory
WHERE model_available_apple_silicon_id BETWEEN 100 AND 106;

DELETE
FROM model_silicon_available_storage
WHERE model_available_apple_silicon_id BETWEEN 100 AND 106;

DELETE
FROM model_available_apple_silicon
WHERE model_id IN (61, 62);

DELETE
FROM model_available_color
WHERE model_id IN (61, 62);

DELETE
FROM model_available_feature
WHERE model_id IN (61, 62);

DELETE
FROM model
WHERE model_id IN (61, 62);
--rollback INSERT INTO model (model_id, name, model_series_id, model_number, technical_specs_url, release_year, display_size, display_size_exact, release_month) VALUES (61, 'MacBook Pro (14", PROLED XDR, 2026)', 2, 'A3601', 'https://www.apple.com/de/macbook-pro/proled-xdr-display/specs/', 2026, 14, 14.2, 11), (62, 'MacBook Pro (16", PROLED XDR, 2026)', 2, 'A3603', 'https://www.apple.com/de/macbook-pro/proled-xdr-display/specs/', 2026, 16, 16.2, 11);
--rollback INSERT INTO model_available_apple_silicon (model_available_apple_silicon_id, model_id, model_apple_silicon_id) VALUES (100, 61, 34), (101, 61, 35), (102, 61, 36), (103, 61, 37), (104, 62, 35), (105, 62, 36), (106, 62, 37);
--rollback INSERT INTO model_silicon_available_storage (model_silicon_available_storage_id, model_available_apple_silicon_id, model_storage_id) VALUES (371, 100, 5), (372, 100, 6), (373, 100, 7), (374, 100, 8), (375, 100, 9), (376, 101, 5), (377, 101, 6), (378, 101, 7), (379, 101, 8), (380, 101, 9), (381, 102, 5), (382, 102, 6), (383, 102, 7), (384, 102, 8), (385, 102, 9), (386, 103, 5), (387, 103, 6), (388, 103, 7), (389, 103, 8), (390, 103, 9), (391, 104, 5), (392, 104, 6), (393, 104, 7), (394, 104, 8), (395, 104, 9), (396, 105, 5), (397, 105, 6), (398, 105, 7), (399, 105, 8), (400, 105, 9), (401, 106, 5), (402, 106, 6), (403, 106, 7), (404, 106, 8), (405, 106, 9);
--rollback INSERT INTO model_silicon_available_unified_memory (model_silicon_available_unified_memory_id, model_available_apple_silicon_id, model_apple_silicon_unified_memory_id) VALUES (197, 100, 5), (198, 100, 8), (199, 101, 5), (200, 101, 8), (201, 101, 9), (202, 102, 7), (203, 103, 8), (204, 103, 9), (205, 103, 11), (206, 104, 5), (207, 104, 8), (208, 104, 9), (209, 105, 7), (210, 106, 8), (211, 106, 9), (212, 106, 11);
--rollback INSERT INTO model_available_color (model_available_color_id, model_id, model_color_id) VALUES (170, 61, 1), (171, 61, 6), (172, 62, 1), (173, 62, 6);
--rollback INSERT INTO model_available_feature (model_available_feature_id, model_id, model_feature_id) VALUES (140, 61, 1), (141, 61, 2), (142, 61, 3), (143, 61, 10), (144, 62, 1), (145, 62, 2), (146, 62, 3), (147, 62, 10);

--changeset fabian:5-model-insert-proled-xdr-models
INSERT INTO model (model_id, name, model_series_id, model_number, technical_specs_url, release_year,
                   display_size, display_size_exact, release_month)
VALUES (61, 'MacBook Pro (14", PROLED XDR, M5 Pro)', 2, 'A3468', 'https://support.apple.com/de-at/148301', 2026, 14,
        14.2, 11),
       (62, 'MacBook Pro (14", PROLED XDR, M5 Max)', 2, 'A3375', 'https://support.apple.com/de-at/148301', 2026, 14,
        14.2, 11),
       (63, 'MacBook Pro (16", PROLED XDR, M5 Pro)', 2, 'A3469', 'https://support.apple.com/de-at/148302', 2026, 16,
        16.3, 11),
       (64, 'MacBook Pro (16", PROLED XDR, M5 Max)', 2, 'A3376', 'https://support.apple.com/de-at/148302', 2026, 16,
        16.3, 11);

INSERT INTO model_available_apple_silicon (model_available_apple_silicon_id, model_id, model_apple_silicon_id)
VALUES (100, 61, 34),
       (101, 61, 35),
       (102, 62, 36),
       (103, 62, 37),
       (104, 63, 35),
       (105, 64, 36),
       (106, 64, 37);

INSERT INTO model_silicon_available_storage (model_silicon_available_storage_id,
                                             model_available_apple_silicon_id,
                                             model_storage_id)
VALUES (371, 100, 5),
       (372, 100, 6),
       (373, 100, 7),
       (374, 101, 5),
       (375, 101, 6),
       (376, 101, 7),
       (377, 102, 6),
       (378, 102, 7),
       (379, 102, 8),
       (380, 102, 9),
       (381, 103, 6),
       (382, 103, 7),
       (383, 103, 8),
       (384, 103, 9),
       (385, 104, 5),
       (386, 104, 6),
       (387, 104, 7),
       (388, 105, 6),
       (389, 105, 7),
       (390, 105, 8),
       (391, 105, 9),
       (392, 106, 6),
       (393, 106, 7),
       (394, 106, 8),
       (395, 106, 9);

INSERT INTO model_silicon_available_unified_memory (model_silicon_available_unified_memory_id,
                                                    model_available_apple_silicon_id,
                                                    model_apple_silicon_unified_memory_id)
VALUES (197, 100, 5),
       (198, 100, 8),
       (199, 101, 5),
       (200, 101, 8),
       (201, 101, 9),
       (202, 102, 7),
       (203, 103, 8),
       (204, 103, 9),
       (205, 103, 11),
       (206, 104, 5),
       (207, 104, 8),
       (208, 104, 9),
       (209, 105, 7),
       (210, 106, 8),
       (211, 106, 9),
       (212, 106, 11);

-- Silber, Space Schwarz
INSERT INTO model_available_color (model_available_color_id, model_id, model_color_id)
VALUES (170, 61, 1),
       (171, 61, 6),
       (172, 62, 1),
       (173, 62, 6),
       (174, 63, 1),
       (175, 63, 6),
       (176, 64, 1),
       (177, 64, 6);

-- True Tone, ProMotion bis 120 Hz, Schnelles Aufladen, PROLED XDR Display
INSERT INTO model_available_feature (model_available_feature_id, model_id, model_feature_id)
VALUES (140, 61, 1),
       (141, 61, 2),
       (142, 61, 3),
       (143, 61, 10),
       (144, 62, 1),
       (145, 62, 2),
       (146, 62, 3),
       (147, 62, 10),
       (148, 63, 1),
       (149, 63, 2),
       (150, 63, 3),
       (151, 63, 10),
       (152, 64, 1),
       (153, 64, 2),
       (154, 64, 3),
       (155, 64, 10);
--rollback DELETE FROM model_silicon_available_unified_memory WHERE model_silicon_available_unified_memory_id BETWEEN 197 AND 212;
--rollback DELETE FROM model_silicon_available_storage WHERE model_silicon_available_storage_id BETWEEN 371 AND 395;
--rollback DELETE FROM model_available_apple_silicon WHERE model_available_apple_silicon_id BETWEEN 100 AND 106;
--rollback DELETE FROM model_available_color WHERE model_available_color_id BETWEEN 170 AND 177;
--rollback DELETE FROM model_available_feature WHERE model_available_feature_id BETWEEN 140 AND 155;
--rollback DELETE FROM model WHERE model_id IN (61, 62, 63, 64);