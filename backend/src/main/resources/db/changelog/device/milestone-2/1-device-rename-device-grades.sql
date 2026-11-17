--liquibase formatted sql
--changeset fabian:1-device-rename-device-grades

UPDATE device_grade
SET description = 'Minimale Gebrauchsspuren, kaum sichtbar'
WHERE device_grade_id = 2;

UPDATE device_grade
SET description = 'Mehrere leichte Kratzer oder kleinere Abnutzungen oder wenige tiefe Kratzer oder Kerben'
WHERE device_grade_id = 3;

UPDATE device_grade
SET description = 'Viele und tiefe Kratzer oder Kerben'
WHERE device_grade_id = 4;