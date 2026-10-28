--liquibase formatted sql
--changeset fabian:2026-05-20-schema context:accounting

CREATE TABLE accounting_invoice
(
    accounting_invoice_id int NOT NULL AUTO_INCREMENT,
    number                       int          NOT NULL,
    date                         date         NOT NULL,
    created_at                   timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at                   timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (accounting_invoice_id),
    UNIQUE KEY uq_accounting_invoice_number (number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE accounting_invoice_number
(
    current_invoice_number int NOT NULL DEFAULT '0',
    created_at                 timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at                 timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;