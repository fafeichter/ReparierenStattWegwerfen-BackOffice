--liquibase formatted sql

--changeset fabian:4-model-update-m6-model-number-and-url
UPDATE model
SET model_number        = 'A3536',
    technical_specs_url = 'https://support.apple.com/de-at/148300'
WHERE model_id = 60;