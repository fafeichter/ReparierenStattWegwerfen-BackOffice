--liquibase formatted sql
--changeset fabian:2026-05-21 context:businesspartner

INSERT INTO business_partner_address_country (business_partner_address_country_id, name, code)
VALUES (1, 'Österreich', 'AT'),
       (2, 'Deutschland', 'DE'),
       (3, 'Moldau', 'MD');