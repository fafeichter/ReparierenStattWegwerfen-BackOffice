--liquibase formatted sql
--changeset fabian:2026-05-20 context:businesspartner

CREATE TABLE business_partner_address_country
(
    business_partner_address_country_id int          NOT NULL AUTO_INCREMENT,
    name                                varchar(256) NOT NULL,
    code                                char(2)      NOT NULL COMMENT 'ISO-3166',
    created_at                          timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                          timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (business_partner_address_country_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE business_partner_address
(
    business_partner_address_id         int          NOT NULL AUTO_INCREMENT,
    street                              varchar(256) NOT NULL,
    house_number                        varchar(64)  NOT NULL,
    zip_code                            varchar(10)  NOT NULL,
    city                                varchar(256) NOT NULL,
    business_partner_address_country_id int          NOT NULL,
    created_at                          timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                          timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (business_partner_address_id),
    KEY                                 idx_bpa_country_id (business_partner_address_country_id),
    CONSTRAINT fk_bpa_country_id FOREIGN KEY (business_partner_address_country_id) REFERENCES business_partner_address_country (business_partner_address_country_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE business_partner
(
    business_partner_id         int          NOT NULL AUTO_INCREMENT,
    first_name                  varchar(256) NOT NULL,
    last_name                   varchar(256) NOT NULL,
    telephone                   varchar(64)           DEFAULT NULL,
    email                       varchar(256)          DEFAULT NULL,
    company_name                varchar(256)          DEFAULT NULL,
    scammer                     tinyint(1)   NOT NULL DEFAULT '0',
    business_partner_address_id int          NOT NULL,
    created_at                  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (business_partner_id),
    KEY                         idx_bp_address_id (business_partner_address_id),
    CONSTRAINT fk_bp_address_id FOREIGN KEY (business_partner_address_id) REFERENCES business_partner_address (business_partner_address_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;