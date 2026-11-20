--liquibase formatted sql
--changeset fabian:2-business-partner-add-buyer-unset-activity-type

INSERT INTO business_partner_activity_type (business_partner_activity_type_id, name)
VALUES (8, 'Als Käufer ausgetragen');