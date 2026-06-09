--liquibase formatted sql
--changeset fabian:2026-06-09 context:model

alter table model_apple_silicon_unified_memory
    add constraint uq_model_apple_silicon_unified_memory
        unique (size, unit);

alter table model_apple_silicon
    change number_efficiency_cores number_cpu_efficiency_cores int not null;

alter table model_apple_silicon
    change number_performance_cores number_cpu_performance_cores int not null;

alter table model_apple_silicon
    change number_super_cores number_cpu_super_cores int;

alter table model_apple_silicon
    add number_gpu_cores int not null after number_cpu_super_cores;

UPDATE model_apple_silicon
SET number_gpu_cores = CASE model_apple_silicon_id
                           WHEN 1 THEN 5
                           WHEN 2 THEN 7
                           WHEN 3 THEN 8
                           WHEN 4 THEN 14
                           WHEN 5 THEN 14
                           WHEN 6 THEN 16
                           WHEN 7 THEN 24
                           WHEN 8 THEN 32
                           WHEN 9 THEN 8
                           WHEN 10 THEN 9
                           WHEN 11 THEN 10
                           WHEN 12 THEN 16
                           WHEN 13 THEN 19
                           WHEN 14 THEN 30
                           WHEN 15 THEN 38
                           WHEN 16 THEN 8
                           WHEN 17 THEN 10
                           WHEN 18 THEN 12
                           WHEN 19 THEN 14
                           WHEN 20 THEN 18
                           WHEN 21 THEN 30
                           WHEN 22 THEN 40
                           WHEN 23 THEN 9
                           WHEN 24 THEN 10
                           WHEN 25 THEN 8
                           WHEN 26 THEN 10
                           WHEN 27 THEN 16
                           WHEN 28 THEN 20
                           WHEN 29 THEN 32
                           WHEN 30 THEN 40
                           WHEN 31 THEN 10
                           WHEN 32 THEN 8
                           WHEN 33 THEN 10
                           WHEN 34 THEN 16
                           WHEN 35 THEN 20
                           WHEN 36 THEN 32
                           WHEN 37 THEN 40
    END;

alter table model_apple_silicon
    modify number_gpu_cores int not null;

