--liquibase formatted sql
--changeset fabian:1-business-partner-add-scammer-activity-types

INSERT INTO business_partner_activity_type (business_partner_activity_type_id, name)
VALUES (6, 'Als Scammer markiert'),
       (7, 'Nicht mehr als Scammer markiert');