--liquibase formatted sql
--changeset fabian:2026-05-22-data context:device

INSERT INTO device_battery_status (device_battery_status_id, name, sort_order)
VALUES (1, 'nicht getauscht', 1),
       (2, 'freiwillig getauscht', 2),
       (3, 'getauscht wegen Defekt', 3);

INSERT INTO device_grade (device_grade_id, name, description, description_long, sort_order)
VALUES (1, 'Grade A', 'Wie neu', 'Makelloser Zustand ohne Gebrauchsspuren', 1),
       (2, 'Grade B', 'Minimale, kaum sichtbare Gebrauchsspuren',
        'Sehr guter Zustand mit minimalen, kaum sichtbaren Gebrauchsspuren', 2),
       (3, 'Grade C', 'Mehrere leichte Kratzer und/oder normale Abnutzungen',
        'Guter Zustand mit mehreren leichten Kratzern oder normalen Abnutzungen', 3),
       (4, 'Grade D', 'Tiefere Kratzer oder Kerben', 'Deutliche Gebrauchsspuren wie tiefere Kratzer oder Kerben', 4);

INSERT INTO device_online_marketplace (device_online_marketplace_id, name, url)
VALUES (1, 'willhaben', 'https://www.willhaben.at/'),
       (2, 'Kleinanzeigen', 'https://kleinanzeigen.de/'),
       (3, 'eBay', 'https://www.ebay.at/');

INSERT INTO device_status_classification (device_status_classification_id, name)
VALUES (1, 'common'),
       (2, 'rare'),
       (3, 'system-only');

INSERT INTO device_status (device_status_id, name, sort_order, device_status_classification_id)
VALUES (1, 'bestellt / gekauft', 0, 1),
       (2, 'eingetroffen', 1, 1),
       (3, 'in Reparatur', 2, 1),
       (4, 'repariert', 3, 1),
       (5, 'zum Verkauf angeboten', 4, 1),
       (6, 'verkauft', 5, 1),
       (7, 'zum Ausschlachten verfügbar', 6, 1),
       (8, 'archiviert', 99999, 3),
       (9, 'falscher Artikel eingetroffen', 7, 2),
       (10, 'zurückgeschickt', 8, 2),
       (11, 'Verkäufer/in hat sich anders entschieden', 9, 2),
       (12, 'nie bekommen - Geld nicht zurückbekommen', 10, 2),
       (13, 'nie bekommen - Geld zurückbekommen', 11, 2);

INSERT INTO device_tag (device_tag_id, name, sort_order)
VALUES (1, 'Activation Lock', 1),
       (2, 'Verkauf mit Defekt', 2),
       (3, 'QWERTY Tastaturlayout', 3),
       (4, 'Tastaturlayout auf QWERTZ umgebaut', 4),
       (5, 'Retoure', 5),
       (6, 'Gewährleistungs-Fall', 6);

INSERT INTO device_activity_type (device_activity_type_id, name)
VALUES (1, 'Erstellt'),
       (2, 'Status geändert'),
       (3, 'Akku Status geändert'),
       (4, 'Äußerlichen Zustand geändert'),
       (5, 'Tag hinzugefügt'),
       (6, 'Tag entfernt');