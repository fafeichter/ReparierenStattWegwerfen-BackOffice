--liquibase formatted sql
--changeset fabian:2026-09-23-schema context:sparepart

CREATE TABLE spare_part
(
    spare_part_id int auto_increment,
    name          varchar(256) NOT NULL,
    sort_order    int          NOT NULL,
    created_at    timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at    timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (spare_part_id),
    UNIQUE KEY spare_part_name (name),
    UNIQUE KEY uq_spare_part_sort (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE spare_part_available_model_series
(
    spare_part_available_model_series_id int          NOT NULL AUTO_INCREMENT,
    spare_part_id                        int          NOT NULL,
    model_series_id                      int          NOT NULL,
    created_at                           timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at                           timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (spare_part_available_model_series_id),
    UNIQUE KEY uq_spare_part_model_series (spare_part_id, model_series_id),
    FOREIGN KEY (spare_part_id) REFERENCES spare_part (spare_part_id) ON DELETE CASCADE,
    FOREIGN KEY (model_series_id) REFERENCES model_series (model_series_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;