--liquibase formatted sql
--changeset fabian:BP-2026-05-20-2159-add-default-data context:businesspartner

INSERT INTO business_partner_address_country (business_partner_address_country_id, name, code)
VALUES (1, 'Österreich', 'AT'),
       (2, 'Deutschland', 'DE'),
       (3, 'Moldau', 'MD');