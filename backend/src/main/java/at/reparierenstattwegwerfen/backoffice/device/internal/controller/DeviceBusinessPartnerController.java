package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceBusinessPartnerService;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceBusinesspartnerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/devices/businesspartners/{businessPartnerId}")
@RequiredArgsConstructor
public class DeviceBusinessPartnerController {

	private final DeviceBusinessPartnerService businessPartnerService;

	@GetMapping("/")
	public DeviceBusinesspartnerDto getDevicesOfBusinessPartner(@PathVariable Integer businessPartnerId) {
		return businessPartnerService.getDevicesOfBusinessPartner(businessPartnerId);
	}
}