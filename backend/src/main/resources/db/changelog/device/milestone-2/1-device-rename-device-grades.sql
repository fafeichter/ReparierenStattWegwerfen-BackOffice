--liquibase formatted sql
--changeset fabian:1-device-rename-device-grades

update device_grade
set description = 'Minimale Gebrauchsspuren, kaum sichtbar'
where device_grade_id = 2;

update device_grade
set description = 'Mehrere leichte Kratzer oder kleinere Abnutzungen oder wenige tiefe Kratzer oder Kerben'
where device_grade_id = 3;

update device_grade
set description = 'Viele und tiefe Kratzer oder Kerben'
where device_grade_id = 4;