--liquibase formatted sql
--changeset fabian:XXX-business-partner-test-data context:@test-data

INSERT INTO business_partner_address (business_partner_address_id, street, house_number, zip_code, city,
                                      business_partner_address_country_id)
VALUES (2, 'Großmannstraße', '17', '79541', 'Lörrach', 2),
       (3, 'Klein Radischen', '11', '3862', 'Eisgarn', 1),
       (4, 'Untergreith', '139', '8443', 'Untergreith', 1);

INSERT INTO business_partner (business_partner_id, first_name, last_name, telephone, email, company_name, company_uid,
                              scammer, business_partner_address_id)
VALUES (2, 'Jonas', 'N/A', NULL, NULL, NULL, NULL, NULL, NULL),
       (3, 'Steven', 'Dreischhoff', NULL, NULL, NULL, NULL, NULL, 2),
       (4, 'Lisa', 'N/A', NULL, NULL, NULL, NULL, NULL, NULL),
       (15, 'Yaren0904', 'N/A', NULL, NULL, NULL, NULL, NULL, NULL),
       (16, 'Christoph', 'Christoph', NULL, NULL, NULL, NULL, NULL, 3),
       (17, 'Andreas', 'N/A', NULL, NULL, NULL, NULL, NULL, NULL),
       (18, 'Maximilian', 'Gollob', NULL, NULL, NULL, NULL, NULL, 4);

INSERT INTO business_partner_activity (business_partner_activity_id, business_partner_id, name, actor,
                                       business_partner_activity_type_id, date)
VALUES (2, 2, 'Jonas N/A', 'system', 1, '2026-09-30 15:45:46.443109'),
       (3, 2, '#1', 'system', 3, '2026-09-30 15:45:46.483586'),
       (4, 3, 'Steven Dreischoff', 'system', 1, '2026-09-30 15:46:37.682363'),
       (5, 3, '#2', 'system', 3, '2026-09-30 15:46:37.685021'),
       (6, 4, 'Lisa N/A', 'system', 1, '2026-09-30 15:49:02.798450'),
       (7, 4, '#3', 'system', 3, '2026-09-30 15:49:02.800588'),
       (8, 3, 'Großmannstraße 17\n79541 Lörrach\nDeutschland', 'fabian', 2, '2026-09-30 15:50:59.155439'),
       (9, 15, 'Yaren0904 N/A', 'system', 1, '2026-09-30 16:21:22.893404'),
       (10, 15, '#4', 'system', 3, '2026-09-30 16:21:22.950075'),
       (11, 16, 'Christoph Christoph', 'fabian', 1, '2026-09-30 16:42:36.704038'),
       (12, 16, 'Klein Radischen 11\n3862 Eisgarn\nÖsterreich', 'fabian', 2, '2026-09-30 16:42:36.704493'),
       (13, 16, '#4', 'fabian', 4, '2026-09-30 16:42:36.706575'),
       (14, 17, 'Andreas N/A', 'system', 1, '2026-09-30 16:45:05.035611'),
       (15, 17, '#5', 'system', 3, '2026-09-30 16:45:05.037805'),
       (16, 18, 'Maximilian Gollob', 'system', 1, '2026-09-30 16:46:27.377065'),
       (17, 18, '#6', 'system', 3, '2026-09-30 16:46:27.378754'),
       (18, 18, 'Untergreith 139\n8443 Untergreith\nÖsterreich', 'fabian', 2, '2026-09-30 16:47:42.371781');