--liquibase formatted sql
--changeset fabian:2026-05-22-data context:device

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

INSERT INTO device_status_classification (device_status_classification_id, name)
VALUES (1, 'common'),
       (2, 'rare');

INSERT INTO device_status (device_status_id, name, sort_order, device_status_classification_id)
VALUES (1, 'bestellt / gekauft', 0, 1),
       (2, 'eingetroffen', 1, 1),
       (3, 'in Reparatur', 2, 1),
       (4, 'repariert', 3, 1),
       (5, 'zum Verkauf angeboten', 4, 1),
       (6, 'verkauft', 5, 1),
       (7, 'zum Ausschlachten verfügbar', 6, 1),
       (8, 'falscher Artikel eingetroffen', 7, 2),
       (9, 'zurückgegeben', 8, 2),
       (10, 'Verkäufer/in hat sich anders entschieden', 9, 2),
       (11, 'nie bekommen - Geld nicht zurückbekommen', 10, 2),
       (12, 'nie bekommen - Geld zurückbekommen', 11, 2);

INSERT INTO device_tag (device_tag_id, name, sort_order)
VALUES (1, 'Activation Lock', 1),
       (2, 'Activation Lock entfernt', 2),
       (3, 'Verkäufer könnte Scammer sein', 3),
       (4, 'Scammer', 4),
       (5, 'Hot-Deal', 5),
       (6, 'Staub unter Panel', 6),
       (7, 'QWERTY Tastaturlayout', 7),
       (8, 'Zu teuer eingekauft', 8),
       (9, 'Retoure', 9),
       (10, 'Verkauf mit Defekt', 10);

INSERT INTO device_activity_type (device_activity_type_id, name, description_template)
VALUES (1, 'Erstellt', 'Device #{{deviceId}} erstellt.'),
       (2, 'Status geändert', 'Status geändert von \"{{oldStatus}}\" auf \"{{newStatus}}\".'),
       (3, 'Akku Status geändert', 'Akku Status automatisch auf \"{{newBatteryStatus}}\" gesetzt.'),
       (4, 'Akku Status geändert', 'Akku Status geändert von \"{{oldBatteryStatus}}\" auf \"{{newBatteryStatus}}\".'),
       (5, 'Rechnung generiert', '{{invoiceFileName}}'),
       (6, 'Verkauft', null);