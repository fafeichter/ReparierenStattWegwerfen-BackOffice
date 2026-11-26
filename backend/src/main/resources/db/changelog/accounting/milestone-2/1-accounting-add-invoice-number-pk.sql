--liquibase formatted sql

--changeset fabian:1-accounting-add-invoice-number-pk
ALTER TABLE accounting_invoice_number
    ADD accounting_invoice_number_id INT NOT NULL AUTO_INCREMENT
        PRIMARY KEY FIRST;