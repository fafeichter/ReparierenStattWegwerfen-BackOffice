--liquibase formatted sql
--changeset fabian:2026-05-20-schema context:model

CREATE TABLE model_apple_silicon
(
    model_apple_silicon_id       int          NOT NULL AUTO_INCREMENT,
    name                         varchar(256) NOT NULL,
    name_short                   varchar(64)  NOT NULL,
    number_cpu_efficiency_cores  int          NOT NULL,
    number_cpu_performance_cores int          NOT NULL,
    number_cpu_super_cores       int          NOT NULL,
    number_gpu_cores             int          NOT NULL,
    created_at                   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
    PRIMARY KEY (model_apple_silicon_unified_memory_id),
    CONSTRAINT uq_model_apple_silicon_unified_memory UNIQUE (size, unit)
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

CREATE TABLE model
(
    model_id            int           NOT NULL AUTO_INCREMENT,
    name                varchar(256)  NOT NULL,
    model_series_id     int           NOT NULL,
    model_number        varchar(5)    NOT NULL,
    technical_specs_url varchar(256)  NOT NULL,
    release_year year          NOT NULL,
    release_month       tinyint       NOT NULL,
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

CREATE TABLE model_available_apple_silicon
(
    model_available_apple_silicon_id int       NOT NULL AUTO_INCREMENT,
    model_id                         int       NOT NULL,
    model_apple_silicon_id           int       NOT NULL,
    created_at                       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_available_apple_silicon_id),
    UNIQUE KEY uq_maas_model_silicon (model_id, model_apple_silicon_id),
    KEY                              idx_maas_apple_silicon_id (model_apple_silicon_id),
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
    model_apple_silicon_unified_memory_id     int       NOT NULL,
    created_at                                timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                                timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (model_silicon_available_unified_memory_id),
    UNIQUE KEY uq_msaum_silicon_memory (model_available_apple_silicon_id, model_apple_silicon_unified_memory_id),
    KEY                                       idx_msaum_memory_id (model_apple_silicon_unified_memory_id),
    CONSTRAINT fk_msaum_maas FOREIGN KEY (model_available_apple_silicon_id) REFERENCES model_available_apple_silicon (model_available_apple_silicon_id) ON DELETE CASCADE,
    CONSTRAINT fk_msaum_memory FOREIGN KEY (model_apple_silicon_unified_memory_id) REFERENCES model_apple_silicon_unified_memory (model_apple_silicon_unified_memory_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;