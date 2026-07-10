package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
public class Device implements NamedId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private DeviceStatus status;

	@Column(name = "seller_business_partner_id")
	private Integer sellerBusinessPartnerId;

	@Column(name = "buyer_business_partner_id")
	private Integer buyerBusinessPartnerId;

	@Column(name = "purchase_price")
	private Double purchasePrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_grade_id")
	private DeviceGrade grade;

	@Column(name = "reported_defect")
	private String reportedDefect;

	@Column(name = "diagnosed_defect")
	private String diagnosedDefect;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "selling_device_online_marketplace_id")
	private DeviceOnlineMarketplace sellingDeviceOnlineMarketplace;

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
	private DeviceBatteryStatus batteryStatus;

	@Column(name = "selling_price")
	private Double sellingPrice;

	@Override
	public String getName() {
		return "#" + getId();
	}
}