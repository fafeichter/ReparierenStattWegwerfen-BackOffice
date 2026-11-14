--liquibase formatted sql

--changeset fabian:1-model-add-apple-silicon-generation-table
CREATE TABLE model_apple_silicon_generation
(
    model_apple_silicon_generation_id int         NOT NULL AUTO_INCREMENT,
    name                              varchar(16) NOT NULL,
    created_at                        datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at                        datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (model_apple_silicon_generation_id),
    UNIQUE KEY uq_apple_silicon_generation_name (name)
);

--changeset fabian:1-model-add-apple-silicon-generation-table-add-generations
INSERT INTO model_apple_silicon_generation (model_apple_silicon_generation_id, name)
VALUES (1, 'M1'),
       (2, 'M2'),
       (3, 'M3'),
       (4, 'M4'),
       (5, 'M5'),
       (6, 'A18'), -- (MacBook Neo)
       (7, 'M6');

--changeset fabian:1-model-add-apple-silicon-add-silicon-generation-column
ALTER TABLE model_apple_silicon
    ADD model_apple_silicon_generation_id int AFTER name;

--changeset fabian:1-model-add-apple-silicon-add-silicon-generation-fk
ALTER TABLE model_apple_silicon
    ADD CONSTRAINT fk_model_apple_silicon_generation_id
        FOREIGN KEY (model_apple_silicon_generation_id) REFERENCES model_apple_silicon_generation (model_apple_silicon_generation_id);

--changeset fabian:1-model-add-apple-silicon-assign-silicon-generation
-- M1
UPDATE model_apple_silicon
SET model_apple_silicon_generation_id = 1
WHERE model_apple_silicon_id IN (2, 3, 4, 5, 6, 7, 8);

-- M2
UPDATE model_apple_silicon
SET model_apple_silicon_generation_id = 2
WHERE model_apple_silicon_id IN (9, 10, 11, 12, 13, 14, 15);

-- M3
UPDATE model_apple_silicon
SET model_apple_silicon_generation_id = 3
WHERE model_apple_silicon_id IN (16, 17, 18, 19, 20, 21, 22);

-- M4
UPDATE model_apple_silicon
SET model_apple_silicon_generation_id = 4
WHERE model_apple_silicon_id IN (23, 24, 25, 26, 27, 28, 29, 30);

-- M5
UPDATE model_apple_silicon
SET model_apple_silicon_generation_id = 5
WHERE model_apple_silicon_id IN (31, 32, 33, 34, 35, 36, 37);

-- A18
UPDATE model_apple_silicon
SET model_apple_silicon_generation_id = 6
WHERE model_apple_silicon_id = 1;

-- M6
UPDATE model_apple_silicon
SET model_apple_silicon_generation_id = 7
WHERE model_apple_silicon_id = 38;
--rollback UPDATE model_apple_silicon SET model_apple_silicon_generation_id = NULL WHERE model_apple_silicon_id BETWEEN 1 AND 38;

--changeset fabian:1-model-add-apple-silicon-silicon-generation-not-null
ALTER TABLE model_apple_silicon
    MODIFY model_apple_silicon_generation_id int NOT NULL;