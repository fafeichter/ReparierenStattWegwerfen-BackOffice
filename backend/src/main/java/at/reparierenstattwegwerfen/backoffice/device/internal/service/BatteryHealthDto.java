package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Value;

/**
 * @author Fabian Feichter
 */
@Value
public class BatteryHealthDto {

	@Min(0)
	@Max(100)
	Integer maximumCapacity;

	@Positive
	Integer cycleCount;

	public BatteryHealthDto(Integer maximumCapacity, Integer cycleCount) {
		this.maximumCapacity = maximumCapacity;
		this.cycleCount = cycleCount;
	}

	public Integer determineStatusId() {
		if (maximumCapacity == null) {
			return null;
		}

		if (maximumCapacity >= 90) {
			return 1;
		}

		if (maximumCapacity <= 80 || (cycleCount != null && cycleCount >= 500)) {
			return 2;
		}

		return null;
	}
}