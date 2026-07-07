--liquibase formatted sql
--changeset fabian:2026-05-20-schema context:device

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

CREATE TABLE device_status_classification
(
    device_status_classification_id int          NOT NULL AUTO_INCREMENT,
    name                            varchar(256) NOT NULL,
    created_at                      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_status_classification_id),
    UNIQUE KEY uq_device_status_class_name (name)
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

CREATE TABLE device
(
    device_id                             int            NOT NULL AUTO_INCREMENT,
    model_id                              int            NOT NULL,
    buying_date                           date                    DEFAULT (CURRENT_DATE) NOT NULL,
    model_apple_silicon_id                int                     DEFAULT NULL,
    model_apple_silicon_unified_memory_id int                     DEFAULT NULL,
    model_storage_id                      int                     DEFAULT NULL,
    model_color_id                        int                     DEFAULT NULL,
    url                                   varchar(256)   NOT NULL,
    serial_number                         varchar(128)            DEFAULT NULL,
    device_status_id                      int            NOT NULL DEFAULT 1,
    seller_business_partner_id            int                     DEFAULT NULL,
    buyer_business_partner_id             int                     DEFAULT NULL,
    purchase_price                        DECIMAL(10, 2) NOT NULL,
    device_grade_id                       int                     DEFAULT NULL,
    reported_defect                       text           NOT NULL,
    diagnosed_defect                      text                    DEFAULT NULL,
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
    buying_date                           date                    DEFAULT (CURRENT_DATE) NOT NULL,
    model_id                              int            NOT NULL,
    model_apple_silicon_id                int                     DEFAULT NULL,
    model_apple_silicon_unified_memory_id int                     DEFAULT NULL,
    model_storage_id                      int                     DEFAULT NULL,
    model_color_id                        int                     DEFAULT NULL,
    url                                   varchar(128)   NOT NULL,
    serial_number                         varchar(128)            DEFAULT NULL,
    device_status_id                      int            NOT NULL DEFAULT 1,
    seller_business_partner_id            int                     DEFAULT NULL,
    buyer_business_partner_id             int                     DEFAULT NULL,
    purchase_price                        DECIMAL(10, 2) NOT NULL,
    device_grade_id                       int                     DEFAULT NULL,
    reported_defect                       text           NOT NULL,
    diagnosed_defect                      text                    DEFAULT NULL,
    selling_device_online_marketplace_id  int                     DEFAULT NULL,
    selling_date                          date                    DEFAULT NULL,
    battery_maximum_capacity              tinyint                 DEFAULT NULL,
    battery_cycle_count                   int                     DEFAULT NULL,
    device_battery_status_id              int                     DEFAULT NULL,
    selling_price                         DECIMAL(10, 2)          DEFAULT NULL,
    sale_id                               int                     DEFAULT NULL,
    created_at                            timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                            timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_audit_id),
    KEY                                   idx_audit_device_id (device_id),
    KEY                                   idx_audit_timestamp (device_audit_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_spare_part
(
    device_spare_part_id int            NOT NULL AUTO_INCREMENT,
    device_id            int            NOT NULL,
    name                 varchar(256)   NOT NULL,
    price_netto          DECIMAL(10, 2) NOT NULL,
    timestamp            timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at           timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY                  idx_device_spare_part_device_id (device_id),
    KEY                  idx_device_spare_part_timestamp (timestamp),
    CONSTRAINT fk_device_spare_part_device_id FOREIGN KEY (device_id) REFERENCES device (device_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    PRIMARY KEY (device_spare_part_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_note
(
    device_note_id int       NOT NULL AUTO_INCREMENT,
    device_id      int       NOT NULL,
    text           text      NOT NULL,
    date           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_note_id),
    KEY            idx_device_note_device_id (device_id),
    KEY            idx_device_note_date (date),
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
    date                 timestamp NOT NULL,
    created_at           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_note_audit_id),
    KEY                  idx_dna_device_note_id (device_note_id),
    KEY                  idx_dna_device_id (device_id),
    KEY                  idx_dna_audit_timestamp (device_note_audit_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_tag
(
    device_tag_id int          NOT NULL AUTO_INCREMENT,
    name          varchar(256) NOT NULL,
    sort_order    int          NOT NULL,
    created_at    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_tag_id),
    UNIQUE KEY uq_device_tag_name (name),
    UNIQUE KEY uq_device_tags_sort (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_tags
(
    device_tags_id int AUTO_INCREMENT,
    device_id      int       NOT NULL,
    device_tag_id  int       NOT NULL,
    created_at     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_tags_id),
    CONSTRAINT uq_device_tags_device_id_device_tag_id
        UNIQUE (device_id, device_tag_id),
    CONSTRAINT fk_device_tags_device_id
        FOREIGN KEY (device_id) REFERENCES device (device_id),
    CONSTRAINT fk_device_tags_device_tag_id
        FOREIGN KEY (device_tag_id) REFERENCES device_tag (device_tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_activity_type
(
    device_activity_type_id int auto_increment,
    name                    varchar(256) NOT NULL,
    description_template    varchar(256),
    created_at              timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_activity_type_id),
    CONSTRAINT uq_device_activity_type_description_template
        UNIQUE (description_template),
    CONSTRAINT uq_device_activity_type_name_description_template
        UNIQUE (name, description_template)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE device_activity
(
    device_activity_id      int auto_increment,
    device_id               int          NOT NULL,
    name                    varchar(256) NOT NULL,
    device_activity_type_id int          NOT NULL NULL,
    date                    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at              timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (device_activity_id),
    CONSTRAINT fk_activity_device_id
        FOREIGN KEY (device_id) REFERENCES device (device_id),
    CONSTRAINT fk_activity_device_activity_type_id
        FOREIGN KEY (device_id) REFERENCES device_activity_type (device_activity_type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;