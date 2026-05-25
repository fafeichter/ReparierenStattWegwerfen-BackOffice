--liquibase formatted sql
--changeset fabian:INVOICE-2026-05-20-2159-add-default-data context:invoice

INSERT INTO invoice_number (current_invoice_number)
VALUES (1);