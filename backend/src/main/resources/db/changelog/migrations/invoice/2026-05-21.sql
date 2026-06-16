--liquibase formatted sql
--changeset fabian:2026-05-21 context:invoice

INSERT INTO invoice_number (current_invoice_number)
VALUES (1);