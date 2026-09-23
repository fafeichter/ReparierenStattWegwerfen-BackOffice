--liquibase formatted sql
--changeset fabian:2026-05-20-schema context:accounting

CREATE TABLE accounting_device_invoice
(
    accounting_device_invoice_id int          NOT NULL AUTO_INCREMENT,
    number                       int          NOT NULL,
    device_id                    int          NOT NULL,
    date                         date         NOT NULL,
    created_at                   timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at                   timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (accounting_device_invoice_id),
    UNIQUE KEY uq_accounting_device_invoice_number (number),
    KEY                          idx_accounting_device_invoice_device_id (device_id),
    CONSTRAINT fk_accounting_device_invoice_device_id
        FOREIGN KEY (device_id) REFERENCES device (device_id)
            ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE accounting_device_invoice_number
(
    last_device_invoice_number int          NOT NULL DEFAULT '0',
    created_at                 timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at                 timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;