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
		if (maximumCapacity == null && cycleCount == null) {
			return null;
		}

		if ((maximumCapacity != null && maximumCapacity >= 86) || (cycleCount != null && cycleCount < 500)) {
			return 1;
		}

		if ((maximumCapacity != null && maximumCapacity < 80) || (cycleCount != null && cycleCount > 1000)) {
			// Apple considers a battery with less than 80% maximum capacity or more than 1,000 charge cycles to be defective.
			return 3;
		}

		return 2;
	}
}