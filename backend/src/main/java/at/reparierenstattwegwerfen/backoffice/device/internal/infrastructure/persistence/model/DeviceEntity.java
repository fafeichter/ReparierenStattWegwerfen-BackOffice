package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
public class DeviceEntity implements NamedId {

	@Id
	@DeviceMaxPlusOneId
	@Column(name = "device_id")
	private Integer id;

	@Column(name = "model_id")
	private Integer modelId;

	@Column(name = "buying_date")
	private LocalDate buyingDate;

	@Column(name = "model_apple_silicon_id")
	private Integer modelAppleSiliconId;

	@Column(name = "model_apple_silicon_unified_memory_id")
	private Integer modelAppleSiliconUnifiedMemoryId;

	@Column(name = "model_storage_id")
	private Integer modelStorageId;

	@Column(name = "model_color_id")
	private Integer modelColorId;

	@Column(name = "url")
	private String url;

	@Column(name = "serial_number")
	private String serialNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_status_id")
	private DeviceStatusEntity status;

	@Column(name = "status_date")
	private LocalDateTime statusDate;

	@Column(name = "seller_business_partner_id")
	private Integer sellerBusinessPartnerId;

	@Column(name = "buyer_business_partner_id")
	private Integer buyerBusinessPartnerId;

	@Column(name = "purchase_price")
	private Double purchasePrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_grade_id")
	private DeviceGradeEntity grade;

	@Column(name = "reported_defect")
	private String reportedDefect;

	@Column(name = "reported_defect_confirmed")
	private Boolean reportedDefectConfirmed;

	@Column(name = "corrected_defect")
	private String correctedDefect;

	@Column(name = "selling_date")
	private LocalDate sellingDate;

	@Column(name = "selling_accessory_charger")
	private Boolean sellingAccessoryCharger;

	@Column(name = "selling_accessory_charging_cable")
	private Boolean sellingAccessoryChargingCable;

	@Column(name = "selling_accessory_original_packaging")
	private Boolean sellingAccessoryOriginalPackaging;

	@Column(name = "battery_maximum_capacity")
	private Integer batteryMaximumCapacity;

	@Column(name = "battery_cycle_count")
	private Integer batteryCycleCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_battery_status_id")
	private DeviceBatteryStatusEntity batteryStatus;

	@Column(name = "selling_price")
	private Double sellingPrice;

	@Column(name = "invoice_number")
	private Integer invoiceNumber;

	@Override
	public String getName() {
		return "#" + getId();
	}
}