--liquibase formatted sql
--changeset fabian:2026-05-21-data context:accounting

INSERT INTO accounting_device_invoice_number (last_device_invoice_number)
VALUES (0);