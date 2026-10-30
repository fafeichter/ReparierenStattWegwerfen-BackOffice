package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.selling.DeviceSellingService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSellingDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 * @since 30.10.2026
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/selling")
@RequiredArgsConstructor
public class DeviceSellingController {

	private final DeviceSellingService deviceSellingService;

	@PostMapping("/")
	public void setSellingDetails(@PathVariable Integer deviceId,
								  @Valid @RequestBody DeviceSellingDto deviceSellingDto,
								  @AuthenticationPrincipal UserDetails currentUser) {
		deviceSellingService.setSellingDetails(deviceId, deviceSellingDto, currentUser);
	}
}