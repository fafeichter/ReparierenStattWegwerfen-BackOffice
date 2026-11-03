package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Value;

/**
 * @author Fabian Feichter
 * @since 07.07.2026
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

		if (maximumCapacity != null && maximumCapacity < 80 || cycleCount != null && cycleCount > 1000) {
			// "Service empfohlen"
			return 2;
		}

		if (maximumCapacity != null && maximumCapacity >= 85 && cycleCount != null) {
			// Nicht getauscht
			return 1;
		}

		if (maximumCapacity != null) {
			if (maximumCapacity < 85) {
				// Freiwillig getauscht
				return 4;
			}
		}

		// TODO: iPad nie tauschen (außer bei Defekt)?

		return null;
	}
}