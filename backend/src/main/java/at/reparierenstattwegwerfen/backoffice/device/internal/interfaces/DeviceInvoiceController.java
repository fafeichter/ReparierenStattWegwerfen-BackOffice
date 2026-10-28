package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.invoice.DeviceInvoiceService;
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
@RequestMapping("/api/devices/{deviceId}/invoices")
@RequiredArgsConstructor
public class DeviceInvoiceController {

	private final DeviceInvoiceService deviceInvoiceService;

	@GetMapping("/sale")
	public Integer getDeviceSaleInvoiceNumber(@PathVariable Integer deviceId) {
		return deviceInvoiceService.getDeviceSaleInvoiceNumber(deviceId);
	}
}