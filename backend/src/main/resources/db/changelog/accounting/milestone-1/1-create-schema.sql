--liquibase formatted sql
--changeset fabian:1-create-schema

CREATE TABLE accounting_invoice
(
    accounting_invoice_id int  NOT NULL AUTO_INCREMENT,
    number                int  NOT NULL,
    date                  date NOT NULL,
    created_at            datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at            datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (accounting_invoice_id),
    UNIQUE KEY uq_accounting_invoice_number (number)
);

CREATE TABLE accounting_invoice_number
(
    current_invoice_number int NOT NULL DEFAULT '0',
    created_at             datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at             datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);