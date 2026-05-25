--liquibase formatted sql
--changeset fabian:DEVICE-2026-05-20-2159-add-default-data context:device

INSERT INTO device_battery_status (device_battery_status_id, name, sort_order)
VALUES (1, 'nicht getauscht', 1),
       (2, 'freiwillig getauscht', 2),
       (3, 'getauscht wegen Defekt', 3);

INSERT INTO device_grade (device_grade_id, name, description)
VALUES (1, 'A', 'neuwertig'),
       (2, 'B', 'kleinere sichtbare Kratzer'),
       (3, 'C', 'sichtbare Kratzer, Dellen oder Kerben');

INSERT INTO device_online_marketplace (device_online_marketplace_id, name, url)
VALUES (1, 'willhaben', 'https://www.willhaben.at/'),
       (2, 'Kleinanzeigen', 'https://kleinanzeigen.de/'),
       (3, 'eBay', 'https://www.ebay.at/');