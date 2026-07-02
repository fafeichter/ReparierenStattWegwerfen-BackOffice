--liquibase formatted sql
--changeset fabian:event_publication_registry:application

CREATE TABLE event_publication
(
    id                     VARCHAR(36)   NOT NULL,
    listener_id            VARCHAR(512)  NOT NULL,
    event_type             VARCHAR(512)  NOT NULL,
    serialized_event       VARCHAR(4000) NOT NULL,
    publication_date       TIMESTAMP(6)  NOT NULL,
    completion_date        TIMESTAMP(6) DEFAULT NULL NULL,
    status                 VARCHAR(20),
    completion_attempts    INT,
    last_resubmission_date TIMESTAMP(6) DEFAULT NULL NULL,
    PRIMARY KEY (id),
    INDEX                  event_publication_by_completion_date_idx (completion_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE event_publication_archive
(
    id                     VARCHAR(36)   NOT NULL,
    listener_id            VARCHAR(512)  NOT NULL,
    event_type             VARCHAR(512)  NOT NULL,
    serialized_event       VARCHAR(4000) NOT NULL,
    publication_date       TIMESTAMP(6)  NOT NULL,
    completion_date        TIMESTAMP(6) DEFAULT NULL NULL,
    status                 VARCHAR(20),
    completion_attempts    INT,
    last_resubmission_date TIMESTAMP(6) DEFAULT NULL NULL,
    PRIMARY KEY (id),
    INDEX                  event_publication_archive_by_completion_date_idx (completion_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;