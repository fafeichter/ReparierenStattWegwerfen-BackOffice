--liquibase formatted sql
--changeset fabian:2026-05-20-2115-add-initial-schema

CREATE TABLE model_apple_silicon
(
    model_apple_silicon_id int NOT NULL AUTO_INCREMENT,
    name                     varchar(256) NOT NULL,
    name_short               varchar(64)  NOT NULL,
    number_efficiency_cores  int          NOT NULL,
    number_performance_cores int          NOT NULL,
    number_super_cores       int          NOT NULL,
    created_at               timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at               timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_apple_silicon_id),
    UNIQUE KEY uq_apple_silicon_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE model_apple_silicon_unified_memory
(
    model_apple_silicon_unified_memory_id int       NOT NULL AUTO_INCREMENT,
    size                                  smallint  NOT NULL,
    unit                                  char(2)   NOT NULL,
    created_at                            timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                            timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_apple_silicon_unified_memory_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE business_partner_address_country
(
    business_partner_address_country_id int          NOT NULL AUTO_INCREMENT,
    name                                varchar(256) NOT NULL,
    code                                char(2)      NOT NULL COMMENT 'ISO-3166',
    created_at                          timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                          timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (business_partner_address_country_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_battery_status
(
    device_battery_status_id int          NOT NULL AUTO_INCREMENT,
    name                     varchar(256) NOT NULL,
    sort_order               int          NOT NULL,
    created_at               timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at               timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_battery_status_id),
    UNIQUE KEY uq_device_battery_sort (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_grade
(
    device_grade_id int          NOT NULL AUTO_INCREMENT,
    name            varchar(1)   NOT NULL,
    description     varchar(256) NOT NULL,
    created_at      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_grade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_spare_part
(
    device_spare_part_id int            NOT NULL AUTO_INCREMENT,
    name                 varchar(256)   NOT NULL,
    price_netto          DECIMAL(10, 2) NOT NULL,
    created_at           timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_spare_part_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_status_classification
(
    device_status_classification_id int          NOT NULL AUTO_INCREMENT,
    name                            varchar(256) NOT NULL,
    created_at                      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_status_classification_id),
    UNIQUE KEY uq_device_status_class_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE invoice_number
(
    current_invoice_number int       NOT NULL DEFAULT '1',
    created_at             timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at             timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE model_color
(
    model_color_id int          NOT NULL AUTO_INCREMENT,
    name           varchar(256) NOT NULL,
    created_at     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_color_id),
    UNIQUE KEY uq_model_color_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE model_feature_category
(
    model_feature_category_id int          NOT NULL AUTO_INCREMENT,
    value                     varchar(128) NOT NULL,
    created_at                timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_feature_category_id),
    UNIQUE KEY uq_model_feature_cat_value (value)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE model_series
(
    model_series_id int          NOT NULL AUTO_INCREMENT,
    name            varchar(256) NOT NULL,
    created_at      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_series_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE model_storage
(
    model_storage_id int       NOT NULL AUTO_INCREMENT,
    size             smallint  NOT NULL,
    unit             char(2)   NOT NULL,
    created_at       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_storage_id),
    UNIQUE KEY uq_model_storage_size (size, unit)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_online_marketplace
(
    device_online_marketplace_id int          NOT NULL AUTO_INCREMENT,
    name                         varchar(256) NOT NULL,
    url                          varchar(256) NOT NULL,
    created_at                   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_online_marketplace_id),
    UNIQUE KEY uq_online_marketplace_name (name)
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

CREATE TABLE device_status
(
    device_status_id                int          NOT NULL AUTO_INCREMENT,
    name                            varchar(256) NOT NULL,
    sort_order                      int          NOT NULL,
    device_status_classification_id int          NOT NULL,
    created_at                      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_status_id),
    UNIQUE KEY uq_device_status_name (name),
    UNIQUE KEY uq_device_status_sort (sort_order),
    KEY                             idx_device_status_classification_id (device_status_classification_id),
    CONSTRAINT fk_device_status_classification_id FOREIGN KEY (device_status_classification_id) REFERENCES device_status_classification (device_status_classification_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE model
(
    model_id            int           NOT NULL AUTO_INCREMENT,
    name                varchar(256)  NOT NULL,
    model_series_id     int           NOT NULL,
    model_number        varchar(5)    NOT NULL,
    technical_specs_url varchar(256)  NOT NULL,
    release_year        smallint      NOT NULL,
    display_size        smallint      NOT NULL,
    display_size_exact  DECIMAL(4, 1) NOT NULL,
    created_at          timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_id),
    UNIQUE KEY uq_model_name_number (name, model_number) USING BTREE,
    KEY                 idx_model_series_id (model_series_id),
    CONSTRAINT fk_model_series_id FOREIGN KEY (model_series_id) REFERENCES model_series (model_series_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE model_feature
(
    model_feature_id          int          NOT NULL AUTO_INCREMENT,
    name                      varchar(512) NOT NULL,
    model_feature_category_id int          NOT NULL,
    created_at                timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_feature_id),
    UNIQUE KEY uq_model_feature_name (name),
    KEY                       idx_mf_category_id (model_feature_category_id),
    CONSTRAINT fk_mf_category_id FOREIGN KEY (model_feature_category_id) REFERENCES model_feature_category (model_feature_category_id) ON DELETE RESTRICT ON UPDATE RESTRICT
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

CREATE TABLE model_available_apple_silicon
(
    model_available_apple_silicon_id int       NOT NULL AUTO_INCREMENT,
    model_id                         int       NOT NULL,
    model_apple_silicon_id int NOT NULL,
    created_at                       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_available_apple_silicon_id),
    UNIQUE KEY uq_maas_model_silicon (model_id, model_apple_silicon_id),
    KEY                    idx_maas_apple_silicon_id (model_apple_silicon_id),
    CONSTRAINT fk_maas_apple_silicon_id FOREIGN KEY (model_apple_silicon_id) REFERENCES model_apple_silicon (model_apple_silicon_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_maas_model_id FOREIGN KEY (model_id) REFERENCES model (model_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE model_available_color
(
    model_available_color_id int       NOT NULL AUTO_INCREMENT,
    model_id                 int       NOT NULL,
    model_color_id           int       NOT NULL,
    created_at               timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at               timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_available_color_id),
    UNIQUE KEY uq_mac_model_color (model_id, model_color_id),
    KEY                      idx_mac_model_color_id (model_color_id),
    CONSTRAINT fk_mac_model_color_id FOREIGN KEY (model_color_id) REFERENCES model_color (model_color_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_mac_model_id FOREIGN KEY (model_id) REFERENCES model (model_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE model_available_feature
(
    model_available_feature_id int       NOT NULL AUTO_INCREMENT,
    model_id                   int       NOT NULL,
    model_feature_id           int       NOT NULL,
    created_at                 timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                 timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_available_feature_id),
    UNIQUE KEY uq_maf_model_feature (model_id, model_feature_id),
    KEY                        idx_maf_feature_id (model_feature_id),
    CONSTRAINT fk_maf_feature_id FOREIGN KEY (model_feature_id) REFERENCES model_feature (model_feature_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_maf_model_id FOREIGN KEY (model_id) REFERENCES model (model_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE model_silicon_available_storage
(
    model_silicon_available_storage_id int       NOT NULL AUTO_INCREMENT,
    model_available_apple_silicon_id   int       NOT NULL,
    model_storage_id                   int       NOT NULL,
    created_at                         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_silicon_available_storage_id),
    UNIQUE KEY uq_msas_silicon_storage (model_available_apple_silicon_id, model_storage_id),
    KEY                                idx_msas_storage_id (model_storage_id),
    CONSTRAINT fk_msas_available_silicon_id FOREIGN KEY (model_available_apple_silicon_id) REFERENCES model_available_apple_silicon (model_available_apple_silicon_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_msas_storage_id FOREIGN KEY (model_storage_id) REFERENCES model_storage (model_storage_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE model_silicon_available_unified_memory
(
    model_silicon_available_unified_memory_id int       NOT NULL AUTO_INCREMENT,
    model_available_apple_silicon_id          int       NOT NULL,
    model_apple_silicon_unified_memory_id int NOT NULL,
    created_at                                timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                                timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_silicon_available_unified_memory_id),
    UNIQUE KEY uq_msaum_silicon_memory (model_available_apple_silicon_id, model_apple_silicon_unified_memory_id),
    KEY                                   idx_msaum_memory_id (model_apple_silicon_unified_memory_id),
    CONSTRAINT fk_msaum_maas FOREIGN KEY (model_available_apple_silicon_id) REFERENCES model_available_apple_silicon (model_available_apple_silicon_id) ON DELETE CASCADE,
    CONSTRAINT fk_msaum_memory FOREIGN KEY (model_apple_silicon_unified_memory_id) REFERENCES model_apple_silicon_unified_memory (model_apple_silicon_unified_memory_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device
(
    device_id                             int            NOT NULL AUTO_INCREMENT,
    model_id                              int            NOT NULL,
    model_apple_silicon_id                int                     DEFAULT NULL,
    model_apple_silicon_unified_memory_id int                     DEFAULT NULL,
    model_storage_id                      int                     DEFAULT NULL,
    model_color_id                        int                     DEFAULT NULL,
    serial_number                         varchar(128)            DEFAULT NULL,
    device_status_id                      int            NOT NULL DEFAULT 1,
    seller_business_partner_id            int                     DEFAULT NULL,
    buyer_business_partner_id             int                     DEFAULT NULL,
    purchase_price                        DECIMAL(10, 2) NOT NULL,
    device_grade_id                       int                     DEFAULT NULL,
    reported_defect                       text           NOT NULL,
    diagnosed_defect                      text                    DEFAULT NULL,
    purchase_device_online_marketplace_id int            NOT NULL,
    selling_device_online_marketplace_id  int                     DEFAULT NULL,
    selling_date                          date                    DEFAULT NULL,
    battery_maximum_capacity              tinyint                 DEFAULT NULL,
    battery_cycle_count                   int                     DEFAULT NULL,
    device_battery_status_id              int                     DEFAULT NULL,
    selling_price                         DECIMAL(10, 2)          DEFAULT NULL,
    created_at                            timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                            timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_id),
    KEY                                   idx_device_model_id (model_id),
    KEY                                   idx_device_apple_silicon_id (model_apple_silicon_id),
    KEY                                   idx_device_unified_memory_id (model_apple_silicon_unified_memory_id),
    KEY                                   idx_device_buyer_bp_id (buyer_business_partner_id),
    KEY                                   idx_device_status_id (device_status_id),
    KEY                                   idx_device_model_color_id (model_color_id),
    KEY                                   idx_device_purchase_om_id (purchase_device_online_marketplace_id),
    KEY                                   idx_device_seller_bp_id (seller_business_partner_id),
    KEY                                   idx_device_grade_id (device_grade_id),
    KEY                                   idx_device_model_storage_id (model_storage_id),
    KEY                                   idx_device_selling_om (selling_device_online_marketplace_id),
    KEY                                   idx_device_battery_status_id (device_battery_status_id),
    CONSTRAINT fk_device_apple_silicon_id FOREIGN KEY (model_apple_silicon_id) REFERENCES model_apple_silicon (model_apple_silicon_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_device_battery_status_id FOREIGN KEY (device_battery_status_id) REFERENCES device_battery_status (device_battery_status_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_device_buyer_bp_id FOREIGN KEY (buyer_business_partner_id) REFERENCES business_partner (business_partner_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_device_device_grade_id FOREIGN KEY (device_grade_id) REFERENCES device_grade (device_grade_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_device_model_color_id FOREIGN KEY (model_color_id) REFERENCES model_color (model_color_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_device_model_id FOREIGN KEY (model_id) REFERENCES model (model_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_device_model_storage_id FOREIGN KEY (model_storage_id) REFERENCES model_storage (model_storage_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_device_purchase_om_id FOREIGN KEY (purchase_device_online_marketplace_id) REFERENCES device_online_marketplace (device_online_marketplace_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_device_seller_bp_id FOREIGN KEY (seller_business_partner_id) REFERENCES business_partner (business_partner_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_device_selling_om_id FOREIGN KEY (selling_device_online_marketplace_id) REFERENCES device_online_marketplace (device_online_marketplace_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_device_status_id FOREIGN KEY (device_status_id) REFERENCES device_status (device_status_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_device_unified_memory_id FOREIGN KEY (model_apple_silicon_unified_memory_id) REFERENCES model_apple_silicon_unified_memory (model_apple_silicon_unified_memory_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_audit
(
    device_audit_id                       bigint         NOT NULL AUTO_INCREMENT,
    audit_action                          enum('INSERT','UPDATE','DELETE') NOT NULL,
    audit_timestamp                       timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    device_id                             int            NOT NULL,
    model_id                              int            NOT NULL,
    model_apple_silicon_id                int                     DEFAULT NULL,
    model_apple_silicon_unified_memory_id int                     DEFAULT NULL,
    model_storage_id                      int                     DEFAULT NULL,
    model_color_id                        int                     DEFAULT NULL,
    serial_number                         varchar(128)            DEFAULT NULL,
    device_status_id                      int            NOT NULL,
    seller_business_partner_id            int                     DEFAULT NULL,
    buyer_business_partner_id             int                     DEFAULT NULL,
    purchase_price                        DECIMAL(10, 2) NOT NULL,
    device_grade_id                       int                     DEFAULT NULL,
    reported_defect                       text           NOT NULL,
    diagnosed_defect                      text                    DEFAULT NULL,
    purchase_device_online_marketplace_id int            NOT NULL,
    selling_device_online_marketplace_id  int                     DEFAULT NULL,
    selling_date                          date                    DEFAULT NULL,
    battery_maximum_capacity              tinyint                 DEFAULT NULL,
    battery_cycle_count                   int                     DEFAULT NULL,
    device_battery_status_id              int                     DEFAULT NULL,
    selling_price                         DECIMAL(10, 2)          DEFAULT NULL,
    invoice_id                            int                     DEFAULT NULL,
    created_at                            timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                            timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_audit_id),
    KEY                                   idx_audit_device_id (device_id),
    KEY                                   idx_audit_timestamp (device_audit_id),
    KEY                                   idx_audit_action (device_audit_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_installed_spare_part
(
    device_installed_spare_part_id int       NOT NULL AUTO_INCREMENT,
    device_id                      int       NOT NULL,
    device_spare_part_id           int       NOT NULL,
    created_at                     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_installed_spare_part_id),
    KEY                            idx_disp_device_id (device_id),
    KEY                            idx_disp_spare_part_id (device_spare_part_id),
    CONSTRAINT fk_disp_device_id FOREIGN KEY (device_id) REFERENCES device (device_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_disp_spare_part_id FOREIGN KEY (device_spare_part_id) REFERENCES device_spare_part (device_spare_part_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_note
(
    device_note_id int       NOT NULL AUTO_INCREMENT,
    device_id      int       NOT NULL,
    text           text      NOT NULL,
    created_at     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_note_id),
    KEY            idx_device_note_device_id (device_id),
    CONSTRAINT fk_device_note_device_id FOREIGN KEY (device_id) REFERENCES device (device_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_note_audit
(
    device_note_audit_id bigint    NOT NULL AUTO_INCREMENT,
    audit_action         enum('INSERT','UPDATE','DELETE') NOT NULL,
    audit_timestamp      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    device_note_id       int       NOT NULL,
    device_id            int       NOT NULL,
    text                 text      NOT NULL,
    created_at           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_note_audit_id),
    KEY                  idx_dna_device_note_id (device_note_id),
    KEY                  idx_dna_device_id (device_id),
    KEY                  idx_dna_audit_timestamp (device_note_audit_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE invoice
(
    invoice_id int       NOT NULL AUTO_INCREMENT,
    number     int       NOT NULL,
    device_id  int       NOT NULL,
    date       date      NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (invoice_id),
    UNIQUE KEY uq_invoice_number (number),
    KEY        idx_invoice_device_id (device_id),
    CONSTRAINT fk_invoice_device_id FOREIGN KEY (device_id) REFERENCES device (device_id) ON DELETE RESTRICT ON
        UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;