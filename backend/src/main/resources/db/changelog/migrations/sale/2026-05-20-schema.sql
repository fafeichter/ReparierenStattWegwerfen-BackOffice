--liquibase formatted sql
--changeset fabian:2026-05-20-schema context:sale

CREATE TABLE sale_invoice
(
    sale_invoice_id int       NOT NULL AUTO_INCREMENT,
    number          int       NOT NULL,
    device_id       int       NOT NULL,
    date            date      NOT NULL,
    created_at      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (sale_invoice_id),
    UNIQUE KEY uq_sale_number (number),
    KEY             idx_sale_device_id (device_id),
    CONSTRAINT fk_sale_device_id FOREIGN KEY (device_id) REFERENCES device (device_id) ON DELETE RESTRICT ON
        UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE sale_invoice_number
(
    current_sale_number int       NOT NULL DEFAULT '1',
    created_at          timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;