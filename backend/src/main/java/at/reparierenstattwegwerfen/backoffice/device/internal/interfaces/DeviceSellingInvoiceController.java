package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.selling.DeviceSellingInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Feichter
 * @since 28.10.2026
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/selling-invoices")
@RequiredArgsConstructor
public class DeviceSellingInvoiceController {

	private final DeviceSellingInvoiceService deviceSellingInvoiceService;

	@GetMapping("/number")
	public Integer getDeviceSellingInvoiceNumber(@PathVariable Integer deviceId) {
		return deviceSellingInvoiceService.getDeviceSellingInvoiceNumber(deviceId);
	}
}