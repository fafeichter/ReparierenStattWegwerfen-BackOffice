package at.reparierenstattwegwerfen.backoffice.device.internal.domain;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.battery.DeviceBatteryStatus;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.buying.DeviceOnlineMarketplace;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade.DeviceGrade;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.status.DeviceStatus;
import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Device implements NamedId {
	private Integer id;
	private Integer modelId;
	private LocalDate buyingDate;
	private Integer modelAppleSiliconId;
	private Integer modelAppleSiliconUnifiedMemoryId;
	private Integer modelStorageId;
	private Integer modelColorId;
	private String url;
	private String serialNumber;
	private DeviceStatus status;
	private LocalDateTime statusDate;
	private Integer sellerBusinessPartnerId;
	private Integer buyerBusinessPartnerId;
	private Double purchasePrice;
	private DeviceGrade grade;
	private String reportedDefect;
	private String diagnosedDefect;
	private DeviceOnlineMarketplace sellingDeviceOnlineMarketplace;
	private LocalDate sellingDate;
	private Boolean sellingAccessoryCharger;
	private Boolean sellingAccessoryChargingCable;
	private Boolean sellingAccessoryOriginalPackaging;
	private Integer batteryMaximumCapacity;
	private Integer batteryCycleCount;
	private DeviceBatteryStatus batteryStatus;
	private Double sellingPrice;

	@Override
	public String getName() {
		return "#" + getId();
	}
}