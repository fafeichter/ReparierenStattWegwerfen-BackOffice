--liquibase formatted sql
--changeset fabian:2026-05-21-triggers context:device endDelimiter:$$
-- --------------------------------------------------------

CREATE TRIGGER tr_device_insert
    AFTER INSERT
    ON device
    FOR EACH ROW
BEGIN
    INSERT INTO device_audit (audit_action, audit_timestamp, device_id, buying_date, model_id, model_apple_silicon_id,
                              model_apple_silicon_unified_memory_id, model_storage_id, model_color_id, url,
                              serial_number, device_status_id, seller_business_partner_id, buyer_business_partner_id,
                              purchase_price, device_grade_id, reported_defect, diagnosed_defect,
                              selling_device_online_marketplace_id, selling_date, battery_maximum_capacity,
                              battery_cycle_count, device_battery_status_id, selling_price, created_at, updated_at)
    VALUES ('INSERT', CURRENT_TIMESTAMP, NEW.device_id, NEW.buying_date, NEW.model_id, NEW.model_apple_silicon_id,
            NEW.model_apple_silicon_unified_memory_id, NEW.model_storage_id, NEW.model_color_id, NEW.url,
            NEW.serial_number, NEW.device_status_id, NEW.seller_business_partner_id, NEW.buyer_business_partner_id,
            NEW.purchase_price, NEW.device_grade_id, NEW.reported_defect, NEW.diagnosed_defect,
            NEW.selling_device_online_marketplace_id, NEW.selling_date, NEW.battery_maximum_capacity,
            NEW.battery_cycle_count, NEW.device_battery_status_id, NEW.selling_price, NEW.created_at, NEW.updated_at);
END $$

CREATE TRIGGER tr_device_update
    AFTER UPDATE
    ON device
    FOR EACH ROW
BEGIN
    INSERT INTO device_audit (audit_action, audit_timestamp, device_id, buying_date, model_id, model_apple_silicon_id,
                              model_apple_silicon_unified_memory_id, model_storage_id, model_color_id, url,
                              serial_number, device_status_id, seller_business_partner_id, buyer_business_partner_id,
                              purchase_price, device_grade_id, reported_defect, diagnosed_defect,
                              selling_device_online_marketplace_id, selling_date, battery_maximum_capacity,
                              battery_cycle_count, device_battery_status_id, selling_price, created_at,
                              updated_at)
    VALUES ('UPDATE', CURRENT_TIMESTAMP, NEW.device_id, NEW.buying_date, NEW.model_id, NEW.model_apple_silicon_id,
            NEW.model_apple_silicon_unified_memory_id, NEW.model_storage_id, NEW.model_color_id, NEW.url,
            NEW.serial_number, NEW.device_status_id, NEW.seller_business_partner_id, NEW.buyer_business_partner_id,
            NEW.purchase_price, NEW.device_grade_id, NEW.reported_defect, NEW.diagnosed_defect,
            NEW.selling_device_online_marketplace_id, NEW.selling_date, NEW.battery_maximum_capacity,
            NEW.battery_cycle_count, NEW.device_battery_status_id, NEW.selling_price, NEW.created_at,
            NEW.updated_at);
END $$

CREATE TRIGGER tr_device_delete
    AFTER DELETE
    ON device
    FOR EACH ROW
BEGIN
    INSERT INTO device_audit (audit_action, audit_timestamp, device_id, buying_date, model_id, model_apple_silicon_id,
                              model_apple_silicon_unified_memory_id, model_storage_id, model_color_id, url,
                              serial_number, device_status_id, seller_business_partner_id, buyer_business_partner_id,
                              purchase_price, device_grade_id, reported_defect, diagnosed_defect,
                              selling_device_online_marketplace_id, selling_date, battery_maximum_capacity,
                              battery_cycle_count, device_battery_status_id, selling_price, created_at,
                              updated_at)
    VALUES ('DELETE', CURRENT_TIMESTAMP, OLD.device_id, OLD.buying_date, OLD.model_id, OLD.model_apple_silicon_id,
            OLD.model_apple_silicon_unified_memory_id, OLD.model_storage_id, OLD.model_color_id, OLD.url,
            OLD.serial_number, OLD.device_status_id, OLD.seller_business_partner_id, OLD.buyer_business_partner_id,
            OLD.purchase_price, OLD.device_grade_id, OLD.reported_defect, OLD.diagnosed_defect,
            OLD.selling_device_online_marketplace_id, OLD.selling_date, OLD.battery_maximum_capacity,
            OLD.battery_cycle_count, OLD.device_battery_status_id, OLD.selling_price, OLD.created_at,
            OLD.updated_at);
END $$

CREATE TRIGGER tr_device_note_insert
    AFTER INSERT
    ON device_note
    FOR EACH ROW
BEGIN
    INSERT INTO device_note_audit (audit_action, audit_timestamp, device_id, device_note_id, text, timestamp,
                                   created_at, updated_at)
    VALUES ('INSERT', CURRENT_TIMESTAMP, NEW.device_id, NEW.device_note_id, NEW.text, NEW.timestamp, NEW.created_at,
            NEW.updated_at);
END $$

CREATE TRIGGER tr_device_note_update
    AFTER UPDATE
    ON device_note
    FOR EACH ROW
BEGIN
    INSERT INTO device_note_audit (audit_action, audit_timestamp, device_id, device_note_id, text, timestamp,
                                   created_at, updated_at)
    VALUES ('UPDATE', CURRENT_TIMESTAMP, NEW.device_id, NEW.device_note_id, NEW.text, NEW.timestamp, NEW.created_at,
            NEW.updated_at);
END $$

CREATE TRIGGER tr_device_note_delete
    AFTER DELETE
    ON device_note
    FOR EACH ROW
BEGIN
    INSERT INTO device_note_audit (audit_action, audit_timestamp, device_id, device_note_id, text, timestamp,
                                   created_at, updated_at)
    VALUES ('DELETE', CURRENT_TIMESTAMP, OLD.device_id, OLD.device_note_id, OLD.text, OLD.timestamp, OLD.created_at, OLD
        .updated_at);
END $$