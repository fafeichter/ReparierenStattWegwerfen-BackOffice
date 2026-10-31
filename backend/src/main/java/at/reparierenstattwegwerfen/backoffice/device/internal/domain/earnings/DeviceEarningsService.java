package at.reparierenstattwegwerfen.backoffice.device.internal.domain.earnings;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceSpareParts;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceSparePartsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 31.10.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceEarningsService {

	private final DeviceRepository deviceRepository;
	private final DeviceSparePartsRepository deviceSparePartsRepository;

	public Double getDeviceEarnings(Integer deviceId) {
		Device device = deviceRepository.getById(deviceId);
		List<DeviceSpareParts> spareParts = deviceSparePartsRepository.getSparePartsForDevice(deviceId);

		return new DeviceEarning(device, spareParts).getProfitOrLoss();
	}
}