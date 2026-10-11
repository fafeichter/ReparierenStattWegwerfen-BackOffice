package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceBusinessPartnerService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.CreateBuyerBusinessPartnerForDeviceDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceBusinesspartnerDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
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
	public void createBuyerBusinessPartnerForDevice(
		@Valid @RequestBody CreateBuyerBusinessPartnerForDeviceDto buyerBusinessPartnerForDevice,
		@AuthenticationPrincipal UserDetails currentUser) {
		businessPartnerService.createBuyerBusinessPartnerForDevice(buyerBusinessPartnerForDevice, currentUser);
	}
}