--liquibase formatted sql
--changeset fabian:2-device-update-spare-part-price-format

UPDATE device_activity
SET name = CONCAT(
        SUBSTRING_INDEX(name, '€ ', 1),
        '€ ',
        REPLACE(REPLACE(REPLACE(
                                SUBSTRING_INDEX(name, '€ ', -1),
                                ',', '#'),
                        '.', ','),
                '#', '.')
           )
WHERE device_activity_type_id = 18;