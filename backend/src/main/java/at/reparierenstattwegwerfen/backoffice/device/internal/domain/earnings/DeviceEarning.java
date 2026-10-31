package at.reparierenstattwegwerfen.backoffice.device.internal.domain.earnings;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceSpareParts;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 31.10.2026
 */
@RequiredArgsConstructor
public class DeviceEarning {

	private final Device device;
	private final List<DeviceSpareParts> spareParts;

	public Double getProfitOrLoss() {
		if (device.getSellingPrice() == null) {
			return device.getPurchasePrice() * -1;
		} else {
			double rohDifferenz = device.getSellingPrice() - device.getPurchasePrice();
			double ust = rohDifferenz * 20 / 120;
			double sparePartsNetto = spareParts.stream()
				.mapToDouble(DeviceSpareParts::getPriceNetto)
				.sum();

			return rohDifferenz - ust - sparePartsNetto;
		}
	}
}