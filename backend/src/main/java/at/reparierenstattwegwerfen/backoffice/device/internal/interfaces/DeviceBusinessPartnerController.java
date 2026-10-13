package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceBusinessPartnerService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.CreateBuyerBusinessPartnerForDeviceDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceBusinesspartnerDto;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class DeviceBusinessPartnerController {

	private final DeviceBusinessPartnerService businessPartnerService;

	@GetMapping("/devices/businesspartners/{businessPartnerId}/")
	public DeviceBusinesspartnerDto getDevicesOfBusinessPartner(@PathVariable Integer businessPartnerId) {
		return businessPartnerService.getDevicesOfBusinessPartner(businessPartnerId);
	}

	@PostMapping("/devices/{deviceId}/businesspartners/buyer")
	public void createBuyerBusinessPartnerForDevice(
		@PathVariable Integer deviceId,
		@Valid @RequestBody CreateBuyerBusinessPartnerForDeviceDto buyerBusinessPartnerForDevice,
		@AuthenticationPrincipal UserDetails currentUser) {
		businessPartnerService.createBuyerBusinessPartnerForDevice(deviceId, buyerBusinessPartnerForDevice, currentUser);
	}

	@PostMapping("/devices/{deviceId}/businesspartners/buyer/asgoodasnew")
	public NamedIdDto setBuyerToAsgoodasnew(@PathVariable Integer deviceId,
											@AuthenticationPrincipal UserDetails currentUser) {
		return businessPartnerService.setBuyerToAsgoodasnew(deviceId, currentUser);
	}
}