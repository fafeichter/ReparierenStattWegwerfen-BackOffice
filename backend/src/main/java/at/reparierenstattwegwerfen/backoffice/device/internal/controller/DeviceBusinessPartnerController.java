package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.CreateBuyerBusinessPartnerForDeviceDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceBusinessPartnerService;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceBusinesspartnerDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/devices/businesspartners")
@RequiredArgsConstructor
public class DeviceBusinessPartnerController {

	private final DeviceBusinessPartnerService businessPartnerService;

	@GetMapping("/{businessPartnerId}/")
	public DeviceBusinesspartnerDto getDevicesOfBusinessPartner(@PathVariable Integer businessPartnerId) {
		return businessPartnerService.getDevicesOfBusinessPartner(businessPartnerId);
	}

	@PostMapping("/buyer")
	public void createBuyerBusinessPartnerForDevice(@Valid @RequestBody CreateBuyerBusinessPartnerForDeviceDto buyerBusinessPartnerForDevice) {
		businessPartnerService.createBuyerBusinessPartnerForDevice(buyerBusinessPartnerForDevice);
	}
}