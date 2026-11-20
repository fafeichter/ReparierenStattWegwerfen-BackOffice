--liquibase formatted sql
--changeset fabian:3-device-add-business-partner-unset-activity-type

INSERT INTO device_activity_type (device_activity_type_id, name)
VALUES (29, 'Käufer ausgetragen');