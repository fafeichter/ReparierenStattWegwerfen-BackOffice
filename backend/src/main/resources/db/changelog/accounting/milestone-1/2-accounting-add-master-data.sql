--liquibase formatted sql
--changeset fabian:2-accounting-add-master-data

INSERT INTO accounting_invoice_number (current_invoice_number)
VALUES (0);