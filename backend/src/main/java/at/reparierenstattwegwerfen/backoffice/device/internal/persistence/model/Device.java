package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.device.DeviceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
public class Device {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_id")
	private Integer id;

	@Column(name = "model_id")
	private Integer modelId;

	@Column(name = "model_apple_silicon_id")
	private Integer modelAppleSiliconId;

	@Column(name = "model_apple_silicon_unified_memory_id")
	private Integer modelAppleSiliconUnifiedMemoryId;

	@Column(name = "model_storage_id")
	private Integer modelStorageId;

	@Column(name = "model_color_id")
	private Integer modelColorId;

	@Column(name = "serial_number")
	private String serialNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "device_status")
	private DeviceStatus status;

	@Column(name = "seller_business_partner_id")
	private Integer sellerBusinessPartnerId;

	@Column(name = "buyer_business_partner_id")
	private Integer buyerBusinessPartnerId;

	@Column(name = "purchase_price")
	private BigDecimal purchasePrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_grade_id")
	private DeviceGrade grade;

	@Column(name = "reported_defect")
	private String reportedDefect;

	@Column(name = "diagnosed_defect")
	private String diagnosedDefect;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchase_device_online_marketplace_id")
	private DeviceOnlineMarketplace purchaseDeviceOnlineMarketplace;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "selling_device_online_marketplace_id")
	private DeviceOnlineMarketplace sellingDeviceOnlineMarketplace;

	@Column(name = "selling_date")
	private LocalDate sellingDate;

	@Column(name = "battery_maximum_capacity")
	private Byte batteryMaximumCapacity;

	@Column(name = "battery_cycle_count")
	private Integer batteryCycleCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_battery_status_id")
	private DeviceBatteryStatus batteryStatus;

	@Column(name = "selling_price")
	private BigDecimal sellingPrice;
}