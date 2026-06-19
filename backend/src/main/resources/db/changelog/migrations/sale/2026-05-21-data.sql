--liquibase formatted sql
--changeset fabian:2026-05-21-data context:sale

INSERT INTO sale_invoice_number (current_sale_number)
VALUES (1);