--liquibase formatted sql

--changeset fabian:2-model-apple-silicon-variant-create-table
CREATE TABLE model_apple_silicon_variant
(
    model_apple_silicon_variant_id    int         NOT NULL AUTO_INCREMENT,
    name                              varchar(16) NOT NULL,
    model_apple_silicon_generation_id int         NOT NULL,
    created_at                        datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at                        datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (model_apple_silicon_variant_id),
    UNIQUE KEY uq_apple_silicon_variant_name (name),
    CONSTRAINT fk_model_apple_silicon_variant_generation_id
        FOREIGN KEY (model_apple_silicon_generation_id) REFERENCES model_apple_silicon_generation (model_apple_silicon_generation_id)
);

--changeset fabian:2-model-apple-silicon-variant-insert-data
INSERT INTO model_apple_silicon_variant (model_apple_silicon_variant_id, name, model_apple_silicon_generation_id)
VALUES (1, 'A18 Pro', 6), -- (MacBook Neo)
       (2, 'M1', 1),
       (3, 'M1 Pro', 1),
       (4, 'M1 Max', 1),
       (5, 'M2', 2),
       (6, 'M2 Pro', 2),
       (7, 'M2 Max', 2),
       (8, 'M3', 3),
       (9, 'M3 Pro', 3),
       (10, 'M3 Max', 3),
       (11, 'M4', 4),
       (12, 'M4 Pro', 4),
       (13, 'M4 Max', 4),
       (14, 'M5', 5),
       (15, 'M5 Pro', 5),
       (16, 'M5 Max', 5),
       (17, 'M6', 7);

--changeset fabian:2-model-apple-silicon-add-variant-column
ALTER TABLE model_apple_silicon
    ADD model_apple_silicon_variant_id int AFTER model_apple_silicon_generation_id;

--changeset fabian:2-model-apple-silicon-add-variant-fk
ALTER TABLE model_apple_silicon
    ADD CONSTRAINT fk_model_apple_silicon_variant_id
        FOREIGN KEY (model_apple_silicon_variant_id) REFERENCES model_apple_silicon_variant (model_apple_silicon_variant_id);

--changeset fabian:2-model-apple-silicon-assign-variant
-- A18 Pro
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 1
WHERE model_apple_silicon_id = 1;

-- M1
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 2
WHERE model_apple_silicon_id IN (2, 3);

-- M1 Pro
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 3
WHERE model_apple_silicon_id IN (4, 5, 6);

-- M1 Max
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 4
WHERE model_apple_silicon_id IN (7, 8);

-- M2
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 5
WHERE model_apple_silicon_id IN (9, 10, 11);

-- M2 Pro
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 6
WHERE model_apple_silicon_id IN (12, 13);

-- M2 Max
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 7
WHERE model_apple_silicon_id IN (14, 15);

-- M3
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 8
WHERE model_apple_silicon_id IN (16, 17, 18);

-- M3 Pro
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 9
WHERE model_apple_silicon_id IN (19, 20);

-- M3 Max
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 10
WHERE model_apple_silicon_id IN (21, 22);

-- M4
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 11
WHERE model_apple_silicon_id IN (23, 24, 25, 26);

-- M4 Pro
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 12
WHERE model_apple_silicon_id IN (27, 28);

-- M4 Max
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 13
WHERE model_apple_silicon_id IN (29, 30);

-- M5
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 14
WHERE model_apple_silicon_id IN (31, 32, 33);

-- M5 Pro
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 15
WHERE model_apple_silicon_id IN (34, 35);

-- M5 Max
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 16
WHERE model_apple_silicon_id IN (36, 37);

-- M6
UPDATE model_apple_silicon
SET model_apple_silicon_variant_id = 17
WHERE model_apple_silicon_id = 38;
--rollback UPDATE model_apple_silicon SET model_apple_silicon_variant_id = NULL WHERE model_apple_silicon_id BETWEEN 1 AND 38;

--changeset fabian:2-model-apple-silicon-set-variant-not-null
ALTER TABLE model_apple_silicon
    MODIFY model_apple_silicon_variant_id int NOT NULL;

--changeset fabian:2-model-apple-silicon-drop-generation-fk
ALTER TABLE model_apple_silicon
DROP
FOREIGN KEY fk_model_apple_silicon_generation_id;

--changeset fabian:2-model-apple-silicon-drop-generation-column
ALTER TABLE model_apple_silicon
DROP
COLUMN model_apple_silicon_generation_id;

--changeset fabian:2-model-apple-silicon-drop-name-short-column
ALTER TABLE model_apple_silicon
DROP
COLUMN name_short;